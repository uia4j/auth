package uia.auth.ms;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

public class UiaUser extends AbstractUser {
	
	private String userId;
	
	private JsonObject principal;
	
	public UiaUser(String userId) {
		this.userId = userId;
	}

	@Override
	public JsonObject principal() {
		if (this.principal == null) {
			this.principal = new JsonObject().put("userId", userId);
		}
		return this.principal;	
	}

	@Override
	public void setAuthProvider(AuthProvider provider) {
	}

	@Override
	protected void doIsPermitted(String permissionOrRole, Handler<AsyncResult<Boolean>> resultHandler) {
	}

}
