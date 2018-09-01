package db.and.file.system.data.checker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"db.and.file.system.data.checker"})
public class AppContext {

    private static final Logger log = LoggerFactory.getLogger(AppContext.class);

    public AppContext() {
        log.info("Starting Spring app context");
    }

}

