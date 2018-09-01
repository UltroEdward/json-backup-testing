package db.and.file.system.data.checker.core.clients.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.and.file.system.data.checker.core.utils.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.stream.Stream;

@Component
public class HttpClient {

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static CloseableHttpClient client = HttpClients.createDefault();
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.server.base_url}")
    private String baseUrl;

    public CloseableHttpResponse doGet(String url) {
        HttpRequestBase httpGet = new HttpGet(getURL(url));
        setCommonHeaders(httpGet);

        return invoke(httpGet);
    }

    public CloseableHttpResponse doPost(String url, JsonNode body) {
        return doPost(url, body, null, true);
    }

    public CloseableHttpResponse doPost(String url, String body, Header[] headers, boolean needAuth) {
        return doPost(url, JsonHelper.parseJsonNode(body), headers, true);
    }

    public CloseableHttpResponse doPost(String url, JsonNode body, Header[] headers, boolean needAuth) {
        HttpPost httpPost = new HttpPost(getURL(url));

        try {
            httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsBytes(body)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        setCommonHeaders(httpPost);
        if (headers != null && headers.length > 0) {
            Stream.of(headers).forEach(httpPost::addHeader);
        }

        return invoke(httpPost);
    }

    private CloseableHttpResponse invoke(HttpRequestBase http) {
        try (CloseableHttpResponse response = client.execute(http)) {

            if (http instanceof HttpPost) {
                InputStream inputStream = ((HttpPost) http).getEntity().getContent();
                String body = IOUtils.toString(inputStream, Charset.defaultCharset());
                log.info(String.format("Sending request: [%s] %s => [%s] Payload:\n%s", http.getMethod(), http.getURI(), response.getStatusLine(), body));
            } else {
                log.info(String.format("Sending request: [%s] %s => [%s]", http.getMethod(), http.getURI(), response.getStatusLine()));
            }

            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);

            return response;
        } catch (IOException e) {
            throw new RuntimeException("HTTP request failed: " + http.toString());
        }
    }

    private void setCommonHeaders(HttpRequestBase request) {
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
    }

    private String getURL(String urlToResource) {
        return baseUrl + urlToResource;
    }

}