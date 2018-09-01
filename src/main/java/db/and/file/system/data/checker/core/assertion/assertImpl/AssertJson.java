package db.and.file.system.data.checker.core.assertion.assertImpl;

import com.fasterxml.jackson.databind.JsonNode;
import db.and.file.system.data.checker.core.assertion.IAssertJson;
import db.and.file.system.data.checker.core.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertJson implements IAssertJson {

    private JsonNode json;

    private static final Logger log = LoggerFactory.getLogger(IAssertJson.class);

    public AssertJson(String json) {
        this.json = JsonHelper.parseJsonNode(json);
    }

    @Override
    public void containsNode(String nodeName, String nodeValue) {
        JsonNode node = json.at(nodeName);
        if (node.isMissingNode()) {
            throw new AssertionError(String.format("Can not find path %s in json, check if jsonNode valid for json %s", nodeName, json.toString()));
        }
        String expectedField = node.asText();
        log.debug(String.format("Node value [%s] extracted from [%s]", expectedField, nodeName));
        assertThat(expectedField).isEqualTo(nodeValue).withFailMessage(String.format("Node value [%s] extracted from [%s] was not equals to: %s", expectedField, nodeName, nodeName));
    }

    @Override
    public void update(String json) {
        this.json = JsonHelper.parseJsonNode(json);
    }

    @Override
    public JsonNode getJson() {
        return json;
    }

}
