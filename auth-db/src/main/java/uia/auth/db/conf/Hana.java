package uia.auth.db.conf;

import java.sql.Connection;
import java.sql.SQLException;

public class Hana {

    public static String CONN;

    public static String USER;

    public static String PWD;

    static {
        try {
            Class.forName("com.sap.db.jdbc.Driver");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (System.getProperties().get("pms.db.connection") == null) {
            CONN = "jdbc:sap://192.168.137.245:39015";
            USER = "WIP";
            PWD = "Sap12345";
        }
        else {
            CONN = "" + System.getProperties().get("pms.db.connection");
            USER = "" + System.getProperties().get("pms.db.user");
            PWD = "" + System.getProperties().get("pms.db.pwd");
        }
    }

    public static void config(String conn, String user, String pwd) {
        CONN = conn;
        USER = user;
        PWD = pwd;
    }

    /**
    * 取得資料庫連線。
    * @return 結果。
    * @throws SQLException 資料庫發生異常。
    */
    public static Connection create() throws SQLException {
        return java.sql.DriverManager.getConnection(CONN, USER, PWD);
    }
}