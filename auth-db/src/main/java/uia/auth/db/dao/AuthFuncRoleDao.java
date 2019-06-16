package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthFuncRole;

public class AuthFuncRoleDao {

    private static final String SQL_INS = "INSERT INTO auth_func_role(auth_func,auth_role,access_type) VALUES (?,?,?)";

    private static final String SQL_UPD = "UPDATE auth_func_role SET access_type=? WHERE auth_func=? AND auth_role=?";

    private static final String SQL_SEL = "SELECT auth_func,auth_role,access_type FROM auth_func_role ";

    private final Connection conn;

    public AuthFuncRoleDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(AuthFuncRole data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            ps.setLong(1, data.getAuthFunc());
            ps.setLong(2, data.getAuthRole());
            ps.setString(3, data.getAccessType());

            return ps.executeUpdate();
        }
    }

    public int insert(List<AuthFuncRole> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            for (AuthFuncRole data : dataList) {
                ps.setLong(1, data.getAuthFunc());
                ps.setLong(2, data.getAuthRole());
                ps.setString(3, data.getAccessType());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int update(AuthFuncRole data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            ps.setString(1, data.getAccessType());
            ps.setLong(2, data.getAuthFunc());
            ps.setLong(3, data.getAuthRole());

            return ps.executeUpdate();
        }
    }

    public int update(List<AuthFuncRole> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            for (AuthFuncRole data : dataList) {
                ps.setString(1, data.getAccessType());
                ps.setLong(2, data.getAuthFunc());
                ps.setLong(3, data.getAuthRole());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int delete(long authFunc) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("DELETE FROM auth_func_role WHERE auth_func=? ")) {
            ps.setLong(1, authFunc);

            return ps.executeUpdate();
        }
    }

    public int delete(long authFunc, long authRole) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("DELETE FROM auth_func_role WHERE auth_func=? AND auth_role=?")) {
            ps.setLong(1, authFunc);
            ps.setLong(2, authRole);

            return ps.executeUpdate();
        }
    }

    public AuthFuncRole selectByPK(long authFunc, long authRole) throws SQLException {
        AuthFuncRole result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_func=? AND auth_role=?")) {
            ps.setLong(1, authFunc);
            ps.setLong(2, authRole);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthFuncRole> selectAll() throws SQLException {
        ArrayList<AuthFuncRole> result = new ArrayList<AuthFuncRole>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY auth_func,auth_role")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncRole> selectByRole(long authRole) throws SQLException {
        ArrayList<AuthFuncRole> result = new ArrayList<AuthFuncRole>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_role=? ORDER BY auth_func")) {
            ps.setLong(1, authRole);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncRole> selectByFunc(long authFunc) throws SQLException {
        ArrayList<AuthFuncRole> result = new ArrayList<AuthFuncRole>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_func=? ORDER BY auth_role")) {
            ps.setLong(1, authFunc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthFuncRole convert(ResultSet rs) throws SQLException {
        AuthFuncRole data = new AuthFuncRole();

        int index = 1;
        data.setAuthFunc(rs.getLong(index++));
        data.setAuthRole(rs.getLong(index++));
        data.setAccessType(rs.getString(index++));

        return data;
    }
}
