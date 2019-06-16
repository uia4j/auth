package uia.auth.db;

public class AuthUser {

    private long id;

    private String userId;

    private String userName;

    private String enabled;

    private String seed;

    private String mobileNo;

    private String email;

    public AuthUser() {
    	this.seed = "00000";
    }

    public AuthUser(AuthUser data) {
        this.id = data.id;
        this.userId = data.userId;
        this.userName = data.userName;
        this.enabled = data.enabled;
        this.seed = data.seed;
        this.mobileNo = data.mobileNo;
        this.email = data.email;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getSeed() {
        return this.seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
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
    public AuthUser clone() {
        return new AuthUser(this);
    }

    @Override
    public String toString() {
        return this.userId;
    }
}
