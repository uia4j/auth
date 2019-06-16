package uia.auth.ms;

import java.sql.Connection;
import java.util.List;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthHandler;
import uia.auth.db.AuthRole;
import uia.auth.db.conf.DB;
import uia.auth.db.dao.AuthRoleDao;

/**
 * Hello world!
 *
 */
public class RoleService extends AppService {
	
    public static Router bind(Router router, AuthHandler authHandler) {
    	new RoleService(router, authHandler);
    	return router;
    }

    RoleService(Router router, AuthHandler authHandler) {
        router.get(apiPath("/roles"))
			.produces(ContentType.APPLICATION_JSON)
			.handler(this::queryRoles);
    }
    
    private void queryRoles(RoutingContext ctx) {
		HttpServerResponse response = ctx.response();
		response.setChunked(true);
    	try(Connection conn = DB.create()) {
    		AuthRoleDao dao = new AuthRoleDao(conn);
    		List<AuthRole> roles = dao.selectAll();
    		response
    			.putHeader("content-type", ContentType.APPLICATION_JSON)
    			.write(this.gson.toJson(roles))
    			.end();
    	}
    	catch(Exception ex) {
    		ctx.fail(ex);
    	}
    }
}
