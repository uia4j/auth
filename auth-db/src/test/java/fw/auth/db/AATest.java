package fw.auth.db;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import uia.utils.dao.ColumnType;
import uia.utils.dao.Database;
import uia.utils.dao.JavaClassPrinter;
import uia.utils.dao.TableType;
import uia.utils.dao.ora.Oracle;
import uia.utils.dao.pg.PostgreSQL;

public class AATest {

    @Test
    public void testPostgres() throws Exception {
        Database database = new PostgreSQL("localhost", "5432", "authdb", "auth", "auth");
        database.selectTableNames().forEach(System.out::println);
        database.close();
    }

    @Test
    public void testTable() throws Exception {
        String pathDTO = "E:/fw/auth/auth-db/src/main/java/uia/auth/db/";
        String pathDAO = pathDTO + "dao/";

        String tableName = "auth_security";
        String dtoName = "AuthSecurity";

        Database database = new PostgreSQL("localhost", "5432", "authdb", "auth", "auth");
        JavaClassPrinter printer = new JavaClassPrinter(database, tableName);
        String dto = printer.generateDTO("uia.auth.db", dtoName);
        String dao = printer.generateDAO("uia.auth.db.dao", "fw.pms.db", dtoName);

        Files.write(Paths.get(pathDTO + dtoName + ".java"), dto.getBytes());
        Files.write(Paths.get(pathDAO + dtoName + "Dao.java"), dao.getBytes());
    }

    @Test
    public void testView() throws Exception {
        String pathDTO = "E:/fw/auth/auth-db/src/main/java/uia/auth/db/";
        String pathDAO = pathDTO + "dao/";

        String tableName = "auth_security_view";
        String dtoName = "AuthSecurityView";

        Database database = new PostgreSQL("localhost", "5432", "authdb", "auth", "auth");
        JavaClassPrinter printer = new JavaClassPrinter(database, tableName);
        String dto = printer.generateDTO("fw.auth.db", dtoName);
        String dao = printer.generateDAO4View("fw.auth.db.dao", "fw.auth.db", dtoName);

        Files.write(Paths.get(pathDTO + dtoName + ".java"), dto.getBytes());
        Files.write(Paths.get(pathDAO + dtoName + "Dao.java"), dao.getBytes());
    }

    @Test
    public void testHelp() throws Exception {
        // final Database database = new Hana("192.168.137.245", "39015", null, "WIP", "Sap12345");
        final Database database = new PostgreSQL("localhost", "5432", "pmsdb", "pms", "pms");
        System.out.println("no,pk,column,type,size,nullable");
        for (String tn : database.selectTableNames()) {
            TableType table = database.selectTable(tn, false);
            int i = 1;
            System.out.println("\n" + table.getTableName());
            for (ColumnType col : table.getColumns()) {
                System.out.println(String.format("%2s,%s,%s,%s,%s,%s",
                        i++,
                        col.isPk() ? "v" : "",
                        col.getColumnName(),
                        col.getDataTypeName(),
                        col.getColumnSize(),
                        col.isNullable()));
            }
        }
        database.close();
    }

    public void testSql() throws Exception {
        String path = "E:/fw/auth/auth-db/src/main/java/uia/auth/db/";

        Database pgsql = new PostgreSQL("localhost", "5432", "pmsdb", "pms", "pms");
        Database target = new Oracle();
        target.setSchema("PMS");

        pgsql.selectTableNames().forEach(t -> {
            try {
                String sql = target.generateCreateTableSQL(pgsql.selectTable(t, false));
                Files.write(Paths.get(path + t + ".sql"), sql.getBytes());
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        pgsql.selectViewNames().forEach(t -> {
            try {
                String sql = target.generateCreateTableSQL(pgsql.selectTable(t, false));
                Files.write(Paths.get(path + t + ".sql"), sql.getBytes());
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        target.close();
        pgsql.close();
    }
}
