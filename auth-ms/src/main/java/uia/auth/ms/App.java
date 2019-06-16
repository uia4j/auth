package uia.auth.ms;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.UserSessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import uia.auth.ee.AuthHeaders;

/**
 * Hello world!
 *
 */
public class App extends AbstractVerticle {

	private HttpServer server;
	
    public App() {
    }

    @Override
    public void start() throws Exception {
		Set<String> allowedHeaders = new HashSet<String>();
		allowedHeaders.add(AuthHeaders.USER);
		allowedHeaders.add(AuthHeaders.SESSION);
		allowedHeaders.add("x-requested-with");
		allowedHeaders.add("Access-Control-Allow-Origin");
		allowedHeaders.add("origin");
		allowedHeaders.add("Content-Type");
		allowedHeaders.add("accept");
		allowedHeaders.add("X-PINGARUNER");
    	
	    Set<HttpMethod> allowedMethods = new HashSet<>();
	    allowedMethods.add(HttpMethod.GET);
	    allowedMethods.add(HttpMethod.POST);
	    allowedMethods.add(HttpMethod.OPTIONS);
	    allowedMethods.add(HttpMethod.DELETE);
	    allowedMethods.add(HttpMethod.PATCH);
	    allowedMethods.add(HttpMethod.PUT);

	    UiaAuthProvider authProvider = new UiaAuthProvider();
    	AuthHandler authHandler = BasicAuthHandler.create(authProvider);

    	Router router = Router.router(this.vertx);
    	router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));
    	router.route().handler(CookieHandler.create());
    	router.route().handler(SessionHandler.create(LocalSessionStore.create(this.vertx)));
    	router.route().handler(UserSessionHandler.create(authProvider));
        router.route().handler(StaticHandler.create());
        // router.route().handler(authHandler);
    	router.route().failureHandler(this::failed);
    	
    	UserService.bind(router, authHandler);
    	RoleService.bind(router, authHandler);
    	
    	this.server = this.vertx.createHttpServer()
    		.requestHandler(router)
    		.listen(18080);	
	}
    
    
    @Override
    public void stop() throws Exception {
    	this.server.close();
    	super.stop();
    }
    
    private void failed(RoutingContext ctx) {
		ctx.response()
			.setStatusCode(ctx.statusCode())
			.end("{ 'code': " + ctx.statusCode() + ",'error': '" + ctx.failure().getMessage() + "' }");
    }
}
