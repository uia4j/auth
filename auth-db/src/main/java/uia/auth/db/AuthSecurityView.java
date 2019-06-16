package uia.auth.db;
 
public class AuthSecurityView extends AuthSecurity {

    private String userId;

    private String enabled;

    private String seed;

    public AuthSecurityView() {
    }	

    public AuthSecurityView(AuthSecurityView data) {
    	super(data);
        this.userId = data.userId;
        this.enabled = data.enabled;
        this.seed = data.seed;
    }	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	@Override
    public AuthSecurityView clone() {
    	return new AuthSecurityView(this);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.userId, getAuthUser());
    }
}
