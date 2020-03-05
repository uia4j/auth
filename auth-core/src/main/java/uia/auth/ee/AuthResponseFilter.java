package uia.auth.ee;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import uia.auth.ee.AuthHeaders;
import uia.auth.ee.Secured;

public final class AuthResponseFilter implements ContainerResponseFilter {

	
	@Context
	private HttpServletResponse resp;
	
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    	System.out.println("AuthResponseFilter...");
    	
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "auth-user, auth-session, auth-state, X-Requested-With, Content-Type, X-Codingpedia, Authorization");
        headers.add("Access-Control-Expose-Headers", "auth-user, auth-session, auth-state, reason, Authorized");

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate, private, s-maxage=0");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", 0);

        if (this.resourceInfo == null || this.resourceInfo.getResourceMethod() == null) {
            return;
        }
        
        
        Secured secured = this.resourceInfo.getResourceMethod().getAnnotation(Secured.class);
        if (secured != null) {
        	String user = requestContext.getHeaderString(AuthHeaders.USER);
        	if(!secured.admin() && user != null) {
                headers.putSingle(AuthHeaders.USER, user);
        	}

        	String session = requestContext.getHeaderString(AuthHeaders.SESSION);
        	if(session != null) {
                headers.putSingle(AuthHeaders.SESSION, session);
        	}
        	
        	System.out.println(user + ","  + session);
        }
    }
}
