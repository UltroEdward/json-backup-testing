package db.and.file.system.data.checker.core.baseproviders.filesystem;

import db.and.file.system.data.checker.core.clients.filesystem.RemoteFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseFileProvider {

    private static final Logger log = LoggerFactory.getLogger(BaseFileProvider.class);

    @Value("${json.server.base_path}")
    protected String basePath;

    @Autowired
    protected RemoteFileReader fr;

}
