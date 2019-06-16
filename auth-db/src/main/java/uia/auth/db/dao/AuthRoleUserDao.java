package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthRoleUser;

public class AuthRoleUserDao {

    private static final String SQL_INS = "INSERT INTO auth_role_user(auth_user,auth_role) VALUES (?,?)";

    private static final String SQL_SEL = "SELECT auth_user,auth_role FROM auth_role_user ";

    private final Connection conn;

    public AuthRoleUserDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(long authUser, long authRole) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            ps.setLong(1, authUser);
            ps.setLong(2, authRole);

            return ps.executeUpdate();
        }
    }

    public int insert(List<AuthRoleUser> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            for (AuthRoleUser data : dataList) {
                ps.setLong(1, data.getAuthUser());
                ps.setLong(2, data.getAuthRole());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int delete(long authUser, long authRole) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("DELETE FROM auth_role_user WHERE auth_user=? AND auth_role=?")) {
            ps.setLong(1, authUser);
            ps.setLong(2, authRole);

            return ps.executeUpdate();
        }
    }

    public AuthRoleUser selectByPK(long authUser, long authRole) throws SQLException {
        AuthRoleUser result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_user=? AND auth_role=?")) {
            ps.setLong(1, authUser);
            ps.setLong(2, authRole);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthRoleUser> selectAll() throws SQLException {
        ArrayList<AuthRoleUser> result = new ArrayList<AuthRoleUser>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY auth_role,auth_user")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthRoleUser convert(ResultSet rs) throws SQLException {
        AuthRoleUser data = new AuthRoleUser();

        int index = 1;
        data.setAuthUser(rs.getLong(index++));
        data.setAuthRole(rs.getLong(index++));

        return data;
    }
}
