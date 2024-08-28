/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetch;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;

import client.LocalhostClient;
import document.IndexApi;
import jhazm.Normalizer;

/**
 *
 * @author baghe
 */
public class Transform {
        private static final Logger LOGGER = Logger.getLogger(Transform.class);

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {
        final RestHighLevelClient client = LocalhostClient.create();
        IndexApi ia = new IndexApi();
        CountRequest countRequest = new CountRequest();
        Map<String, Object> doc = new HashMap<String, Object>();
        Normalizer normal = new Normalizer();
        DAO news = new DAO("fetchednews");
        ResultSet rs = news.retrieveAll();
        long counter = 0;
        while (rs.next()) {
            String s = rs.getString("fetchtime");
            if (s.length() == 32) {
                s = Long.toString(ZonedDateTime.parse(s.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZZZZZ")).toInstant().toEpochMilli());
            } else if (s.length() == 31) {
                s = Long.toString(ZonedDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSZZZZZ")).toInstant().toEpochMilli());
            } else if (s.length() == 30) {
                s = Long.toString(ZonedDateTime.parse(s.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSZZZZZ")).toInstant().toEpochMilli());
            } else if (s.length() == 29) {
                s = Long.toString(ZonedDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSZZZZZ")).toInstant().toEpochMilli());
            } else if (s.length() == 28) {
                s = Long.toString(ZonedDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSZZZZZ")).toInstant().toEpochMilli());
            } else if (s.length() == 27) {
                s = Long.toString(ZonedDateTime.parse(s.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SZZZZZ")).toInstant().toEpochMilli());
            } else {
                s = Long.toString(ZonedDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZZZZZ")).toInstant().toEpochMilli());
            }
//            LOGGER.info(++counter);
countRequest.query(QueryBuilders.termQuery("id", rs.getString("url").trim()) );
            CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
            LOGGER.info("count of similar news: " + countResponse.getCount());
            LOGGER.info("id: " + rs.getString("url").trim());
//            Thread.sleep(1000);
            if (countResponse.getCount()==0) {
                doc.put("id", rs.getString("url").trim());
                doc.put("name", "ایسنا");
                doc.put("homeurl", "https://www.isna.ir");
                doc.put("topic", "all");
                doc.put("newsurl", rs.getString("url").trim());
                doc.put("title", normal.run(rs.getString("title").trim()));
                doc.put("description", normal.run(rs.getString("description").trim()));
                doc.put("content", normal.run(rs.getString("content").trim()));
                doc.put("fetchtime", s);
                doc.put("processed", "false");
                IndexResponse ir = ia.indexSync("fetchednews", doc);
                LOGGER.info(ir.getResult().getLowercase());
                doc.clear();
//                LOGGER.info(ia.getPubDate());
                LOGGER.info("news added, key number="  + counter++);
                LOGGER.info(s );
//                Thread.sleep(1000);
            }
        }
        client.close();
//           Thread.sleep(500);
    }

    public static String deDup(String s) {
        return new LinkedHashSet<String>(Arrays.asList(s.split("-"))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", "-");
    }
}
