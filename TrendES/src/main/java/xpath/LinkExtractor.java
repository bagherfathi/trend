/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpath;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.client.core.CountResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import document.GetCountApi;

/**
 *
 * @author baghe
 */
public class LinkExtractor {

    private static final Logger LOGGER = Logger.getLogger(LinkExtractor.class);

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        LinkExtractor le = new LinkExtractor();
        List<String> s = le.allExternalLinks("https://plus.irna.ir/");
        printLinks(s);
        s = le.allInternalLinks("https://plus.irna.ir/");
        printLinks(s);
    }

    public List<String> allInternalLinks(String url) throws IOException, URISyntaxException, InterruptedException {
        List<String> s = new ArrayList<String>();
        print("Fetching %s...", url);
        String domain = getDomainName(url);
        url = url.trim();
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        print("\ninternal Links: (%d)", links.size());
        GetCountApi gca1 = new GetCountApi();
        GetCountApi gca2 = new GetCountApi();
        for (Element link : links) {
            Thread.sleep(200);
            String absHref = link.attr("abs:href"); // "http://jsoup.org/"
//            LOGGER.info("abs:-------- " + absHref);
//            int slashslash = absHref.indexOf("//") + 2;
//            String host = absHref.substring(0, absHref.indexOf('/', slashslash));
//            String relHref = absHref.substring(absHref.indexOf('/', slashslash)+1);
//            String u=host+ "/" +URLEncoder.encode(relHref,Charset.forName("UTF-8"));
            //u=u.trim().strip();            
//            LOGGER.info("rel: " + relHref);
            String u=absHref;
//            LOGGER.info("abs: " + absHref);
//            LOGGER.info("encoded: "+u);
            CountResponse countResponse1 = gca1.getCount("fetchednews", "id", u);
            long count1 = countResponse1.getCount();
            CountResponse countResponse2 = gca2.getCount("faulturls", "url", u);
            long count2 = countResponse2.getCount();
            if (count1 == 0) {
                if (count2 == 0) {
                    if (!s.contains(u)) {
                        if (!domain.isEmpty() && !u.isEmpty()) {
                            if (getDomainName(u).equalsIgnoreCase(domain)) {
                                s.add(u);
                            }
                        }
                    }
                }
            }
            //print(" Domain:<%s>   link:<%s> ", domain, link.attr("abs:href"));
        }
        print("\ninternal Links(after harassment): (%d)", s.size());
        return s;
    }

    public List<String> allExternalLinks(String url) throws IOException, URISyntaxException {
        List<String> s = new ArrayList<String>();
        print("Fetching %s...", url);
        String domain = getDomainName(url);
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        print("\nexternal Links: (%d)", links.size());
        for (Element link : links) {
            if (!getDomainName(link.attr("abs:href")).equalsIgnoreCase(domain)) {
                if (!s.contains(link.attr("abs:href").trim())) {
                    s.add(link.attr("abs:href"));
                    //print(" Domain:<%s>   link:<%s> ", domain, link.attr("abs:href"));
                }
            }
        }
        return s;
    }

    private static void print(String msg, Object... args) {
        LOGGER.info(String.format(msg, args));
    }

    public static String getDomainName(String url)  {
        int slashslash = url.indexOf("//") + 2;
            String host;
            if(url.indexOf('/', slashslash)==-1)
                host = url.substring(slashslash,url.trim().length());
            else
                host = url.substring(slashslash,url.indexOf('/', slashslash));
            return host.startsWith("www.") ? host.substring(4) : host;
    }

    public static void printLinks(List<String> l) {
        for (String s : l) {
            LOGGER.info(s);
        }
    }

}
