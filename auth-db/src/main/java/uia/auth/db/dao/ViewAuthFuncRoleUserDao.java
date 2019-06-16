package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthFuncRoleUserView;

public class ViewAuthFuncRoleUserDao {

    private static final String SQL_SEL = "SELECT auth_func,auth_role,access_type,func_name,func_description,role_name,role_enabled,auth_user,user_id,user_name,user_enabled FROM view_auth_func_role_user ";

    private final Connection conn;

    public ViewAuthFuncRoleUserDao(Connection conn) {
        this.conn = conn;
    }

    public List<AuthFuncRoleUserView> selectAll() throws SQLException {
        ArrayList<AuthFuncRoleUserView> result = new ArrayList<AuthFuncRoleUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE func_name=? AND user_id=? ORDER BY func_name,auth_role,auth_user")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public AuthFuncRoleUserView select(String funcName, String userId) throws SQLException {
        AuthFuncRoleUserView result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE func_name=? AND user_id=?")) {
            ps.setString(1, funcName);
            ps.setString(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthFuncRoleUserView> selectByUser(long authUser) throws SQLException {
        ArrayList<AuthFuncRoleUserView> result = new ArrayList<AuthFuncRoleUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_user=? ORDER BY func_name")) {
            ps.setLong(1, authUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncRoleUserView> selectByUser(String userId) throws SQLException {
        ArrayList<AuthFuncRoleUserView> result = new ArrayList<AuthFuncRoleUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE user_id=? ORDER BY func_name")) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthFuncRoleUserView convert(ResultSet rs) throws SQLException {
        AuthFuncRoleUserView data = new AuthFuncRoleUserView();

        int index = 1;
        data.setAuthFunc(rs.getLong(index++));
        data.setAuthRole(rs.getLong(index++));
        data.setAccessType(rs.getString(index++));
        data.setFuncName(rs.getString(index++));
        data.setFuncDescription(rs.getString(index++));
        data.setRoleName(rs.getString(index++));
        data.setRoleEnabled(rs.getString(index++));
        data.setAuthUser(rs.getLong(index++));
        data.setUserId(rs.getString(index++));
        data.setUserName(rs.getString(index++));
        data.setUserEnabled(rs.getString(index++));

        return data;
    }
}
