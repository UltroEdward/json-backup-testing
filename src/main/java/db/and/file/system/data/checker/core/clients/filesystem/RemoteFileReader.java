package db.and.file.system.data.checker.core.clients.filesystem;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
public class RemoteFileReader {

    @Value("${json.server.url}")
    private String url;
    @Value("${json.server.port}")
    private int port;
    @Value("${json.server.username}")
    private String username;
    @Value("${json.server.password}")
    private String password;

    private JSch jsch = new JSch();
    private Session session = null;
    private ChannelSftp sftpChannel = null;

    private static final Logger log = LoggerFactory.getLogger(RemoteFileReader.class);

    private void setConnection() {
        try {
            session = jsch.getSession(username, url, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
        } catch (JSchException e) {
            throw new RuntimeException("Could not connect to remote server", e);
        }
    }

    public synchronized String readFile(String pathToFile) {
        try {
            InputStream stream = sftpChannel.get(pathToFile);

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String result = br.lines().collect(Collectors.joining());
            log.info("File: " + result);
            return result;

        } catch (SftpException e) {
            throw new RuntimeException("Can not read remote file", e);
        }
    }

    @PostConstruct
    public void preConnect() {
        log.info("Creating SFTP connection: " + this.getClass().getSimpleName());
        setConnection();
    }

    @PreDestroy
    public void shutDownConnection() {
        log.info("Destroy SFTP connection: " + this.getClass().getSimpleName());
        sftpChannel.exit();
        session.disconnect();
    }

}