package uia.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uia.auth.db.AuthFunc;
import uia.auth.db.ViewAuthFuncRoleUser;
import uia.auth.db.ViewAuthFuncUser;
import uia.auth.db.dao.AuthFuncDao;
import uia.auth.db.dao.ViewAuthFuncRoleUserDao;
import uia.auth.db.dao.ViewAuthFuncUserDao;
import uia.dao.DaoException;

public class AuthValidator {
	
    private static final Logger LOGGER = LogManager.getLogger(AuthValidator.class);

    public enum AccessType {
        
        DENY(8, "D"),
        
        READONLY(4, "R"), 
        
        SELF(2, "S"), 
        
        WRITE(1, "W"), 
        
    	UNKNOWN(0, "U");
    	
    	public final int priority;
    	
    	public final String code;
    	
    	AccessType(int priority, String code) {
    		this.priority  = priority;
    		this.code = code;
    	}
    	
    	public static AccessType codeOf(String code) {
    		if("D".equalsIgnoreCase(code)) {
    			return AccessType.DENY;
    		}
    		if("R".equalsIgnoreCase(code)) {
    			return AccessType.READONLY;
    		}
    		if("S".equalsIgnoreCase(code)) {
    			return AccessType.SELF;
    		}
    		if("W".equalsIgnoreCase(code)) {
    			return AccessType.WRITE;
    		}
    		return AccessType.UNKNOWN;
    	}
    };
    
    public static class UserAccessInfo {
    	
    	private AccessType accessType;
    	
    	private String funcArgs;
    	
    	private String funcUserArgs;
    	
    	public UserAccessInfo(){
    	}
    	
    	public UserAccessInfo(AccessType accessType, String funcArgs, String funcUserArgs){
    		this.accessType = accessType;
    		this.funcArgs = funcArgs;
    		this.funcUserArgs = funcUserArgs;
    	}

		public AccessType getAccessType() {
			return this.accessType;
		}

		public String getFuncArgs() {
			return this.funcArgs;
		}

		public String getFuncUserArgs() {
			return this.funcUserArgs;
		}
    }

    private final String tx;

    private final String userId;

    private AuthFuncDao funcDao;

    private ViewAuthFuncRoleUserDao fruvDao;

    private ViewAuthFuncUserDao fuvDao;

    private AccessType result;
    
    private AuthUserPower power;

    AuthValidator(Connection conn, String userId) {
    	this.tx = "" + System.currentTimeMillis();
        this.userId = userId;
        this.result = AccessType.UNKNOWN;
        this.funcDao = new AuthFuncDao(conn);
        this.fuvDao = new ViewAuthFuncUserDao(conn);
        this.fruvDao = new ViewAuthFuncRoleUserDao(conn);
    }
    
    public AuthUserPower getPower() {
    	return this.power;
    }
    
    public String getUserId() {
    	return this.userId;
    }

    public AccessType result() {
        return this.result;
    }
    
    public UserAccessInfo resultInfo(String funcArgs, String funcUserArgs) {
    	return new UserAccessInfo(result(), funcArgs, funcUserArgs);
    }

    public AuthValidator and(AuthValidator aor) {
        and(aor.result);
        return this;
    }

    public AuthValidator and(String funcName) throws SQLException, DaoException {
        if (this.result == AccessType.DENY) {
            return this;
        }

        List<AuthFunc> funcs = this.funcDao.searchParents(funcName);
        for (AuthFunc func : funcs) {
            AuthUserPower fua = search(func.getFuncName());
            LOGGER.info(String.format("auth> %s> %-20s: func=%-40s, accessType=%s", this.tx, this.userId, func.getFuncName(), fua));
            if (fua != null) {
            	this.power = fua;
                and(toAccessType(fua));
                return this;
            }
        }

        this.result = AccessType.DENY;
        return this;
    }

    public AuthValidator or(AuthValidator aor) {
        or(aor.result);
        return this;
    }

    public AuthValidator or(String funcName) throws SQLException, DaoException {
        if (this.result == AccessType.WRITE) {
            return this;
        }

        List<AuthFunc> funcs = this.funcDao.searchParents(funcName);
        for (AuthFunc func : funcs) {
            AuthUserPower fua = search(func.getFuncName());
            if (fua != null) {
            	this.power = fua;
                or(toAccessType(fua));
                return this;
            }
        }

        this.result = AccessType.DENY;
        return this;
    }
    
    public String toString() {
    	return String.format("%-20s: result=%s", this.userId, this.result);
    }

    private void and(AccessType access) {
    	if(access.priority > result.priority) {
            this.result = access;
    	}
    }

    private void or(AccessType access) {
    	if(this.result == AccessType.UNKNOWN) {
    		this.result = access;
    	}
    	if(access.priority < result.priority) {
            this.result = access;
    	}
    }

    private static AccessType toAccessType(AuthUserPower fua) {
        if (!fua.isEnabled()) {
            return AccessType.DENY;
        }

        if ("W".equals(fua.getAccessType())) {
            return AccessType.WRITE;
        }

        if ("S".equals(fua.getAccessType())) {
            return AccessType.SELF;
        }

        if ("R".equals(fua.getAccessType())) {
            return AccessType.READONLY;
        }

        if ("D".equals(fua.getAccessType())) {
            return AccessType.DENY;
        }

        return AccessType.UNKNOWN;
    }

    private AuthUserPower search(String funcName) throws SQLException, DaoException {
        AuthUserPower fua = null;

        ViewAuthFuncUser fuv = this.fuvDao.select(funcName, this.userId);
        ViewAuthFuncRoleUser fruv = this.fruvDao.select(funcName, this.userId);
        if (fuv != null) {
            fua = new AuthUserPower();
            fua.setAuthFunc(fuv.getAuthFunc());
            fua.setAuthUser(fuv.getAuthUser());
            fua.setFuncName(fuv.getFuncName());
            fua.setUserId(fuv.getUserId());
            fua.setUserName(fuv.getUserName());
            fua.setAccessType(fuv.getAccessType());
            fua.setEnabled("y".equalsIgnoreCase(fuv.getUserEnabled()));
            fua.setUserLevel(true);
            fua.setArgs(fuv.getFuncUserArgs());
        }
        else if (fruv != null) {
            fua = new AuthUserPower();
            fua.setAuthFunc(fruv.getAuthFunc());
            fua.setAuthUser(fruv.getAuthUser());
            fua.setFuncName(fruv.getFuncName());
            fua.setUserId(fruv.getUserId());
            fua.setUserName(fruv.getUserName());
            fua.setAccessType(fruv.getAccessType());
            fua.setEnabled("y".equalsIgnoreCase(fruv.getUserEnabled()) && "y".equalsIgnoreCase(fruv.getRoleEnabled()));
            fua.setUserLevel(false);
            fua.setArgs(fruv.getFuncRoleArgs());
        }

        return fua;
    }
}
