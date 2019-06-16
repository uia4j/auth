package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthUser;

public class AuthUserDao {

    private static final String SQL_INS = "INSERT INTO auth_user(id,user_id,user_name,enabled,seed,mobile_no,email) VALUES (?,?,?,?,?,?,?)";

    private static final String SQL_UPD = "UPDATE auth_user SET user_id=?,user_name=?,enabled=?,seed=?,mobile_no=?,email=? WHERE id=?";

    private static final String SQL_SEL = "SELECT id,user_id,user_name,enabled,seed,mobile_no,email FROM auth_user ";

    private static final String SQL_SEL2 = "SELECT u.id,u.user_id,u.user_name,u.enabled,u.seed,u.mobile_no,u.email FROM auth_user u,auth_role_user ru WHERE u.id=ru.auth_user ";

    private final Connection conn;

    public AuthUserDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(AuthUser data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            ps.setLong(1, data.getId());
            ps.setString(2, data.getUserId());
            ps.setString(3, data.getUserName());
            ps.setString(4, data.getEnabled());
            ps.setString(5, data.getSeed());
            ps.setString(6, data.getMobileNo());
            ps.setString(7, data.getEmail());

            return ps.executeUpdate();
        }
    }

    public int insert(List<AuthUser> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            for (AuthUser data : dataList) {
                ps.setLong(1, data.getId());
                ps.setString(2, data.getUserId());
                ps.setString(3, data.getUserName());
                ps.setString(4, data.getEnabled());
                ps.setString(5, data.getSeed());
                ps.setString(6, data.getMobileNo());
                ps.setString(7, data.getEmail());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int update(AuthUser data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            ps.setString(1, data.getUserId());
            ps.setString(2, data.getUserName());
            ps.setString(3, data.getEnabled());
            ps.setString(4, data.getSeed());
            ps.setString(5, data.getMobileNo());
            ps.setString(6, data.getEmail());
            ps.setLong(7, data.getId());

            return ps.executeUpdate();
        }
    }

    public int update(List<AuthUser> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            for (AuthUser data : dataList) {
                ps.setString(1, data.getUserId());
                ps.setString(2, data.getUserName());
                ps.setString(3, data.getEnabled());
                ps.setString(4, data.getSeed());
                ps.setString(5, data.getMobileNo());
                ps.setString(6, data.getEmail());
                ps.setLong(7, data.getId());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int delete(long id) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("UPDATE auth_user SET enabled='N' WHERE id=?")) {
            ps.setLong(1, id);

            return ps.executeUpdate();
        }
    }

    public int deletebByUserId(String userId) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("UPDATE auth_user SET enabled='N' WHERE user_id=?")) {
            ps.setString(1, userId);

            return ps.executeUpdate();
        }
    }

    public AuthUser selectByPK(long id) throws SQLException {
        AuthUser result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE id=?")) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public AuthUser selectByUserId(String userId) throws SQLException {
        AuthUser result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE user_id=?")) {
            ps.setString(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthUser> selectAll() throws SQLException {
        ArrayList<AuthUser> result = new ArrayList<AuthUser>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY user_id")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthUser> selectByRole(long authRole) throws SQLException {
        ArrayList<AuthUser> result = new ArrayList<AuthUser>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL2 + "AND ru.auth_role=? ORDER BY u.user_id")) {
            ps.setLong(1, authRole);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthUser convert(ResultSet rs) throws SQLException {
        AuthUser data = new AuthUser();

        int index = 1;
        data.setId(rs.getLong(index++));
        data.setUserId(rs.getString(index++));
        data.setUserName(rs.getString(index++));
        data.setEnabled(rs.getString(index++));
        data.setSeed(rs.getString(index++));
        data.setMobileNo(rs.getString(index++));
        data.setEmail(rs.getString(index++));

        return data;
    }
}
