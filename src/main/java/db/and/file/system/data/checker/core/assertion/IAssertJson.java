package db.and.file.system.data.checker.core.assertion;

import com.fasterxml.jackson.databind.JsonNode;

public interface IAssertJson {

    void containsNode(String nodeName, String nodeValue);

    void update(String json);

    JsonNode getJson();

}
