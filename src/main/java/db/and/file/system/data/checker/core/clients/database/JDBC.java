package db.and.file.system.data.checker.core.clients.database;


import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JDBC {

    private static final Logger log = LoggerFactory.getLogger(JDBC.class);

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public String getQuery(String sql) {
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        String jsonQuery = JSONObject.toJSONString(map);

        log.info("Getting query: " + jsonQuery);
        return jsonQuery;
    }

    public String getQueryList(String sql) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        org.json.JSONArray json_arr = new org.json.JSONArray(list);
        String jsonQuery = json_arr.toString();

        log.info("Getting query: " + jsonQuery);
        return jsonQuery;
    }

}