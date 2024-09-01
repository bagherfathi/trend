package com.fff.trendmanager.trend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author baghe
 */
import org.json.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;

public class NestedJSONObjectTest {

    public static void main(String args[]) throws IOException {
        String url = "http://localhost:9200/fetchednews/_search";
        String json = "{\n"
                + "  \"query\": {\n"
                + "        \"range\" : {\n"
                + "            \"fetchtime\" : {\n"
                + "				\"time_zone\": \"+04:30\",\n"
                + "                \"gte\" : \"now-12h/h\"\n"
                + "            }\n"
                + "        }\n"
                + "    },\n"
                + "    \"size\": 0, \n"
                + "  \"aggregations\": {\n"
                + "    \"sample\": {\n"
                + "      \"sampler\": {\n"
                + "        \"shard_size\": 10000\n"
                + "      },\n"
                + "      \"aggregations\": {\n"
                + "        \"keywords\": {\n"
                + "          \"significant_text\": {\n"
                + "            \"field\": \"description\",\n"
                + "            \"filter_duplicate_text\": true\n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}";
        String s = sendJsonPOST(url, json,"elastic","tbontb");
        System.out.println(s);
        System.out.println("--------------------------------------------------------------------------------------------------");
        TreeMap result=extractTrendFromJson(s);
        Iterator it=result.descendingKeySet().iterator();
        for (;it.hasNext();) {
            Float score=(Float)it.next();
            System.out.println("score: " + score+ " key: " + result.get(score));
        }
        

    }

    public static TreeMap extractTrendFromJson(String jsonString) {
        TreeMap result = new TreeMap();
        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONObject("aggregations").getJSONObject("sample").getJSONObject("keywords").getJSONArray("buckets");
        for (int i = 0; i < arr.length(); i++) {
            result.put(arr.getJSONObject(i).getFloat("score"),arr.getJSONObject(i).getString("key"));
        }
        return result;
    }
    public static TreeMap extractAgenciesFromJson(String jsonString) {
        TreeMap result = new TreeMap();
        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONObject("aggregations").getJSONObject("keys").getJSONArray("buckets");
        for (int i = 0; i < arr.length(); i++) {
            result.put(arr.getJSONObject(i).getInt("doc_count"),arr.getJSONObject(i).getString("key"));
        }
        return result;
    }
    public static TreeMap extractFrequencyFromJson(String jsonString) {
        TreeMap result = new TreeMap();
        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONObject("aggregations").getJSONObject("byday").getJSONArray("buckets");
        for (int i = 0; i < arr.length(); i++) {
            result.put(arr.getJSONObject(i).getLong("key"),arr.getJSONObject(i).getInt("doc_count"));
        }
        return result;
    }
    public static String sendJsonPOST(String url, String json, String user, String pass) throws IOException {
        String result = "";
        String auth = user + ":" + pass;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);      
        HttpPost post = new HttpPost(url);
        post.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        post.setHeader(HttpHeaders.CONTENT_ENCODING,"UTF-8");
        post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        // send a JSON data
        post.setEntity(new StringEntity(json, "UTF-8"));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {
            result = EntityUtils.toString(response.getEntity());
        }
        return result;
    }
}
