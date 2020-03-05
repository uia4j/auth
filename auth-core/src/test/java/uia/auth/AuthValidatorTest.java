package uia.auth;

import org.junit.Test;

import uia.auth.AuthFuncHelper;
import uia.auth.AuthValidator;

public class AuthValidatorTest {

    @Test
    public void test1() throws Exception {
        String userId = "authadm";
        try(AuthFuncHelper afHelper = new AuthFuncHelper()) {
            AuthValidator v1 = afHelper.validate(userId, "AUTH");
            System.out.println( v1.result() + "\n");

            AuthValidator v2 = afHelper.validate(userId, "AUTH.FUNC");
            System.out.println( v2.result() + "\n");

            AuthValidator v3 = afHelper.validate(userId, "AUTH.FUNC.QRY");
            System.out.println( v3.result() + "\n");
        }
    }

    @Test
    public void test2() throws Exception {
        String userId = "authuser";

        try(AuthFuncHelper afHelper = new AuthFuncHelper()) {
            AuthValidator v1 = afHelper.validate(userId, "AUTH");
            System.out.println( v1.result() + "\n");

            AuthValidator v2 = afHelper.validate(userId, "AUTH.FUNC");
            System.out.println( v2.result() + "\n");

            AuthValidator v3 = afHelper.validate(userId, "AUTH.FUNC.QRY");
            System.out.println( v3.result() + "\n");
	    }
    }
}
