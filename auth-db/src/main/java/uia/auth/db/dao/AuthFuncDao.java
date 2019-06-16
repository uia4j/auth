package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthFunc;

public class AuthFuncDao {

    private static final String SQL_INS = "INSERT INTO auth_func(id,func_name,parent_func,func_description) VALUES (?,?,?,?)";

    private static final String SQL_UPD = "UPDATE auth_func SET func_name=?,parent_func=?,func_description=? WHERE id=?";

    private static final String SQL_SEL = "SELECT id,func_name,parent_func,func_description FROM auth_func ";

    private final Connection conn;

    public AuthFuncDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(AuthFunc data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            ps.setLong(1, data.getId());
            ps.setString(2, data.getFuncName());
            ps.setLong(3, data.getParentFunc());
            ps.setString(4, data.getFuncDescription());

            return ps.executeUpdate();
        }
    }

    public int insert(List<AuthFunc> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_INS)) {
            for (AuthFunc data : dataList) {
                ps.setLong(1, data.getId());
                ps.setString(2, data.getFuncName());
                ps.setLong(3, data.getParentFunc());
                ps.setString(4, data.getFuncDescription());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int update(AuthFunc data) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            ps.setString(1, data.getFuncName());
            ps.setLong(2, data.getParentFunc());
            ps.setString(3, data.getFuncDescription());
            ps.setLong(4, data.getId());

            return ps.executeUpdate();
        }
    }

    public int update(List<AuthFunc> dataList) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_UPD)) {
            for (AuthFunc data : dataList) {
                ps.setString(1, data.getFuncName());
                ps.setLong(2, data.getParentFunc());
                ps.setString(3, data.getFuncDescription());
                ps.setLong(4, data.getId());

                ps.addBatch();
            }
            return ps.executeBatch().length;
        }
    }

    public int delete(long id) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("DELETE FROM auth_func WHERE id=?")) {
            ps.setLong(1, id);

            return ps.executeUpdate();
        }
    }

    public AuthFunc selectByPK(long id) throws SQLException {
        AuthFunc result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE id=?")) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public AuthFunc selectByName(String funcName) throws SQLException {
        AuthFunc result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE func_name=?")) {
            ps.setString(1, funcName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = convert(rs);
            }
            return result;
        }
    }

    public List<AuthFunc> selectAll() throws SQLException {
        ArrayList<AuthFunc> result = new ArrayList<AuthFunc>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "ORDER BY func_name")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    public List<AuthFunc> searchParents(long id) throws SQLException {
        ArrayList<AuthFunc> result = new ArrayList<AuthFunc>();
        AuthFunc f = selectByPK(id);
        while (f != null) {
            result.add(f);
            f = selectByPK(f.getParentFunc());
        }
        return result;
    }

    public List<AuthFunc> searchParents(String funcName) throws SQLException {
        ArrayList<AuthFunc> result = new ArrayList<AuthFunc>();
        AuthFunc f = selectByName(funcName);
        while (f != null) {
            result.add(f);
            f = selectByPK(f.getParentFunc());
        }
        return result;
    }

    public List<AuthFunc> searchNexts(long id) throws SQLException {
        ArrayList<AuthFunc> result = new ArrayList<AuthFunc>();
        try (PreparedStatement ps = this.conn.prepareStatement(SQL_SEL + "WHERE parent_func=? ORDER BY func_name")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(convert(rs));
            }
            return result;
        }
    }

    private AuthFunc convert(ResultSet rs) throws SQLException {
        AuthFunc data = new AuthFunc();

        int index = 1;
        data.setId(rs.getLong(index++));
        data.setFuncName(rs.getString(index++));
        data.setParentFunc(rs.getLong(index++));
        data.setFuncDescription(rs.getString(index++));

        return data;
    }
}
