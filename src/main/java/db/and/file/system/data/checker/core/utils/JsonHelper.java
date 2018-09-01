package db.and.file.system.data.checker.core.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonHelper {

    private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

    public static List<JsonNode> parseJsonNodeList(String json) {
        String cleanJson = JSONObject.escape(json);

        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> newNode = null;
        try {
            newNode = mapper.readValue(cleanJson, List.class);
        } catch (IOException e) {
            log.warn(String.format("Can't parse string to List<JsonNode>: %s", json), e);
        }
        return newNode;
    }

    public static JsonNode parseJsonNode(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode newNode = null;
        try {
            newNode = mapper.readTree(json);
        } catch (IOException e) {
            log.warn(String.format("Can't parse string to JsonNode: %s", json), e);
        }
        return newNode;
    }

    public static JsonNode parseJsonNode(InputStream stream) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode newNode = null;
        try {
            newNode = mapper.readTree(stream);
        } catch (IOException e) {
            log.warn("Can't read data from input stream, it can be empty", e);
        }
        return newNode;
    }

}
