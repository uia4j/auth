package uia.auth.ms;

import java.sql.Connection;
import java.util.List;
import java.util.TreeMap;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import uia.auth.AuthFuncHelper;
import uia.auth.AuthValidator;
import uia.auth.AuthValidator.AccessType;
import uia.auth.db.AuthUser;
import uia.auth.db.conf.AuthDB;
import uia.auth.db.dao.AuthUserDao;

/**
 * Hello world!
 *
 */
public class UserService extends AppService {
	
    
    public static Router bind(Router router, AuthHandler authHandler) {
    	new UserService(router, authHandler);
    	return router;
    }

    UserService(Router router, AuthHandler authHandler) {
    	//
        router.get(apiPath("/users"))
			.produces(ContentType.APPLICATION_JSON)
			.handler(authHandler)
			.handler(this::queryUsers);
    	//
        router.get(apiPath("/users/:userId"))
    		.produces(ContentType.APPLICATION_JSON)
    		.handler(this::queryUser);
    	//
        router.post(apiPath("/users/:userId/_authorize"))
			.produces(ContentType.APPLICATION_JSON)
			.handler(BodyHandler.create())
    		.handler(this::authorize);
    }
    
    private void queryUsers(RoutingContext ctx) {
		HttpServerResponse response = ctx.response();
		response.setChunked(true);
    	try(Connection conn = AuthDB.create()) {
    		AuthUserDao dao = new AuthUserDao(conn);
    		List<AuthUser> users = dao.selectAll();
    		response
    			.putHeader("content-type", ContentType.APPLICATION_JSON)
    			.write(this.gson.toJson(users))
    			.end();
    	}
    	catch(Exception ex) {
    		ctx.fail(404, ex);
    	}
    }
    
    private void queryUser(RoutingContext ctx) {
    	String userId = ctx.request().getParam("userId");
    	  
    	HttpServerResponse response = ctx.response();
		response.setChunked(true);
    	try(Connection conn = AuthDB.create()) {
    		AuthUserDao dao = new AuthUserDao(conn);
    		AuthUser user = dao.selectByUserId(userId);
    		response
    			.putHeader("content-type", ContentType.APPLICATION_JSON)
    			.write(this.gson.toJson(user))
    			.end();
    	}
    	catch(Exception ex) {
    		ctx.fail(404, ex);
    	}
    }
    
    private void authorize(RoutingContext ctx) {
    	String userId = ctx.request().getParam("userId");

    	AccessType at = null;
        try(AuthFuncHelper helper = new AuthFuncHelper()) {
        	System.out.println(ctx.getBodyAsString());
        	JsonObject info = ctx.getBodyAsJson();
        	System.out.println(info);
        	String op = info.getString("op");

        	AuthValidator validator = helper.validate(userId);
        	if("and".equals(op)) {
            	info.getJsonArray("funcs").forEach(f -> {
            		try {
						validator.and(f.toString());
					} catch (Exception e) {

					}
            	});
            	at = validator.result();
        	}
        	else if("or".equals(op)) {
            	info.getJsonArray("funcs").forEach(f -> {
            		try {
						validator.or(f.toString());
					} catch (Exception e) {

					}
            	});
            	at = validator.result();
        	}
        	else {
                at = AuthValidator.AccessType.DENY;
        	}
        }
        catch(Exception ex) {
            at = AuthValidator.AccessType.DENY;
        }

        TreeMap<String, Object> result = new TreeMap<String, Object>();
        result.put("accessType", at);
        
    	HttpServerResponse response = ctx.response();
		response.setChunked(true);
		response
			.putHeader("content-type", ContentType.APPLICATION_JSON)
			.write(this.gson.toJson(result))
			.end();
    }
}
