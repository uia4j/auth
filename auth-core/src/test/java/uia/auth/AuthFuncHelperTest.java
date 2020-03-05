package uia.auth;

import java.util.List;

import org.junit.Test;

import uia.auth.AuthFuncHelper;
import uia.auth.AuthUserHelper;
import uia.auth.db.ViewAuthFuncRole;

public class AuthFuncHelperTest {

    @Test
    public void testScanNodes() throws Exception {
        final AuthFuncHelper afHelper = new AuthFuncHelper();
        afHelper.scanFuncNodes().forEach(f -> f.println(""));
        afHelper.close();
    }

    @Test
    public void testScanUserNodes() throws Exception {
        final AuthFuncHelper afHelper = new AuthFuncHelper();
        afHelper.scanUserFuncNodes(1).forEach(f -> f.println(""));
        afHelper.close();
    }

    @Test
    public void testFuncs() throws Exception {
        final AuthFuncHelper afHelper = new AuthFuncHelper();
        
        afHelper.searchFuncs().forEach(f -> {
        	System.out.println(f);
        	try {
        		afHelper.searchFuncUsers(f.getId()).forEach(u -> {
        			System.out.println(String.format("  %s: %20s", u.getAccessType(), u.getUserName()));
        		});
        		afHelper.searchFuncRoles(f.getId()).forEach(r -> {
        			System.out.println(String.format("  %s: %20s", r.getAccessType(), r.getRoleName()));
        		});
        	} catch (Exception e) {

			}
        });
        
        afHelper.close();
    }

    @Test
    public void testUsers() throws Exception {
        final AuthUserHelper auHelper = new AuthUserHelper();
        final AuthFuncHelper afHelper = new AuthFuncHelper();

        auHelper.searchUsers().forEach(u -> {
            System.out.println(u.getUserId());
            try {
                auHelper.searchUserRoles(u.getId()).forEach(r -> {
                    System.out.println(" role: " + r);
                });

                afHelper.searchUserFuncs(u.getUserId()).forEach(f -> {
                    System.out.println(" func: " + f);
                });
            }
            catch (Exception e) {
            }
        });

        auHelper.close();
        afHelper.close();
    }

    @Test
    public void testRoles() throws Exception {
        final AuthUserHelper auHelper = new AuthUserHelper();
        final AuthFuncHelper afHelper = new AuthFuncHelper();

        auHelper.searchRoles().forEach(r -> {
            try {
                List<ViewAuthFuncRole> result = afHelper.searchRoleFuncs(r.getId());
                System.out.println(r.getRoleName());
                for (ViewAuthFuncRole afr : result) {
                    System.out.println(" " + afr);
                }
            }
            catch (Exception e) {
            }
        });

        auHelper.close();
        afHelper.close();
    }
}
