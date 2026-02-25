package tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  public static PersistenceManager getPM() {
    return pmfInstance.getPersistenceManager();
  }

  @GetMapping("/test1")
  public String test1(@RequestParam(value = "name", defaultValue = "World") String name, HttpServletResponse response, Connection conn) {
    String taint_src = name;

    // ruleid: java-sql-sqli
    conn.prepareCall("select * from " + taint_src);

    // ruleid: java-sql-sqli
    conn.prepareStatement("select * from " + taint_src);
    return "ok";
  }

  @GetMapping("/test2")
  public String test2(@RequestParam(value = "name", defaultValue = "World") String name, HttpServletResponse response, Statement stmt) {
    String taint_src = name;

    // ruleid: java-sql-sqli
    stmt.addBatch("select * from " + taint_src);

    // ruleid: java-sql-sqli
    stmt.execute("select * from " + taint_src);

    // ruleid: java-sql-sqli
    stmt.executeLargeUpdate("select * from " + taint_src);

    // ruleid: java-sql-sqli
    stmt.executeQuery("select * from " + taint_src);

    // ruleid: java-sql-sqli
    stmt.executeUpdate("select * from " + taint_src);
    return "ok";
  }

}
