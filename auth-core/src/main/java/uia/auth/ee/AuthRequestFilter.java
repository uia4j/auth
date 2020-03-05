package uia.auth.ee;

import java.io.IOException;
import java.util.Base64;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uia.auth.AuthFuncHelper;
import uia.auth.AuthUserHelper;
import uia.auth.AuthValidator; 
import uia.auth.AuthValidator.AccessType;
import uia.auth.db.ViewAuthSecurity;
import uia.auth.ee.Secured;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthRequestFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(AuthRequestFilter.class);

    private static final String BA_SCHEME = "Basic";

    private static String REALM = "AUTH";

    private static long TIMEOUT = 20000;
    
    public static void config(String realm, long timeout) {
    	REALM = realm;
    	TIMEOUT = Math.max(60000, timeout);
    }

    @Context
    private ResourceInfo resourceInfo;

    @Context
    HttpServletRequest request;
    
    private AuthAuthenticator authenticator;

    public AuthRequestFilter() throws Exception {
    	String driver = System.getProperty("auth.authenticator");
    	if(driver != null) {
    		try {
			this.authenticator = (AuthAuthenticator) Class.forName(driver).newInstance();
    		}
    		catch(Exception ex) {
    			throw new Exception("Load AuthAuthenticator failed:" + driver, ex);
    		}
    	}
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Secured secured = this.resourceInfo.getResourceMethod().getAnnotation(Secured.class);
        if (secured == null) {
            return;
        }

        /**
         * Authentication and Authorization:
         * 1. Authentication
         *    a. 若有 BA 資訊，進行 BA 驗證，並產生新的 Session 編號。
         *    b. 根據 Session 編號進行驗證。
         *    驗證完成後會產生新的 Session 並回傳。
         *    
         * 2. Authorization
         *    驗證使用者的讀取權限 (WRITE, READONLY, DENY)。
         */

        String baHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String user = requestContext.getHeaderString(AuthHeaders.USER);
        String session = requestContext.getHeaderString(AuthHeaders.SESSION);
    	LOGGER.info("auth> filter> USER=" + user);
    	LOGGER.info("auth> filter> SESSION=" + session);
    	LOGGER.info("auth> filter> BA=" + baHeader);

        // 1. Authentication
    	String userId = null;
    	if(secured.admin()) {
        	LOGGER.info("auth> filter> admin");
        	userId = validateBA(requestContext, baHeader, true);
    	}
    	else {
    		userId = validateAuthentication(requestContext, user, session, baHeader);
        	LOGGER.info("auth> filter> authentication:" + userId);
        }
    	if(userId == null) {
    		return;
    	}

        // 2. Authorization
    	if(!validateAccess(requestContext, secured, userId)) {
            abortSession(requestContext, HttpServletResponse.SC_UNAUTHORIZED, "access deny");
    	}
    }
    
    private String validateAuthentication(ContainerRequestContext ctx, String userId, String session, String baHeader) {
    	try(AuthUserHelper helper = new AuthUserHelper()) {
        	ViewAuthSecurity security = null;
        	if(session != null) {
        		security = helper.searchSecurityBySession(session);
        	}
        	else if(userId != null) {
        		security = helper.searchSecurityByUserId(userId);
        	}

        	if(security == null) {
        		if(baHeader == null) {
            	    LOGGER.info("auth> filter> SESSION,USER,BA not found");
	                abortBA(ctx, "SESSION,USER,BA not found");					// 強迫重新驗證
	                return null;
        		}
        		else {
            		return validateBA(ctx, baHeader, false);
        		}
        	}

		    LOGGER.info(String.format("auth> filter> SESSION user:%s expire on %s", 
		    		security.getUserId(), 
		    		security.getTokenExpired()));
        	
            // 逾時
			if(System.currentTimeMillis() >= security.getTokenExpired().getTime()) {
        		// 更換逾時時間和 Session 編號
				ViewAuthSecurity vas = helper.udpateToken(security.getUserId(), TIMEOUT);
    		    LOGGER.info(String.format("auth> filter> SESSION user:%s expired, new:%s", security.getUserId(), vas.getToken()));
                abortBA(ctx, "session expired");								// 強迫重新驗證
				return null;
			}

			// 用戶確認
		    LOGGER.info(String.format("auth> filter> SESSION user:%s pass", security.getUserId()));
        	ctx.getHeaders().putSingle(AuthHeaders.USER, security.getUserId());
        	ctx.getHeaders().putSingle(AuthHeaders.SESSION, security.getToken());
        	return security.getUserId();
        }
        catch(Exception ex) {
		    LOGGER.error("auth> filter> SESSION error", ex);
            abortBA(ctx, "internal error: "  + ex.getMessage());				// 強迫重新驗證
            return null;
        }
    }

    private String validateBA(ContainerRequestContext ctx, String baHeader, boolean admin) {
    	if(baHeader == null || !baHeader.startsWith(BA_SCHEME + " ")) {
    		abortBA(ctx, "BA token failed");
            return null;
        }

        String token = baHeader.substring(BA_SCHEME.length()).trim();

        byte[] data = Base64.getDecoder().decode(token);
        String[] up = new String(data).split(":");
        if (up.length != 2) {
		    LOGGER.error("auth> filter> BA format is wrong");
            abortBA(ctx, "authentication failed: format");						// 強迫重新驗證
            return null;
        }

        String userId = up[0].trim();
        String password = up[1].trim();
    	try(AuthUserHelper helper = new AuthUserHelper()) {
    		boolean ok = false;
    		if(this.authenticator != null) {
    			ok = this.authenticator.check(userId, password);
    		}
    		else {
    			ok = helper.validatePassword(userId, password);
    		}
    		if(!ok) {
    		    LOGGER.info(String.format("auth> filter> BA user:%s failed", userId));
                abortBA(ctx, "authentication failed: password");				// 強迫重新驗證
    			return null;
    		}
    		
    		ViewAuthSecurity security = helper.searchSecurityByUserId(userId);

		    // 逾時
        	boolean expired = System.currentTimeMillis() >= security.getTokenExpired().getTime();
        	boolean expiredShort = System.currentTimeMillis() >= security.getTokenExpiredShort().getTime();
        	if((admin && expiredShort)) {
        		// 僅調整逾時時間，不更換 Session 編號
    		    LOGGER.info(String.format("auth> filter> BA user:%s expired, updateTokenTime", userId));
        		helper.udpateTokenTime(userId, TIMEOUT);
                abortBA(ctx, "basic authentication first");					// 強迫重新驗證
        		return null;
        	}
        	else if(expired) {
        		// 更換逾時時間和 Session 編號
    		    LOGGER.info(String.format("auth> filter> BA user:%s expired, updateToken", userId));
        		helper.udpateToken(userId, TIMEOUT);
                abortBA(ctx, "session expired");								// 強迫重新驗證
        		return null;
        	}
 
			// 用戶確認
			// Session 確認
		    LOGGER.info(String.format("auth> filter> BA user:%s pass", userId));
		    if(!admin) {
	            ctx.getHeaders().putSingle(AuthHeaders.USER, userId);
				ctx.getHeaders().putSingle(AuthHeaders.SESSION, security.getToken());
		    }
		    else {
	            ctx.getHeaders().putSingle(AuthHeaders.ADMIN, userId);
		    }
            return userId;
    	}
    	catch(Exception ex) {
		    LOGGER.error(String.format("auth> filter> BA user:%s error", userId), ex);
            abortBA(ctx, "internal error: "  + ex.getMessage());				// 強迫重新驗證
    		return null;
    	}
    }

    private boolean validateAccess(ContainerRequestContext ctx, Secured secured, String userId) {
        AccessType at = AccessType.WRITE;

        String[] funcs = secured.authorization();
        if (funcs == null || funcs.length == 0) {
            ctx.getHeaders().putSingle(AuthHeaders.ACCESS, AccessType.WRITE.toString());
            return true;
        }
        
        try(AuthFuncHelper helper = new AuthFuncHelper()) {
        	AuthValidator validator = helper.validate(userId);
        	if(Secured.StrategyType.OR == secured.strategy()) {
        		for(String func : funcs) {
        			validator.or(func);
        		}
    			at = validator.result();
        	}
        	else {
        		for(String func : funcs) {
        			validator.and(func);
        		}
    			at = validator.result();
        	}
        }
        catch(Exception ex) {
        	ex.printStackTrace();
            at = AuthValidator.AccessType.DENY;
        }
        ctx.getHeaders().putSingle(AuthHeaders.ACCESS, at.toString());
        
	    LOGGER.info(String.format("auth> filter> %s access=%s", userId, at));
	    return at != AccessType.DENY;
    }

    private void abortBA(ContainerRequestContext requestContext, String message) {
		requestContext.abortWith(
                Response.status(HttpServletResponse.SC_UNAUTHORIZED)									// 401
                        .header(HttpHeaders.WWW_AUTHENTICATE, BA_SCHEME + " realm=\"" + REALM + "\"")	// force to login
                        .entity(message)
                        .build());
    }

    private void abortSession(ContainerRequestContext requestContext, int responseType, String message) {
        requestContext.abortWith(
        		Response.status(responseType)
        				.header("auth-reason", message)
        				.entity(message)
        		        .build());
    }
}