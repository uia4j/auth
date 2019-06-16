package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthFuncRoleView;

public class ViewAuthFuncRoleDao {

    private static final String SQL_SEL = "SELECT auth_func,auth_role,access_type,func_name,func_description,role_name,role_enabled FROM view_auth_func_role ";

    private final Connection conn;

    public ViewAuthFuncRoleDao(Connection conn) {
        this.conn = conn;
    }

    public List<AuthFuncRoleView> selectAll() throws SQLException {
        ArrayList<AuthFuncRoleView> result = new ArrayList<AuthFuncRoleView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncRoleView> selectByFunc(long authFunc) throws SQLException {
        ArrayList<AuthFuncRoleView> result = new ArrayList<AuthFuncRoleView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_func=? ORDER BY auth_role")) {
            ps.setLong(1, authFunc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncRoleView> selectByFunc(String funcName) throws SQLException {
        ArrayList<AuthFuncRoleView> result = new ArrayList<AuthFuncRoleView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE func_name=? ORDER BY auth_role")) {
            ps.setString(1, funcName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncRoleView> selectByRole(long authRole) throws SQLException {
        ArrayList<AuthFuncRoleView> result = new ArrayList<AuthFuncRoleView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_role=? ORDER BY func_name")) {
            ps.setLong(1, authRole);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncRoleView> selectByRole(String roleName) throws SQLException {
        ArrayList<AuthFuncRoleView> result = new ArrayList<AuthFuncRoleView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE role_name=? ORDER BY func_name")) {
            ps.setString(1, roleName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthFuncRoleView convert(ResultSet rs) throws SQLException {
        AuthFuncRoleView data = new AuthFuncRoleView();

        int index = 1;
        data.setAuthFunc(rs.getLong(index++));
        data.setAuthRole(rs.getLong(index++));
        data.setAccessType(rs.getString(index++));
        data.setFuncName(rs.getString(index++));
        data.setFuncDescription(rs.getString(index++));
        data.setRoleName(rs.getString(index++));
        data.setRoleEnabled(rs.getString(index++));

        return data;
    }
}
