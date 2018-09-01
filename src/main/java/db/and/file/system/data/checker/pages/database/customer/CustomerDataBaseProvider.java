package db.and.file.system.data.checker.pages.database.customer;

import db.and.file.system.data.checker.core.baseproviders.db.BaseDataBaseProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataBaseProvider extends BaseDataBaseProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomerDataBaseProvider.class);

    public String getCustomer(String customerID) {
        return db.getQuery("Select * from ACCOUNT where ACCOUNT_ID = " + customerID);
    }

}
