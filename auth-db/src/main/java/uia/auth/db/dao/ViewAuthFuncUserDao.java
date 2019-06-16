package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthFuncUserView;

public class ViewAuthFuncUserDao {

    private static final String SQL_SEL = "SELECT auth_func,auth_user,access_type,func_name,func_description,user_id,user_name,user_enabled FROM view_auth_func_user ";

    private final Connection conn;

    public ViewAuthFuncUserDao(Connection conn) {
        this.conn = conn;
    }

    public List<AuthFuncUserView> selectAll() throws SQLException {
        ArrayList<AuthFuncUserView> result = new ArrayList<AuthFuncUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY func_name,user_id")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public AuthFuncUserView select(String funcName, String userId) throws SQLException {
        AuthFuncUserView result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE func_name=? and user_id=?")) {
            ps.setString(1, funcName);
            ps.setString(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthFuncUserView> selectByFunc(long authFunc) throws SQLException {
        ArrayList<AuthFuncUserView> result = new ArrayList<AuthFuncUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_func=? ORDER BY user_id")) {
            ps.setLong(1, authFunc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncUserView> selectByFunc(String funcName) throws SQLException {
        ArrayList<AuthFuncUserView> result = new ArrayList<AuthFuncUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE func_name=? ORDER BY user_id")) {
            ps.setString(1, funcName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncUserView> selectByUser(long authUser) throws SQLException {
        ArrayList<AuthFuncUserView> result = new ArrayList<AuthFuncUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE auth_user=? ORDER BY func_name")) {
            ps.setLong(1, authUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFuncUserView> selectByUser(String userId) throws SQLException {
        ArrayList<AuthFuncUserView> result = new ArrayList<AuthFuncUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE user_id=? ORDER BY func_name")) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthFuncUserView convert(ResultSet rs) throws SQLException {
        AuthFuncUserView data = new AuthFuncUserView();

        int index = 1;
        data.setAuthFunc(rs.getLong(index++));
        data.setAuthUser(rs.getLong(index++));
        data.setAccessType(rs.getString(index++));
        data.setFuncName(rs.getString(index++));
        data.setFuncDescription(rs.getString(index++));
        data.setUserId(rs.getString(index++));
        data.setUserName(rs.getString(index++));
        data.setUserEnabled(rs.getString(index++));

        return data;
    }
}
