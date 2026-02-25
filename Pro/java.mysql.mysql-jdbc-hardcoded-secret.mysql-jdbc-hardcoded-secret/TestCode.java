import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DataSrc {
    public static DataSource getMysqlDataSource(){
         String password = "password";
        MysqlDataSource src = new MysqlDataSource();
        // ruleid: mysql-jdbc-hardcoded-secret
        src.setPassword("aaaa");
        // ok: mysql-jdbc-hardcoded-secret
        src.setPassword(config);
        // ruleid: mysql-jdbc-hardcoded-secret
        src.setPassword(password);
        return src;
    }
}