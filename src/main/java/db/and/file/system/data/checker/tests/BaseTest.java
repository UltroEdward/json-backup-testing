package db.and.file.system.data.checker.tests;

import db.and.file.system.data.checker.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


@SpringBootTest(classes = AppContext.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUp() {
        log.info("Starting test suite");
    }

    @AfterSuite
    public void tearDown() {
        log.info("Shut-down test suite");
    }

}
