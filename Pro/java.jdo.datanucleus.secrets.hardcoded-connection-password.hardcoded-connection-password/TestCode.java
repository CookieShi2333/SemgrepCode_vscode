import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;

public class test {
    private String password = "asdf";

    public void setUp() throws SQLException {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("datanucleus.autoCreateSchema", true);
        props.put("datanucleus.rdbms.statementBatchLimit", 0);

        JDOPersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(props);
        pmf.setConnectionDriverName(jdbcDriver.class.getName());
        pmf.setConnectionURL("jdbc:hsqldb:mem:testdb;hsqldb.sqllog=3");
        pmf.setConnectionUserName("SA");

        // ruleid: hardcoded-connection-password
        pmf.setConnectionPassword("asdf");

        // ruleid: hardcoded-connection-password
        pmf.setConnectionPassword(password);

        // ok: hardcoded-connection-password
        pmf.setConnectionPassword("");
    }
}