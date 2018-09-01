package db.and.file.system.data.checker.core.assertion;

import com.fasterxml.jackson.databind.JsonNode;

public interface IAssertBothJson {

    void equalsBoth();

    void containsNode(String nodeName, String nodeValue);

    void updateActual(String json);

    void updateExpected(String json);

    JsonNode getActual();

    JsonNode getExpected();

}
