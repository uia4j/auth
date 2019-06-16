package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uia.auth.db.AuthSecurityView;

public class ViewAuthSecurityDao {

    private static final String SQL_SEL = "SELECT auth_user,password,token,token_expired,token_expired_short,user_id,enabled,seed FROM view_auth_security ";
    
    private final Connection conn;

    public ViewAuthSecurityDao(Connection conn) {
        this.conn = conn;
    }

    public AuthSecurityView selectByUserId(long authUser) throws SQLException {
        AuthSecurityView result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_user=?")) {
        	ps.setLong(1, authUser);
        	
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }    

    public AuthSecurityView selectByUserId(String userId) throws SQLException {
        AuthSecurityView result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE user_id=?")) {
        	ps.setString(1, userId);
        	
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }    

    public AuthSecurityView selectBySession(String session) throws SQLException {
        AuthSecurityView result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE token=?")) {
        	ps.setString(1, session);
        	
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }    
    
    private AuthSecurityView convert(ResultSet rs) throws SQLException {
        AuthSecurityView data = new AuthSecurityView();
        
        int index = 1;
        data.setAuthUser(rs.getInt(index++));
        data.setPassword(rs.getString(index++));
        data.setToken(rs.getString(index++));
        data.setTokenExpired(rs.getTimestamp(index++));
        data.setTokenExpiredShort(rs.getTimestamp(index++));
        data.setUserId(rs.getString(index++));
        data.setEnabled(rs.getString(index++));
        data.setSeed(rs.getString(index++));

        return data;
    }
}
