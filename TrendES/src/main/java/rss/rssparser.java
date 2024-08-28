/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rss;

import java.io.IOException;
//import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

//import com.apptastic.rssreader.Item;
//import com.apptastic.rssreader.RssReader;

/**
 *
 * @author baghe
 */
public class rssparser {
        private static final Logger LOGGER = Logger.getLogger(rssparser.class);

//    public rssparser(String rss_url) {
//        this.rss_url = rss_url;
//    }
//    public String rss_url;
//    public static void main(String[] args) throws IOException {
//        String URL = "https://www.isna.ir/rss";
//        RssReader reader = new RssReader();
//        Stream<Item> rssFeed = reader.read(URL);
////        for(Item i:rssFeed.findFirst())
////        LOGGER.info(rssFeed.findFirst().get().getDescription());
//        List<Item> articles;
//        articles = rssFeed.distinct()
//                .collect(Collectors.toList());
//        for(Item a : articles)
//        {
//            LOGGER.info(a.getLink());
////            break;
//        }
//    }
//    public List<Item> readRss() throws IOException
//    {
//        RssReader reader = new RssReader();
//        Stream<Item> rssFeed = reader.read(rss_url);
//        List<Item> articles;
//        articles = rssFeed.distinct()
//                .collect(Collectors.toList());
//        return articles;
//    }
}
