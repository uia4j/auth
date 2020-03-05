package uia.auth;

public class AuthUserPower {

    public long authFunc;

    public long authUser;

    public String funcName;

    public String userId;

    public String userName;

    public String accessType;

    public boolean enabled;

    public boolean userLevel;
    
    public String args;

    public long getAuthFunc() {
        return this.authFunc;
    }

    public void setAuthFunc(long authFunc) {
        this.authFunc = authFunc;
    }

    public long getAuthUser() {
        return this.authUser;
    }

    public void setAuthUser(long authUser) {
        this.authUser = authUser;
    }

    public String getFuncName() {
        return this.funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUserLevel() {
        return this.userLevel;
    }

    public void setUserLevel(boolean userLevel) {
        this.userLevel = userLevel;
    }
    
    public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	@Override
    public String toString() {
    	return this.accessType;
    }

}
