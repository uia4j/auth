package uia.auth.db;

public class AuthFuncRole {

    private long authFunc;

    private long authRole;

    private String accessType;

    public AuthFuncRole() {
    }

    public AuthFuncRole(AuthFuncRole data) {
        this.authFunc = data.authFunc;
        this.authRole = data.authRole;
        this.accessType = data.accessType;
    }

    public AuthFuncRole(long authFunc, long authRole, String accessType) {
    	this.authFunc = authFunc;
    	this.authRole = authRole;
    	this.accessType = accessType;
    }

    public long getAuthFunc() {
        return this.authFunc;
    }

    public void setAuthFunc(long authFunc) {
        this.authFunc = authFunc;
    }

    public long getAuthRole() {
        return this.authRole;
    }

    public void setAuthRole(long authRole) {
        this.authRole = authRole;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @Override
    public AuthFuncRole clone() {
        return new AuthFuncRole(this);
    }

    @Override
    public String toString() {
        return this.authFunc + ", " + this.authRole;
    }
}
