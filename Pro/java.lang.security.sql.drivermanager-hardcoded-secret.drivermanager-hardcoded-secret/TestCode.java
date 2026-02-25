import java.sql.*;

public class a
{
   public static void main(String[] args) throws SQLException
   {
      String password = "a";
      // ruleid: drivermanager-hardcoded-secret
      Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:o92", "a", "password");
      // ruleid: drivermanager-hardcoded-secret
      Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:o92", "a", password);
      // ok: drivermanager-hardcoded-secret
      Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:o92", "a", random);
      // ok: drivermanager-hardcoded-secret
      Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:o92");
      // ok: drivermanager-hardcoded-secret
      Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:o92","a");

      DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
      // ruleid: drivermanager-hardcoded-secret
      driverManagerDataSource.setPassword("a");
      // ok: drivermanager-hardcoded-secret
      driverManagerDataSource.setPassword(config.foo);
      // ruleid: drivermanager-hardcoded-secret
      new DriverManagerDataSource("aaaa", "root", "123456");
      // ok: drivermanager-hardcoded-secret
      new DriverManagerDataSource("aaaa", "root", config);
   }
}
