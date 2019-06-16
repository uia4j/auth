package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthRole;

public class AuthRoleDao {

    private static final String SQL_INS = "INSERT INTO auth_role(id,role_name,enabled) VALUES (?,?,?)";

    private static final String SQL_UPD = "UPDATE auth_role SET role_name=?,enabled=? WHERE id=?";

    private static final String SQL_SEL = "SELECT id,role_name,enabled FROM auth_role ";

    private static final String SQL_SEL2 = "SELECT r.id,r.role_name,r.enabled FROM auth_role r,auth_role_user ru WHERE r.id=ru.auth_role ";

    private final Connection conn;

    public AuthRoleDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(AuthRole data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            ps.setLong(1, data.getId());
            ps.setString(2, data.getRoleName());
            ps.setString(3, data.getEnabled());

            return ps.executeUpdate();
        }
    }

    public int insert(List<AuthRole> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            for (AuthRole data : dataList) {
                ps.setLong(1, data.getId());
                ps.setString(2, data.getRoleName());
                ps.setString(3, data.getEnabled());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int update(AuthRole data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            ps.setString(1, data.getRoleName());
            ps.setString(2, data.getEnabled());
            ps.setLong(3, data.getId());

            return ps.executeUpdate();
        }
    }

    public int update(List<AuthRole> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            for (AuthRole data : dataList) {
                ps.setString(1, data.getRoleName());
                ps.setString(2, data.getEnabled());
                ps.setLong(3, data.getId());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int delete(long id) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("UPDATE auth_role SET enabled='N' WHERE id=?")) {
            ps.setLong(1, id);

            return ps.executeUpdate();
        }
    }

    public AuthRole selectByPK(long id) throws SQLException {
        AuthRole result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE id=?")) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public AuthRole selectByName(String roleName) throws SQLException {
        AuthRole result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE role_name=?")) {
            ps.setString(1, roleName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthRole> selectAll() throws SQLException {
        ArrayList<AuthRole> result = new ArrayList<AuthRole>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY role_name")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthRole> selectByUser(long authUser) throws SQLException {
        ArrayList<AuthRole> result = new ArrayList<AuthRole>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL2 + "AND ru.auth_user=? ORDER BY r.role_name")) {
            ps.setLong(1, authUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthRole convert(ResultSet rs) throws SQLException {
        AuthRole data = new AuthRole();

        int index = 1;
        data.setId(rs.getLong(index++));
        data.setRoleName(rs.getString(index++));
        data.setEnabled(rs.getString(index++));

        return data;
    }
}
