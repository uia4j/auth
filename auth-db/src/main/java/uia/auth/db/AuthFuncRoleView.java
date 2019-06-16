package uia.auth.db;

public class AuthFuncRoleView extends AuthFuncRole {

    private String funcName;

    private String funcDescription;

    private String roleName;

    private String roleEnabled;

    public AuthFuncRoleView() {
    }

    public AuthFuncRoleView(AuthFuncRoleView data) {
        super(data); 
        this.funcName = data.funcName;
        this.funcDescription = data.funcDescription;
        this.roleName = data.roleName;
        this.roleEnabled = data.roleEnabled;
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

	@Override
    public AuthFuncRoleView clone() {
        return new AuthFuncRoleView(this);
    }

    @Override
    public String toString() {
        return String.format("%6s.%-30s %-20s %s", getAuthFunc(), this.funcName, this.roleName, this.getAccessType());
    }
}
