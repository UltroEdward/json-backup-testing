package db.and.file.system.data.checker.core.assertion.assertImpl;

import com.fasterxml.jackson.databind.JsonNode;
import db.and.file.system.data.checker.core.assertion.IAssertBothJson;
import db.and.file.system.data.checker.core.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertBothJson implements IAssertBothJson {

    private JsonNode actual;
    private JsonNode expected;

    private static final Logger log = LoggerFactory.getLogger(IAssertBothJson.class);

    public AssertBothJson(String actual, String expected) {
        this.actual = JsonHelper.parseJsonNode(actual);
        this.expected = JsonHelper.parseJsonNode(expected);
    }

    @Override
    public void equalsBoth() {
        assertThat(actual).isEqualTo(expected).withFailMessage(String.format("Actual node: %s/n and expected /n%s/n not equal", actual.toString(), expected.toString()));
    }

    @Override
    public void containsNode(String nodeName, String nodeValue) {
        //check actual - left
        JsonNode node = actual.at(nodeName);
        if (node.isMissingNode()) {
            throw new AssertionError(String.format("Can not find in ACTUAL path: [%s], check if jsonNode valid %s", nodeName, actual.toString()));
        }
        String expectedField = node.asText();
        log.debug(String.format("Node value [%s] extracted from [%s]", expectedField, nodeName));
        assertThat(expectedField).isEqualTo(nodeValue).withFailMessage(String.format("Node value [%s] extracted from [%s] was not equals to: %s", expectedField, nodeName, nodeName));

        //check expected  - right
        node = expected.at(nodeName);
        if (node.isMissingNode()) {
            throw new AssertionError(String.format("Can not find in EXPECTED path: [%s], check if jsonNode valid %s", nodeName, actual.toString()));
        }
        expectedField = node.asText();
        log.debug(String.format("Node value [%s] extracted from [%s]", expectedField, nodeName));
        assertThat(expectedField).isEqualTo(nodeValue).withFailMessage(String.format("Node value [%s] extracted from [%s] was not equals to: %s", expectedField, nodeName, nodeName));
    }

    @Override
    public void updateActual(String actual) {
        this.actual = JsonHelper.parseJsonNode(actual);
    }

    @Override
    public void updateExpected(String expected) {
        this.expected = JsonHelper.parseJsonNode(expected);
    }

    @Override
    public JsonNode getActual() {
        return actual;
    }

    @Override
    public JsonNode getExpected() {
        return expected;
    }

}
