package uia.auth.ms;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import uia.auth.AuthUserHelper;

public class UiaAuthProvider implements AuthProvider {

	@Override
	public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
		String userId = authInfo.getString("username");
	    if (userId == null) {
	      resultHandler.handle(Future.failedFuture("authInfo must contain username in 'username' field"));
	      return;
	    }
	    
	    String password = authInfo.getString("password");
	    if (password == null) {
	      resultHandler.handle(Future.failedFuture("authInfo must contain password in 'password' field"));
	      return;
	    }
	    
	    try(AuthUserHelper helper = new AuthUserHelper()) {
	    	if(helper.validatePassword(userId, password)) {
	    		System.out.println("pass");
	    		//resultHandler.handle(Future.succeededFuture(new UiaUser(userId)));
	    		resultHandler.handle(Future.failedFuture("user not found or password is wrong"));
	    	}
	    	else {
	    		resultHandler.handle(Future.failedFuture("user not found or password is wrong"));
	    	}
	    }
	    catch(Exception ex) {
	    	resultHandler.handle(Future.failedFuture("failed to authentication"));
	    }
	}

}
