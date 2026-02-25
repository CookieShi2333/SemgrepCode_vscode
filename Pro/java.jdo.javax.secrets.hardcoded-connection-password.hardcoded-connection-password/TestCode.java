import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import javax.jdo.PersistenceManagerFactory;

public class PeopleTest {

    private PersistenceManagerFactory pmf;
    private String pw = "asdf";

    /**
     * @throws SQLException
     */
    @Before
    public void setUp() throws SQLException {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("datanucleus.autoCreateSchema", true);
        props.put("datanucleus.rdbms.statementBatchLimit", 0);

        pmf = new JDOPersistenceManagerFactory(props);
        pmf.setConnectionDriverName(jdbcDriver.class.getName());
        pmf.setConnectionURL("jdbc:hsqldb:mem:testdb;hsqldb.sqllog=3");
        pmf.setConnectionUserName("SA");

        // ruleid: hardcoded-connection-password
        pmf.setConnectionPassword("asdf");

        // ruleid: hardcoded-connection-password
        pmf.setConnectionPassword(pw);

        // ok: hardcoded-connection-password
        pmf.setConnectionPassword("");
    }
}
