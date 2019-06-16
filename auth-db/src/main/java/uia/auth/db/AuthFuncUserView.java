package uia.auth.db;

public class AuthFuncUserView extends AuthFuncUser {

    private String funcName;

    private String funcDescription;

    private String userId;

    private String userName;

    private String userEnabled;

    public AuthFuncUserView() {
    }

    public AuthFuncUserView(AuthFuncUserView data) {
        super(data);
        this.funcName = data.funcName;
        this.funcDescription = data.funcDescription;
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

	@Override
    public AuthFuncUserView clone() {
        return new AuthFuncUserView(this);
    }

    @Override
    public String toString() {
        return String.format("%6s.%-30s %-20s %s", getAuthFunc(), this.funcName, this.userId, this.getAccessType());
    }
}
