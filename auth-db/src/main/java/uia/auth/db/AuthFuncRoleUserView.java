package uia.auth.db;

public class AuthFuncRoleUserView extends AuthFuncRole {

    private String funcName;

    private String funcDescription;

    private String roleName;

    private String roleEnabled;

    private long authUser;

    private String userId;

    private String userName;

    private String userEnabled;

    public AuthFuncRoleUserView() {
    }

    public AuthFuncRoleUserView(AuthFuncRoleUserView data) {
    	super(data);
        this.funcName = data.funcName;
        this.funcDescription = data.funcDescription;
        this.roleName = data.roleName;
        this.roleEnabled = data.roleEnabled;
        this.authUser = data.authUser;
        this.userId = data.userId;
        this.userName = data.userName;
        this.userEnabled = data.userEnabled;
    }

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncDescription() {
		return funcDescription;
	}

	public void setFuncDescription(String funcDescription) {
		this.funcDescription = funcDescription;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleEnabled() {
		return roleEnabled;
	}

	public void setRoleEnabled(String roleEnabled) {
		this.roleEnabled = roleEnabled;
	}

	public long getAuthUser() {
		return authUser;
	}

	public void setAuthUser(long authUser) {
		this.authUser = authUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(String userEnabled) {
		this.userEnabled = userEnabled;
	}
    
}
