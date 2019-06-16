package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthFuncUser;

public class AuthFuncUserDao {

    private static final String SQL_INS = "INSERT INTO auth_func_user(auth_func,auth_user,access_type) VALUES (?,?,?)";

    private static final String SQL_UPD = "UPDATE auth_func_user SET access_type=? WHERE auth_func=? AND auth_user=?";

    private static final String SQL_SEL = "SELECT auth_func,auth_user,access_type FROM auth_func_user ";

    private final Connection conn;

    public AuthFuncUserDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(AuthFuncUser data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            ps.setLong(1, data.getAuthFunc());
            ps.setLong(2, data.getAuthUser());
            ps.setString(3, data.getAccessType());

            return ps.executeUpdate();
        }
    }

    public int insert(List<AuthFuncUser> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            for (AuthFuncUser data : dataList) {
                ps.setLong(1, data.getAuthFunc());
                ps.setLong(2, data.getAuthUser());
                ps.setString(3, data.getAccessType());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int update(AuthFuncUser data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            ps.setString(1, data.getAccessType());
            ps.setLong(2, data.getAuthFunc());
            ps.setLong(3, data.getAuthUser());

            return ps.executeUpdate();
        }
    }

    public int update(List<AuthFuncUser> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            for (AuthFuncUser data : dataList) {
                ps.setString(1, data.getAccessType());
                ps.setLong(2, data.getAuthFunc());
                ps.setLong(3, data.getAuthUser());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int delete(long authFunc) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("DELETE FROM auth_func_user WHERE auth_func=? ")) {
            ps.setLong(1, authFunc);
            return ps.executeUpdate();
        }
    }

    public int delete(long authFunc, long authUser) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("DELETE FROM auth_func_user WHERE auth_func=? AND auth_user=?")) {
            ps.setLong(1, authFunc);
            ps.setLong(2, authUser);

            return ps.executeUpdate();
        }
    }

    public AuthFuncUser selectByPK(long authFunc, long authUser) throws SQLException {
        AuthFuncUser result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_func=? AND auth_user=?")) {
            ps.setLong(1, authFunc);
            ps.setLong(2, authUser);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthFuncUser> selectAll() throws SQLException {
        ArrayList<AuthFuncUser> result = new ArrayList<AuthFuncUser>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY auth_func,auth_user")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncUser> selectByUser(long authUser) throws SQLException {
        ArrayList<AuthFuncUser> result = new ArrayList<AuthFuncUser>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_user=? ORDER BY auth_func")) {
            ps.setLong(1, authUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncUser> selectByFunc(long authFunc) throws SQLException {
        ArrayList<AuthFuncUser> result = new ArrayList<AuthFuncUser>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_func=? ORDER BY auth_user")) {
            ps.setLong(1, authFunc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthFuncUser convert(ResultSet rs) throws SQLException {
        AuthFuncUser data = new AuthFuncUser();

        int index = 1;
        data.setAuthFunc(rs.getLong(index++));
        data.setAuthUser(rs.getLong(index++));
        data.setAccessType(rs.getString(index++));

        return data;
    }
}
