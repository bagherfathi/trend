/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rss;

/**
 *
 * @author baghe
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLParser {
        private static final Logger LOGGER = Logger.getLogger(HTMLParser.class);

    public static void main(String[] args) throws MalformedURLException, IOException {
        URLConnection conn = new URL("https://www.isna.ir/news/98061808937/%D8%A2%D8%BA%D8%A7%D8%B2-%D8%AB%D8%A8%D8%AA-%D9%86%D8%A7%D9%85-%D8%AF%D8%B1-%D9%81%D8%B1%D8%A7%D8%AE%D9%88%D8%A7%D9%86-%D8%AC%D8%B0%D8%A8-%D9%87%DB%8C%D8%A7%D8%AA-%D8%B9%D9%84%D9%85%DB%8C-%D8%A7%D8%B2-%D9%87%D9%81%D8%AA%D9%87-%D8%A2%DB%8C%D9%86%D8%AF%D9%87").openConnection();
        InputStream reader = conn.getInputStream();
//        HTMLParser jt = new HTMLParser();
        String html = convert(reader, Charset.forName("UTF-8"));
        Document document = Jsoup.parse(html);

        //a with href
        Element link = document.select("[itemprop=articleBody]").first();

        LOGGER.info("Text: " + link.text());
        LOGGER.info(extract("https://www.isna.ir/news/98061808937/%D8%A2%D8%BA%D8%A7%D8%B2-%D8%AB%D8%A8%D8%AA-%D9%86%D8%A7%D9%85-%D8%AF%D8%B1-%D9%81%D8%B1%D8%A7%D8%AE%D9%88%D8%A7%D9%86-%D8%AC%D8%B0%D8%A8-%D9%87%DB%8C%D8%A7%D8%AA-%D8%B9%D9%84%D9%85%DB%8C-%D8%A7%D8%B2-%D9%87%D9%81%D8%AA%D9%87-%D8%A2%DB%8C%D9%86%D8%AF%D9%87", "itemprop", "articleBody"));
    }

    public static String convert(InputStream inputStream, Charset charset) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
    public static String extract(String url, String att,String value) throws MalformedURLException, IOException
    {
        URLConnection conn = new URL(url).openConnection();
        InputStream reader = conn.getInputStream();
//        HTMLParser jt = new HTMLParser();
        String html = HTMLParser.convert(reader, Charset.forName("UTF-8"));
        Document document = Jsoup.parse(html);
        Element link = document.select("[" + att + "=" + value + "]").first();
        return link.text();
    }
}
