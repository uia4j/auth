package uia.auth;

import org.junit.Test;

import uia.auth.AuthValidator.AccessType;

public class AccessTypeTest {

	@Test
	public void test() {
		System.out.println(AccessType.valueOf("WRITE"));
		System.out.println(AccessType.valueOf("READONLY"));
		System.out.println(AccessType.valueOf("SELF"));
		System.out.println(AccessType.valueOf("DENY"));
		System.out.println(AccessType.valueOf("UNKNOWN"));
		System.out.println(AccessType.valueOf("UN"));
	}
}
