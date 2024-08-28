/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpath;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 *
 * @author baghe
 */
public class ReturnXpathText {
private HtmlPage page;
 public ReturnXpathText(String searchUrl) throws IOException {
        
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        //client.waitForBackgroundJavaScript(5000);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        LOGGER.info("url: " + searchUrl);
        page = client.getPage(searchUrl);
        page.asXml();
        client.close();
    }
        private static final Logger LOGGER = Logger.getLogger(ReturnXpathText.class);
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        ReturnXpathText rxt=new ReturnXpathText("https://www.irna.ir/news/83756342/%D9%BE%D8%B1%D9%88%DA%98%D9%87-%D8%AC%D9%85%D8%B9-%D8%A2%D9%88%D8%B1%DB%8C-%D9%BE%D9%84%D8%A7%D8%B3%D9%85%D8%A7%DB%8C-%D9%85%D9%88%D8%B1%D8%AF-%D9%86%DB%8C%D8%A7%D8%B2-%D8%AF%D8%B1%D9%85%D8%A7%D9%86-%DA%A9%D8%B1%D9%88%D9%86%D8%A7%DB%8C%DB%8C-%D9%87%D8%A7-%D8%A2%D8%BA%D8%A7%D8%B2-%D8%B4%D8%AF");  
//        String searchQuery = "Iphone 6s";
        HtmlPage page = null;
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

//        String searchUrl = "https://newyork.craigslist.org/search/sss?sort=rel&query=" + URLEncoder.encode(searchQuery, "UTF-8");
        String searchUrl = "https://www.irna.ir/news/83756342/%D9%BE%D8%B1%D9%88%DA%98%D9%87-%D8%AC%D9%85%D8%B9-%D8%A2%D9%88%D8%B1%DB%8C-%D9%BE%D9%84%D8%A7%D8%B3%D9%85%D8%A7%DB%8C-%D9%85%D9%88%D8%B1%D8%AF-%D9%86%DB%8C%D8%A7%D8%B2-%D8%AF%D8%B1%D9%85%D8%A7%D9%86-%DA%A9%D8%B1%D9%88%D9%86%D8%A7%DB%8C%DB%8C-%D9%87%D8%A7-%D8%A2%D8%BA%D8%A7%D8%B2-%D8%B4%D8%AF";

        page = client.getPage(searchUrl);
        List<HtmlElement> items;
        items = page.getByXPath("//*[@id=\"item\"]/div[2]/div[1]/div[1]/span");
        if (items.isEmpty()) {
            LOGGER.info("No items found !");
            client.close();
        } else {
                LOGGER.info(items.get(0).asText());
//                HtmlAnchor itemAnchor = ((HtmlAnchor) page.getByXPath("//*[@id=\"item\"]/div[2]/div[1]/div[1]/span"));
//
//                HtmlElement spanPrice = ((HtmlElement) page.getByXPath("//*[@id=\"item\"]/div[2]/div[2]/h1/a"));
//
//                String itemName = itemAnchor.asText();
//                String itemUrl = itemAnchor.getHrefAttribute(); // It is possible that an item doesn't have any price
//                String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();
//
            LOGGER.info(rxt.textOfXpath("//*[@id=\"item\"]/div[2]/div[2]/h1/a"));
            LOGGER.info(rxt.textOfXpath("//*[@id=\"item\"]/div[2]/div[2]/h1/a"));
            LOGGER.info(rxt.textOfXpath("//*[@id=\"item\"]/p"));
            LOGGER.info(rxt.textOfXpath("//*[@id=\"item\"]/div[3]/div"));
            client.close();
            
        }
    }

    public String textOfXpath(String xpath) throws IOException {
        LOGGER.info(xpath);
        List<HtmlElement> items;
        LOGGER.info("xpath: " + xpath);
        items = page.getByXPath(xpath);
        LOGGER.info("size of items: " + items.size());
        LOGGER.info("pure html: "+ items.get(0).asXml());
         LOGGER.info("pure text: "+ Jsoup.clean(items.get(0).asXml(),Whitelist.none()));
//        if(items.size()>0)
            return Parser.unescapeEntities(Jsoup.clean(items.get(0).asXml(),Whitelist.none()),false);
 //           return Jsoup.clean(items.get(0).asText(),Whitelist.none());
    }
//    public String textOfXpath(String xpath)
//    {
//        Document document = Jsoup.parse(html);
//        String result = Xsoup.compile(xpath +"/text()").evaluate(document).get();
//        return result;
//    }
    
}
