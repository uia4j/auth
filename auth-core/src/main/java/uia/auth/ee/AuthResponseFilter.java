package uia.auth.ee;

import java.io.IOException;

import javax.servlet.http.Cookie;
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
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "auth-user, auth-session, X-Requested-With, Content-Type, X-Codingpedia, Authorization");
        headers.add("Access-Control-Expose-Headers", "auth-user, auth-session");

        if (this.resourceInfo == null) {
            return;
        }
        
        Secured secured = this.resourceInfo.getResourceMethod().getAnnotation(Secured.class);
        if (secured != null) {
        	String user = requestContext.getHeaderString(AuthHeaders.USER);
        	String session = requestContext.getHeaderString(AuthHeaders.SESSION);
        	if(user != null) {
                headers.add(AuthHeaders.USER, user);
        	}
        	if(session != null) {
                headers.add(AuthHeaders.SESSION, session);
        	}
        }
    }
}
