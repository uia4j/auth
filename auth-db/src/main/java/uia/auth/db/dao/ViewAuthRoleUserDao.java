package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthRoleUserView;

public class ViewAuthRoleUserDao {

    private static final String SQL_SEL = "SELECT auth_user,auth_role,role_name,role_enabled,user_id,user_name,user_enabled,seed,mobile_no,email FROM view_auth_role_user ";

    private final Connection conn;

    public ViewAuthRoleUserDao(Connection conn) {
        this.conn = conn;
    }

    public List<AuthRoleUserView> selectAll() throws SQLException {
        ArrayList<AuthRoleUserView> result = new ArrayList<AuthRoleUserView>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY role_name,user_id")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthRoleUserView convert(ResultSet rs) throws SQLException {
        AuthRoleUserView data = new AuthRoleUserView();

        int index = 1;
        data.setAuthUser(rs.getLong(index++));
        data.setAuthRole(rs.getLong(index++));
        data.setRoleName(rs.getString(index++));
        data.setRoleEnabled(rs.getString(index++));
        data.setUserId(rs.getString(index++));
        data.setUserName(rs.getString(index++));
        data.setUserEnabled(rs.getString(index++));
        data.setPwd(rs.getString(index++));
        data.setMobileNo(rs.getString(index++));
        data.setEmail(rs.getString(index++));

        return data;
    }
}
