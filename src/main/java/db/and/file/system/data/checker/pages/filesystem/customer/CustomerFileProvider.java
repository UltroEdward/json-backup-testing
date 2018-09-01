package db.and.file.system.data.checker.pages.filesystem.customer;

import db.and.file.system.data.checker.core.baseproviders.filesystem.BaseFileProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerFileProvider extends BaseFileProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomerFileProvider.class);

    public String getCustomer(String customerID) {
        return fr.readFile(basePath + "/customers/");
    }

}
