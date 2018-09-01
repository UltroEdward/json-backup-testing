package db.and.file.system.data.checker.core.baseproviders.db;

import db.and.file.system.data.checker.core.clients.database.JDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataBaseProvider {

    private static final Logger log = LoggerFactory.getLogger(BaseDataBaseProvider.class);

    @Autowired
    protected JDBC db;

}
