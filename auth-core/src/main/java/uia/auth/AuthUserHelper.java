package uia.auth;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uia.auth.db.AuthRole;
import uia.auth.db.AuthRoleUser;
import uia.auth.db.AuthSecurity;
import uia.auth.db.ViewAuthSecurity;
import uia.auth.db.AuthUser;
import uia.auth.db.conf.AuthDB;
import uia.auth.db.dao.AuthRoleDao;
import uia.auth.db.dao.AuthRoleUserDao;
import uia.auth.db.dao.AuthSecurityDao;
import uia.auth.db.dao.ViewAuthSecurityDao;
import uia.dao.DaoException;
import uia.auth.db.dao.AuthUserDao;

public class AuthUserHelper implements Closeable {

    private Connection conn;

    private ViewAuthSecurityDao asvDao;

    private AuthSecurityDao asDao;

    private AuthUserDao userDao;

    private AuthRoleDao roleDao;

    private AuthRoleUserDao aruDao;

    public AuthUserHelper() throws SQLException {
        this.conn = AuthDB.create();
        this.asDao = new AuthSecurityDao(this.conn);
        this.asvDao = new ViewAuthSecurityDao(this.conn);
        this.userDao = new AuthUserDao(this.conn);
        this.roleDao = new AuthRoleDao(this.conn);
        this.aruDao = new AuthRoleUserDao(this.conn);
    }
    
    public ViewAuthSecurity udpateToken(String userId, long timeout) throws Exception {
    	timeout = Math.max(60000, timeout);
    	
    	ViewAuthSecurity security = this.asvDao.selectByUserId(userId);
		if(security == null) {
			AuthUser user = this.userDao.selectByUserId(userId);
			if(user == null) {
				return null;
			}
			
			security = new ViewAuthSecurity();
			// table data
			security.setAuthUser(user.getId());
			security.setPassword(encodePassword(user.getSeed(), "12345"));
    		security.setToken(user.getId() + "-" + UUID.randomUUID().toString());
    		security.setTokenExpired(new Date(System.currentTimeMillis() + timeout));
    		security.setTokenExpiredShort(new Date(System.currentTimeMillis() + 10000));
    		// view data
    		security.setUserId(user.getUserId());
    		security.setEnabled(user.getEnabled());
    		security.setSeed(user.getSeed());
    		
    		this.asDao.insert(security);
		}
		else {
    		security.setToken(security.getAuthUser() + "-" + UUID.randomUUID().toString());
    		security.setTokenExpired(new Date(System.currentTimeMillis() + timeout));
    		security.setTokenExpiredShort(new Date(System.currentTimeMillis() + 10000));
    		this.asDao.update(security);
		}
		
		return security;
    }
    
    
    public ViewAuthSecurity udpateTokenTime(String userId, long timeout) throws Exception {
    	timeout = Math.max(60000, timeout);
    	
    	ViewAuthSecurity security = this.asvDao.selectByUserId(userId);
		if(security == null) {
			AuthUser user = this.userDao.selectByUserId(userId);
			if(user == null) {
				return null;
			}
			
			security = new ViewAuthSecurity();
			// table data
			security.setAuthUser(user.getId());
			security.setPassword(encodePassword(user.getSeed(), "12345"));
    		security.setToken(user.getId() + "-" + UUID.randomUUID().toString());
    		security.setTokenExpired(new Date(System.currentTimeMillis() + timeout));
    		security.setTokenExpiredShort(new Date(System.currentTimeMillis() + 10000));
    		// view data
    		security.setUserId(user.getUserId());
    		security.setEnabled(user.getEnabled());
    		security.setSeed(user.getSeed());
    		
    		this.asDao.insert(security);
		}
		else {
    		security.setTokenExpired(new Date(System.currentTimeMillis() + timeout));
    		security.setTokenExpiredShort(new Date(System.currentTimeMillis() + 10000));
    		this.asDao.update(security);
		}
		
		return security;
    }

    public boolean validatePassword(String userId, String password) throws Exception {
    	ViewAuthSecurity as = this.asvDao.selectByUserId(userId);
    	if(as == null) {
    		return false;
    	}

    	return as.getPassword().equals(encodePassword(as.getSeed(), password));
    }
    
    public void chanagePassword(long authUser, String password) throws Exception {
    	AuthUser user = this.userDao.selectByPK(authUser);
    	if(user != null) {
    		conn.setAutoCommit(false);

    		// seed
    		String seed = UUID.randomUUID().toString();
        	user.setSeed(seed);
        	// password
        	AuthSecurity as = this.asDao.selectByPK(user.getId());
        	as.setPassword(encodePassword(seed, password));
        	
        	this.userDao.update(user);
        	this.asDao.update(as);

        	conn.commit();
    	}
    }
    
    public void chanagePassword(String userId, String password) throws Exception {
    	AuthUser user = this.userDao.selectByUserId(userId);
    	if(user != null) {
    		conn.setAutoCommit(false);

    		// seed
    		String seed = UUID.randomUUID().toString();
        	user.setSeed(seed);
        	// password
        	AuthSecurity as = this.asDao.selectByPK(user.getId());
        	as.setPassword(encodePassword(seed, password));
        	
        	this.userDao.update(user);
        	this.asDao.update(as);

        	conn.commit();
    	}
    }

    public void insertUser(AuthUser user) throws SQLException, DaoException {
        if (this.userDao.selectByUserId(user.getUserId()) == null) {
            this.userDao.insert(user);
        }
    }

    public void updateUser(AuthUser user) throws SQLException, DaoException {
        AuthUser tmp = this.userDao.selectByPK(user.getId());
        if (tmp != null && tmp.getUserId().equals(user.getUserId())) {
            this.userDao.update(user);
        }
    }

    public void deleteUser(long authUser) throws SQLException {
        this.userDao.deleteByPK(authUser);
    }

    public void deleteUser(String userId) throws SQLException {
        this.userDao.deletebByUserId(userId);
    } 

    public AuthUser searchUser(long authUser) throws SQLException, DaoException {
        return this.userDao.selectByPK(authUser);
    }

    public AuthUser searchUser(String userId) throws SQLException, DaoException {
        return this.userDao.selectByUserId(userId);
    }

    public List<AuthUser> searchUsers() throws SQLException, DaoException {
        return this.userDao.selectAll();
    }

    public void insertSecurity(AuthSecurity security) throws SQLException, DaoException {
        this.asDao.insert(security);
    }

    public void updateSecurity(AuthSecurity security) throws SQLException, DaoException {
        this.asDao.update(security);
    }
    
    public ViewAuthSecurity searchSecurityByUserId(String userId) throws SQLException, DaoException {
		return this.asvDao.selectByUserId(userId);
    }
    
    public ViewAuthSecurity searchSecurityBySession(String session) throws SQLException, DaoException {
		return this.asvDao.selectBySession(session);
    }

    public void insertRole(AuthRole role) throws SQLException, DaoException {
        if (this.roleDao.selectByName(role.getRoleName()) == null) {
            this.roleDao.insert(role);
        }
    }

    public void updateRole(AuthRole role) throws SQLException, DaoException {
        AuthRole authRole = this.roleDao.selectByName(role.getRoleName());
        if (authRole != null && authRole.getId() == role.getId()) {
            this.roleDao.update(role);
        }
    }

    public void deleteRole(long authRole) throws SQLException {
        this.roleDao.deleteByPK(authRole);
    }

    public AuthRole searchRole(long authRole) throws SQLException, DaoException {
        return this.roleDao.selectByPK(authRole);
    }

    public AuthRole searchRole(String roleName) throws SQLException, DaoException {
        return this.roleDao.selectByName(roleName);
    }

    public List<AuthRole> searchRoles() throws SQLException, DaoException {
        return this.roleDao.selectAll();
    }

    public List<AuthRole> searchUserRoles(long authUser) throws SQLException, DaoException {
        return this.roleDao.selectByUser(authUser);
    }

    public List<AuthRole> searchUserRoles(String userId) throws SQLException, DaoException {
    	AuthUser user = this.userDao.selectByUserId(userId);
        return user  == null ? new ArrayList<>() : this.roleDao.selectByUser(user.getId());
    }

    public List<AuthUser> searchRoleUsers(long authRole) throws SQLException, DaoException {
        return this.userDao.selectByRole(authRole);
    }

    public void link(long authUser, long authRole) throws SQLException, DaoException {
    	AuthRoleUser aru = new AuthRoleUser();
    	aru.setAuthUser(authUser);
    	aru.setAuthRole(authRole);
        this.aruDao.insert(aru);
    }

    public void unlink(long authUser, long authRole) throws SQLException {
        this.aruDao.deleteByPK(authUser, authRole);
    }

    @Override
    public void close() throws IOException {
        try {
            this.conn.close();
        }
        catch (Exception ex) {

        }
    }
    
    private String encodePassword(String seed, String pwd) throws Exception {
    	return SecureHash.md5(seed + pwd);    
	}
}
