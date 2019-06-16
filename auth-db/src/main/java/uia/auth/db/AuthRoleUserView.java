package uia.auth.db;

public class AuthRoleUserView {

    private Long authUser;

    private Long authRole;

    private String roleName;

    private String roleEnabled;

    private String userId;

    private String userName;

    private String userEnabled;

    private String pwd;

    private String mobileNo;

    private String email;

    public AuthRoleUserView() {
    }

    public AuthRoleUserView(AuthRoleUserView data) {
        this.authUser = data.authUser;
        this.authRole = data.authRole;
        this.roleName = data.roleName;
        this.roleEnabled = data.roleEnabled;
        this.userId = data.userId;
        this.userName = data.userName;
        this.userEnabled = data.userEnabled;
        this.pwd = data.pwd;
        this.mobileNo = data.mobileNo;
        this.email = data.email;
    }

    public Long getAuthUser() {
        return this.authUser;
    }

    public void setAuthUser(Long authUser) {
        this.authUser = authUser;
    }

    public Long getAuthRole() {
        return this.authRole;
    }

    public void setAuthRole(Long authRole) {
        this.authRole = authRole;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleEnabled() {
        return this.roleEnabled;
    }

    public void setRoleEnabled(String roleEnabled) {
        this.roleEnabled = roleEnabled;
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

    public String getUserEnabled() {
        return this.userEnabled;
    }

    public void setUserEnabled(String userEnabled) {
        this.userEnabled = userEnabled;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public AuthRoleUserView clone() {
        return new AuthRoleUserView(this);
    }

    @Override
    public String toString() {
        return "" + this.authUser;
    }
}
