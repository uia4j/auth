package uia.auth;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uia.auth.AuthValidator.AccessType;
import uia.auth.db.AuthFunc;
import uia.auth.db.AuthFuncRole;
import uia.auth.db.ViewAuthFuncRoleUser;
import uia.auth.db.ViewAuthFuncRole;
import uia.auth.db.AuthFuncUser;
import uia.auth.db.ViewAuthFuncUser;
import uia.auth.db.AuthUser;
import uia.auth.db.conf.AuthDB;
import uia.auth.db.dao.AuthFuncDao;
import uia.auth.db.dao.AuthFuncRoleDao;
import uia.auth.db.dao.ViewAuthFuncRoleUserDao;
import uia.auth.db.dao.ViewAuthFuncRoleDao;
import uia.auth.db.dao.AuthFuncUserDao;
import uia.auth.db.dao.AuthUserDao;
import uia.auth.db.dao.ViewAuthFuncUserDao;
import uia.dao.DaoException;

public class AuthFuncHelper implements Closeable {
	
    private static final Logger LOGGER = LogManager.getLogger(AuthFuncHelper.class);

    private Connection conn;

    private AuthFuncDao funcDao;

    private AuthFuncRoleDao frDao;

    private AuthFuncUserDao fuDao;

    private ViewAuthFuncRoleDao frvDao;

    private ViewAuthFuncUserDao fuvDao;

    private ViewAuthFuncRoleUserDao fruvDao;

    public AuthFuncHelper() throws SQLException {
        this.conn = AuthDB.create();
        this.funcDao = new AuthFuncDao(this.conn);
        this.frDao = new AuthFuncRoleDao(this.conn);
        this.fuDao = new AuthFuncUserDao(this.conn);
        this.frvDao = new ViewAuthFuncRoleDao(this.conn);
        this.fuvDao = new ViewAuthFuncUserDao(this.conn);
        this.fruvDao = new ViewAuthFuncRoleUserDao(this.conn);
    }
    
    public AuthValidator validator(long authUser) throws SQLException, DaoException {
    	AuthUser user = new AuthUserDao(this.conn).selectByPK(authUser);
        return new AuthValidator(this.conn, user.getUserId());
    }

    public AuthValidator validate(String userId) {
        return new AuthValidator(this.conn, userId);
    }

    public AuthValidator validate(String userId, String funcName) throws SQLException, DaoException {
        AuthValidator aor = new AuthValidator(this.conn, userId);
        return aor.and(funcName);
    }

    public void insertFunc(AuthFunc func) throws SQLException, DaoException {
     	if(func.getId() == func.getParentFunc()) {
    		throw new SQLException("id is same as parent_func");
    	}
     	if(func.getParentFunc() > 0 && this.funcDao.selectByPK(func.getParentFunc()) == null) {
    		throw new SQLException("parent_func NOT FOUND");
     	}
        this.funcDao.insert(func);
    }

    public void updateFunc(AuthFunc func) throws SQLException, DaoException {
     	if(func.getId() == func.getParentFunc()) {
    		throw new SQLException("id is same as parent_func");
    	}
     	if(func.getParentFunc() > 0 && this.funcDao.selectByPK(func.getParentFunc()) == null) {
    		throw new SQLException("parent_func NOT FOUND");
     	}
        this.funcDao.update(func);
    }

    public void deleteFunc(long authFunc) throws SQLException, DaoException {
        try {
            this.conn.setAutoCommit(false);

            for (AuthFunc func : this.funcDao.searchNexts(authFunc)) {
                this.frDao.delete(func.getId());
                this.fuDao.delete(func.getId());
                this.funcDao.deleteByPK(func.getId());
            }

            this.frDao.delete(authFunc);
            this.fuDao.delete(authFunc);
            this.funcDao.deleteByPK(authFunc);

            this.conn.commit();
        }
        catch (Exception ex) {
            this.conn.rollback();
            throw ex;
        }
    }

    public List<AuthFunc> searchFuncs() throws SQLException, DaoException {
        return this.funcDao.selectAll();
    }

    public List<AuthFuncNode> searchFuncs(String funcName, int deep) throws SQLException, DaoException {
    	ArrayList<AuthFuncNode> nodes = new ArrayList<>();

    	List<AuthFunc> funcs =  this.funcDao.searchParents(funcName);
    	for(AuthFunc func : funcs) {
        	AuthFuncNode node = new AuthFuncNode(
        			func.getId(), 
        			func.getFuncName(), 
        			func.getFuncDescription(), 
        			func.getFuncArgs(), 
        			func.getParentFunc());
        	
        	nodes.add(node);
    	}
    	
    	return nodes;
    }

    public AuthFunc searchFunc(long authFunc) throws SQLException, DaoException {
        return this.funcDao.selectByPK(authFunc);
    }

    public List<ViewAuthFuncRole> searchFuncRoles(long authFunc) throws SQLException, DaoException {
        return this.frvDao.selectByFunc(authFunc);
    }

    public List<ViewAuthFuncRole> searchFuncRoles(String funcName) throws SQLException, DaoException {
        return this.frvDao.selectByFunc(funcName);
    }

    /**
     * Builds a function tree.
     * 
     * @return Root nodes.
     * @throws SQLException Failed to build a function tree.
     */
    public List<AuthFuncNode> scanFuncNodes() throws SQLException, DaoException {
    	return searchFuncNodesMap().values()
    			.stream()
				.filter(n -> n.isFirst())
				.collect(Collectors.toList());
    }

    /**
     * Builds a specific role's function tree with access information.
     * 
     * @param authRole The role id.
     * @return Root nodes.
     * @throws SQLException Failed to build a function tree.
     */
    public List<AuthFuncNode> scanRoleFuncNodes(long authRole) throws SQLException, DaoException {
    	Map<Long, AuthFuncNode> nodes = searchFuncNodesMap();
    	
    	List<ViewAuthFuncRole> frvs = this.frvDao.selectByRole(authRole);
    	for(ViewAuthFuncRole frv: frvs) {
    		AuthFuncNode node = nodes.get(frv.getAuthFunc());
    		if(node == null) {
    			continue;
    		}

    		if(AccessType.DENY.code.equalsIgnoreCase(frv.getAccessType())) {
    			node.setAccessType(AccessType.DENY, frv.getRoleName());
    		}
    		else if(AccessType.READONLY.code.equalsIgnoreCase(frv.getAccessType())) {
    			node.setAccessType(AccessType.READONLY, frv.getRoleName());
    		}
    		else if(AccessType.SELF.code.equalsIgnoreCase(frv.getAccessType())) {
    			node.setAccessType(AccessType.SELF, frv.getRoleName());
    		}
    		else if(AccessType.WRITE.code.equalsIgnoreCase(frv.getAccessType())) {
    			node.setAccessType(AccessType.WRITE, frv.getRoleName());
    		}
    	}
    	
    	return nodes.values()
    			.stream()
    			.filter(n -> n.isFirst())
    			.collect(Collectors.toList());
    }
    
    public Map<String, AuthValidator.UserAccessInfo> scan(String userId, String funcName) throws SQLException, DaoException {
    	TreeMap<String, AuthValidator.UserAccessInfo> result = new TreeMap<>();
    	AuthFunc root = this.funcDao.selectByName(funcName);
    	if(root == null) {
    		return result;
    	}

    	AuthValidator av1 = new AuthValidator(this.conn, userId).and(root.getFuncName());
    	result.put(
    			root.getFuncName(), 
    			av1.resultInfo(root.getFuncArgs(), av1.getPower().getArgs()));
    	List<AuthFunc> afs = this.funcDao.searchNexts(root.getId());
    	for(AuthFunc af : afs) {
    		AuthValidator av2 = new AuthValidator(this.conn, userId).and(af.getFuncName());
        	result.put(
        			af.getFuncName(), 
        			av2.resultInfo(root.getFuncArgs(), av2.getPower().getArgs()));
    	}
    	
    	return result;
    }
    
    /**
     * Builds a specific user's function tree with access information.
     * 
     * @param userId The user id.
     * @return Root nodes.
     * @throws SQLException Failed to build a function tree.
     */
    public List<AuthFuncNode> scanUserFuncNodes(long authUser) throws SQLException, DaoException {
    	Map<Long, AuthFuncNode> nodes = searchFuncNodesMap();
    	
    	List<ViewAuthFuncRoleUser> fruvs = this.fruvDao.selectByUser(authUser);
    	for(ViewAuthFuncRoleUser fruv: fruvs) {
    		AuthFuncNode node = nodes.get(fruv.getAuthFunc());
    		if(node == null) {
    			continue;
    		}

    		if(AccessType.DENY.code.equalsIgnoreCase(fruv.getAccessType())) {
        		node.setAccessType(AccessType.DENY, fruv.getRoleName());
    		}
    		else if(AccessType.READONLY.code.equalsIgnoreCase(fruv.getAccessType())) {
        		node.setAccessType(AccessType.READONLY, fruv.getRoleName());
    		}
    		else if(AccessType.SELF.code.equalsIgnoreCase(fruv.getAccessType())) {
        		node.setAccessType(AccessType.SELF, fruv.getRoleName());
    		}
    		else if(AccessType.WRITE.code.equalsIgnoreCase(fruv.getAccessType())) {
        		node.setAccessType(AccessType.WRITE, fruv.getRoleName());
    		}
    	}

    	List<ViewAuthFuncUser> fuvs = this.fuvDao.selectByUser(authUser);
    	for(ViewAuthFuncUser fuv: fuvs) {
    		AuthFuncNode node = nodes.get(fuv.getAuthFunc());
    		if(node == null) {
    			continue;
    		}

    		if(AccessType.DENY.code.equalsIgnoreCase(fuv.getAccessType())) {
        		node.setAccessType(AccessType.DENY, "User");
    		}
    		else if(AccessType.READONLY.code.equalsIgnoreCase(fuv.getAccessType())) {
        		node.setAccessType(AccessType.READONLY, "User");
    		}
    		else if(AccessType.SELF.code.equalsIgnoreCase(fuv.getAccessType())) {
        		node.setAccessType(AccessType.SELF, "User");
    		}
    		else if(AccessType.WRITE.code.equalsIgnoreCase(fuv.getAccessType())) {
        		node.setAccessType(AccessType.WRITE, "User");
    		}
    	}
    	
    	return nodes.values()
    			.stream()
    			.filter(n -> n.isFirst())
    			.collect(Collectors.toList());
    }

    public List<ViewAuthFuncRole> searchRoleFuncs(long authRole) throws SQLException, DaoException {
        return this.frvDao.selectByRole(authRole);
    }

    public List<ViewAuthFuncRole> searchRoleFuncs(String roleName) throws SQLException, DaoException {
        return this.frvDao.selectByRole(roleName);
    }

    public void insertFuncRole(AuthFuncRole fr) throws SQLException, DaoException {
        this.frDao.insert(fr);
    }

    public void updateFuncRole(AuthFuncRole fr) throws SQLException, DaoException {
        this.frDao.update(fr);
    }

    public void deleteFuncRole(long authFunc, long authRole) throws SQLException {
        this.frDao.deleteByPK(authFunc, authRole);
    }

    public List<ViewAuthFuncUser> searchFuncUsers(long authFunc) throws SQLException, DaoException {
        return this.fuvDao.selectByFunc(authFunc);
    }

    public List<ViewAuthFuncUser> searchFuncUsers(String funcName) throws SQLException, DaoException {
        return this.fuvDao.selectByFunc(funcName);
    }

    public List<ViewAuthFuncUser> searchUserFuncs(long authUser) throws SQLException, DaoException {
        return this.fuvDao.selectByUser(authUser);
    }

    public List<ViewAuthFuncUser> searchUserFuncs(String userId) throws SQLException, DaoException {
        return this.fuvDao.selectByUser(userId);
    }

    public void insertFuncUser(AuthFuncUser fu) throws SQLException, DaoException {
        this.fuDao.insert(fu);
    }

    public void updateFuncUser(AuthFuncUser fu) throws SQLException, DaoException {
        this.fuDao.update(fu);
    }
 
    public void deleteFuncUser(long authFunc, long authUser) throws SQLException {
        this.fuDao.deleteByPK(authFunc, authUser);
    }

    private Map<Long, AuthFuncNode> searchFuncNodesMap() throws SQLException, DaoException {
    	TreeMap<Long, AuthFuncNode> result = new TreeMap<>();

        List<AuthFunc> afs = this.funcDao.selectAll();
        for(AuthFunc af : afs) {
        	AuthFuncNode node = new AuthFuncNode(
        			af.getId(), 
        			af.getFuncName(), 
        			af.getFuncDescription(),
        			af.getFuncArgs(),
        			af.getParentFunc());
        	result.put(af.getId(), node);
        	
        	AuthFuncNode pn = result.get(af.getParentFunc());
        	if(pn == null) {
        		LOGGER.debug(String.format("auth> %s parent:%s not found", af.getFuncName(), af.getParentFunc()));
        	}	
        	else {
        		node.setLevel(pn.getLevel() + 1);
        		pn.getNodes().add(node);
        	}
        }
        
        return result;
    }

    @Override
    public void close() throws IOException {
        try {
            this.conn.close();
        }
        catch (Exception ex) {

        }
    }
}
