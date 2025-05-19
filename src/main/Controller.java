import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;

@RestController
@RequestMapping("/api")
public class Controller {

	private final String dbUri = "neo4j+s://cf5a6658.databases.neo4j.io";
	private final String dbUser = "neo4j";
	private final String dbPassword = "2USA0UKeiuIhJvLzyXS6I9i4rJY8zjKtq6IfWAS40fg";
	// post api to create a decision
	@GetMapping
	public String verifyDatabaseConnection() {
		GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword)).verifyConnectivity();
		return "Database Connection Working";
	}
}
