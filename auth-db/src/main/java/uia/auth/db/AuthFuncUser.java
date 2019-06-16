package uia.auth.db;

public class AuthFuncUser {

    private long authFunc;

    private long authUser;

    private String accessType;

    public AuthFuncUser() {
    }

    public AuthFuncUser(AuthFuncUser data) {
        this.authFunc = data.authFunc;
        this.authUser = data.authUser;
        this.accessType = data.accessType;
    }

    public AuthFuncUser(long authFunc, long authUser, String accessType) {
    	this.authFunc = authFunc;
    	this.authUser = authUser;
    	this.accessType = accessType;
    }

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

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @Override
    public AuthFuncUser clone() {
        return new AuthFuncUser(this);
    }

    @Override
    public String toString() {
        return this.authFunc + ", " + this.authUser;
    }
}
