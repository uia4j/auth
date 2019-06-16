package uia.auth.db;

public class AuthRoleUser {

    private long authUser;

    private long authRole;

    public AuthRoleUser() {
    }

    public AuthRoleUser(AuthRoleUser data) {
        this.authUser = data.authUser;
        this.authRole = data.authRole;
    }

    public long getAuthUser() {
        return this.authUser;
    }

    public void setAuthUser(long authUser) {
        this.authUser = authUser;
    }

    public long getAuthRole() {
        return this.authRole;
    }

    public void setAuthRole(long authRole) {
        this.authRole = authRole;
    }

    @Override
    public AuthRoleUser clone() {
        return new AuthRoleUser(this);
    }

    @Override
    public String toString() {
        return this.authUser + ", " + this.authRole;
    }
}
