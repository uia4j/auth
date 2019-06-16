package uia.auth.db;

import java.util.Date;
import java.util.UUID;

public class AuthSecurity {

    private long authUser;

    private String password;

    private String token;

    private Date tokenExpired;

    private Date tokenExpiredShort;


    public AuthSecurity() { 
    	this.password = "692692acdf9c710fcc5eb9a60c1af429";
    	this.token = UUID.randomUUID().toString();
    	this.tokenExpired = new Date();
    	this.tokenExpiredShort = this.tokenExpired;
    }	

    public AuthSecurity(AuthSecurity data) {
        this.authUser = data.authUser;
        this.password = data.password;
        this.token = data.token;
        this.tokenExpired = data.tokenExpired;
        this.tokenExpiredShort = data.tokenExpiredShort;
    }	

    public long getAuthUser() {
		return authUser;
	}

	public void setAuthUser(long authUser) {
		this.authUser = authUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Date tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public Date getTokenExpiredShort() {
		return tokenExpiredShort;
	}

	public void setTokenExpiredShort(Date tokenExpiredShort) {
		this.tokenExpiredShort = tokenExpiredShort;
	}

	@Override
    public AuthSecurity clone() {
    	return new AuthSecurity(this);
    }

    @Override
    public String toString() {
        return "" + this.authUser;
    }
}
