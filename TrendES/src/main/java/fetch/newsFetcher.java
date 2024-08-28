/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetch;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;

//import com.apptastic.rssreader.Item;

import client.LocalhostClient;
import jhazm.Normalizer;
import rss.HTMLParser;
import rss.rssparser;

/**
 *
 * @author baghe
 */
public class newsFetcher {
        private static final Logger LOGGER = Logger.getLogger(newsFetcher.class);

//    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//        final RestHighLevelClient client = LocalhostClient.create();
////        IndexApi ia = new IndexApi();
//        int key = 1;
//        Map<String, Object> doc = new HashMap<String, Object>();
//        rssparser rssr = new rssparser("https://www.isna.ir/rss");
//        List<Item> articles = rssr.readRss();
//        CountRequest countRequest = new CountRequest();
////        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        Normalizer normal = new Normalizer();
//        for (Item a : articles) {
//
////            sourceBuilder.query(QueryBuilders.termQuery("id", "isna"));
//            countRequest.query(QueryBuilders.termQuery("id", a.getLink().get().trim()) );
//            CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
//            LOGGER.info("count of similar news: " + countResponse.getCount());
//            LOGGER.info("id: " + a.getLink().get().trim());
//            if (countResponse.getCount()==0) {
//                if (a.getDescription().isEmpty() || a.getTitle().get().isEmpty() || a.getPubDate().get().isEmpty() || a.getLink().get().isEmpty()) {
//                    continue;
//                }
////                doc.put("_id",a.getLink().get().trim() );
//                doc.put("id", a.getLink().get().trim());
//                doc.put("name", "ایسنا");
//                doc.put("homeurl", "https://www.isna.ir");
//                doc.put("topic", "all");
//                doc.put("newsurl", a.getLink().get().trim());
//                doc.put("title", normal.run(a.getTitle().get().trim()));
//                doc.put("description", normal.run(a.getDescription().get().trim()));
//                doc.put("content", normal.run(HTMLParser.extract(a.getLink().get(), "itemprop", "articleBody")));
//                doc.put("fetchtime", Long.toString(System.currentTimeMillis()));
//                doc.put("processed", "false");
////                IndexResponse ir = ia.indexSync("fetchednews", doc);
//                doc.clear();
//                LOGGER.info(a.getPubDate());
//                LOGGER.info("news added, key number=" + key++);
//
////                LOGGER.info(ia.);
//            }
//        }
//        client.close();
//        articles.clear();
//        return;
//    }
}
