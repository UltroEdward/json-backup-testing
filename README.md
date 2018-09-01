## QA Test framework for comparing DB and File Server Data

### Framework covering following logic:
1. Make API call to set-up preconditions
2. Get data from DB
3. Get data from file System over SFTP 
4. Compare this data

### Using technology:
Java 8, Spring, TestNg, Apache HTTP, JSCH, Jackson

### QA Engineer workflow:
1. Implement `API` class to run API preconditions if needed
```
@Component
public class CustomerAPI {

    @Autowired
    HttpClient client;

    private static final String GET_CUSTOMERS_URL = "/api/customers";

    public void getCustomerList() {
        client.doGet(GET_CUSTOMERS_URL);
        //return neede values
    }

}
```
2. Implement `DataBase Provider` to grab data from DB
```
@Component
public class CustomerDataBaseProvider extends BaseDataBaseProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomerDataBaseProvider.class);

    public String getCustomer(String customerID) {
        return db.getQuery("Select * from ACCOUNT where ACCOUNTID = " + customerID);
    }

}
```
3. Implement `File Provider` to grab data form json file
```
@Component
public class CustomerFileProvider extends BaseFileProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomerFileProvider.class);

    public String getCustomer(String customerID) {
        //TODO: dummy hardcoded path
        return fr.readFile(basePath + "/backup-data/users");
    }

}
```
4. Apply needed assertions. Provide JsonNodes to IAssertBothJson or IAssertJson, and then add assertions via `json node path` and `value`:
```
IAssertBothJson customerFromBoth = new AssertBothJson(dbActual, fileExpected);

//fail if both not contains node `FIRSTNAME` with value `Joe`
customerFromBoth.containsNode("/FIRSTNAME", "Joe");

 ```
 or
 ```
IAssertJson dbData = new AssertJson(dbActual);
IAssertJson fileData = new AssertJson(fileExpected);

fileData.containsNode("/status", "A");
dbData.containsNode("/FIRSTNAME", "Joe");
```
5. Extends `BaseTest` for test logic implementation
```
public class DemoTest extends BaseTest {

    @Autowired
    protected CustomerDataBaseProvider providerDB;
    @Autowired
    protected CustomerFileProvider providerFile;
    @Autowired
    protected CustomerAPI customerApi;

    private String customerID;

    @BeforeMethod
    public void beforeTest() {
        customerID = customerApi.getCustomerID();
    }

    @Test
    public void demoTestSameData() {
        String dbActual = providerDB.getCustomer(customerID);
        String fileExpected = providerFile.getCustomer(customerID);

        IAssertBothJson customerFromBoth = new AssertBothJson(dbActual, fileExpected);

        customerFromBoth.containsNode("/FIRSTNAME", "Joe");
    }
  }
```

### Run tests
1. Install [JAVA 8+](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Update application.properties for credentials for API, DB and SFTP
3. Build application with gradle
4. Run with TestNG
