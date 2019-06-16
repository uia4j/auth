package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthSecurity;

public class AuthSecurityDao {

    private static final String SQL_INS = "INSERT INTO auth_security(auth_user,password,token,token_expired,token_expired_short) VALUES (?,?,?,?,?)";

    private static final String SQL_UPD = "UPDATE auth_security SET password=?,token=?,token_expired=?,token_expired_short=? WHERE auth_user=?";

    private static final String SQL_SEL = "SELECT auth_user,password,token,token_expired,token_expired_short FROM auth_security ";
    
    private final Connection conn;

    public AuthSecurityDao(Connection conn) {
        this.conn = conn;
    }
    
    public int insert(AuthSecurity data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            ps.setLong(1, data.getAuthUser());
            ps.setString(2, data.getPassword());
            ps.setString(3, data.getToken());
            ps.setTimestamp(4, new Timestamp(data.getTokenExpired().getTime()));
            ps.setTimestamp(5, new Timestamp(data.getTokenExpiredShort().getTime()));

            return ps.executeUpdate();
        }
    }    
    
    public int insert(List<AuthSecurity> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            for(AuthSecurity data : dataList) {
            ps.setLong(1, data.getAuthUser());
            ps.setString(2, data.getPassword());
            ps.setString(3, data.getToken());
            ps.setTimestamp(4, new Timestamp(data.getTokenExpired().getTime()));
            ps.setTimestamp(5, new Timestamp(data.getTokenExpiredShort().getTime()));

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }    

    public int update(AuthSecurity data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            ps.setString(1, data.getPassword());
            ps.setString(2, data.getToken());
            ps.setTimestamp(3, new Timestamp(data.getTokenExpired().getTime()));
            ps.setTimestamp(4, new Timestamp(data.getTokenExpiredShort().getTime()));
            ps.setLong(5, data.getAuthUser());

            return ps.executeUpdate();
        }
    }    

    public int update(List<AuthSecurity> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            for(AuthSecurity data : dataList) {
            ps.setString(1, data.getPassword());
            ps.setString(2, data.getToken());
            ps.setTimestamp(3, new Timestamp(data.getTokenExpired().getTime()));
            ps.setTimestamp(4, new Timestamp(data.getTokenExpiredShort().getTime()));
            ps.setLong(4, data.getAuthUser());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }    

    public int updatePassword(long authUser, String password) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("UPDATE auth_security SET pwd=?,token_expired=? WHERE auth_user=?")) {
            ps.setString(1, password);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setLong(3, authUser);

            return ps.executeUpdate();
        }
    }

    public int delete(long authUser) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("DELETE FROM auth_security WHERE auth_user=?")) {
            ps.setLong(1, authUser);

            return ps.executeUpdate();
        }
    }    

    public AuthSecurity selectByPK(long authUser) throws SQLException {
        AuthSecurity result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL  + "WHERE auth_user=?")) {
            ps.setLong(1, authUser);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }    

    public AuthSecurity selectBySession(String session) throws SQLException {
        AuthSecurity result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL  + "WHERE token=?")) {
            ps.setString(1, session);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }    

    public List<AuthSecurity> selectAll() throws SQLException {
        ArrayList<AuthSecurity> result = new ArrayList<AuthSecurity>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }    
    
    private AuthSecurity convert(ResultSet rs) throws SQLException {
        AuthSecurity data = new AuthSecurity();
        
        int index = 1;
        data.setAuthUser(rs.getLong(index++));
        data.setPassword(rs.getString(index++));
        data.setToken(rs.getString(index++));
        data.setTokenExpired(rs.getTimestamp(index++));
        data.setTokenExpiredShort(rs.getTimestamp(index++));

        return data;
    }
}
