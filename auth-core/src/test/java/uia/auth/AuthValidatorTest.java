package uia.auth;

import org.junit.Test;

import uia.auth.AuthFuncHelper;
import uia.auth.AuthValidator;

public class AuthValidatorTest {

    @Test
    public void test1() throws Exception {
        String userId = "mdsuser";
        try(AuthFuncHelper afHelper = new AuthFuncHelper()) {
            AuthValidator v1 = afHelper.validate(userId, "DISP");
            System.out.println( v1.result() + "\n");

            AuthValidator v2 = afHelper.validate(userId, "DISP.MDS-HLIS-D711");
            System.out.println( v2.result() + "\n");

            AuthValidator v3 = afHelper.validate(userId, "DISP.MDS-HLIS-D712");
            System.out.println( v3.result() + "\n");

            AuthValidator v4 = afHelper.validate(userId, "AUTH.FUNC.UPDATE");
            System.out.println( v4.result() + "\n");
        }
    }

    @Test
    public void test2() throws Exception {
        String userId = "Kyle";

        try(AuthFuncHelper afHelper = new AuthFuncHelper()) {
	        AuthValidator v1 = afHelper.validate(userId, "PMSUI");
	        System.out.println( v1.result());
	
	        AuthValidator v2 = afHelper.validate(userId, "PMSUI.REPAIR.VIEW");
	        System.out.println( v2.result());
	    }
    }
}
