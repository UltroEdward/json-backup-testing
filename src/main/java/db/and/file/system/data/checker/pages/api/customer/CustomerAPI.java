package db.and.file.system.data.checker.pages.api.customer;

import db.and.file.system.data.checker.core.clients.httpclient.HttpClient;
import db.and.file.system.data.checker.core.utils.JsonHelper;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomerAPI {

    @Autowired
    HttpClient client;

    private static final String GET_CUSTOMERS_URL = "/api/users";

    public String getCustomer() {
        HttpEntity response = client.doGet(GET_CUSTOMERS_URL).getEntity();
        try {
            return JsonHelper.parseJsonNode(response.getContent()).asText();
        } catch (IOException e) {
            throw new RuntimeException("Get customer failed", e);
        }
    }

}
