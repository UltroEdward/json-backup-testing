package db.and.file.system.data.checker.tests.suites;

import db.and.file.system.data.checker.core.assertion.IAssertBothJson;
import db.and.file.system.data.checker.core.assertion.IAssertJson;
import db.and.file.system.data.checker.core.assertion.assertImpl.AssertBothJson;
import db.and.file.system.data.checker.core.assertion.assertImpl.AssertJson;
import db.and.file.system.data.checker.pages.api.customer.CustomerAPI;
import db.and.file.system.data.checker.pages.database.customer.CustomerDataBaseProvider;
import db.and.file.system.data.checker.pages.filesystem.customer.CustomerFileProvider;
import db.and.file.system.data.checker.tests.BaseTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
        customerID = customerApi.getCustomer();
    }

    @Test
    public void demoTestSameData() {
        String dbActual = providerDB.getCustomer(customerID);
        String fileExpected = providerFile.getCustomer(customerID);

        IAssertBothJson customerFromBoth = new AssertBothJson(dbActual, fileExpected);

        customerFromBoth.containsNode("/FIRSTNAME", "Joe");
    }

    @Test
    public void demoTestDifferentData() {
        String dbActual = providerDB.getCustomer(customerID);
        String fileExpected = providerFile.getCustomer(customerID);

        IAssertJson dbData = new AssertJson(dbActual);
        IAssertJson fileData = new AssertJson(fileExpected);

        fileData.containsNode("/status", "A");
        dbData.containsNode("/FIRSTNAME", "Joe");
    }

}
