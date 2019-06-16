package uia.auth.db;

public class AuthRole {

    private long id;

    private String roleName;

    private String enabled;

    public AuthRole() {
    }

    public AuthRole(AuthRole data) {
        this.id = data.id;
        this.roleName = data.roleName;
        this.enabled = data.enabled;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Override
    public AuthRole clone() {
        return new AuthRole(this);
    }

    @Override
    public String toString() {
        return String.format("%6s(%s). %-30s", this.id, this.enabled, this.roleName);
    }
}
