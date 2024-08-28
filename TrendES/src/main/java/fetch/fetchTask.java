/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetch;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.elasticsearch.client.core.CountResponse;

import document.GetCountApi;
import jhazm.Normalizer;
import time.TextToTime;
import xpath.LinkExtractor;
import xpath.ReturnXpathText;

public class fetchTask implements Runnable {

    Map<String, Map<String, String>> agency = new HashMap<String, Map<String, String>>();
    private static final Logger LOGGER = Logger.getLogger(fetchTask.class);

    public fetchTask(Map<String, Map<String, String>> agency) {
        this.agency = copy(agency);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void run() {
        try {
//            IndexApi ia = new IndexApi();
            Map<String, Object> doc = new HashMap<String, Object>();
            Normalizer normal = new Normalizer();
            Map<String, String> agencyProps = null;
            String agencyName = null;
            LOGGER.info("Executing : " + agency);
            Iterator agencyIT = agency.entrySet().iterator();
            LinkExtractor le = new LinkExtractor();
            Map.Entry mapElement = (Map.Entry) agencyIT.next();
            agencyName = (String) mapElement.getKey();
            LOGGER.info("job " + agencyName + " started");
            agencyProps = (Map<String, String>) mapElement.getValue();
            List<String> linklist = le.allInternalLinks(agencyProps.get(agencyName + ".firstpage"));
            GetCountApi gca=new GetCountApi();
            for (String s : linklist) {
                try {
                    Thread.sleep(500);
                    CountResponse countResponse=gca.getCount("fetchednews", "id", s.trim());
                    long count=countResponse.getCount();
                    LOGGER.info("job " + agencyName +":count of similar news: " + count);
                    if (count== 0) {
                        ReturnXpathText rxt = new ReturnXpathText(s.trim());
                        doc.put("id", s.trim());
                        doc.put("name", agencyProps.get(agencyName + ".name"));
                        doc.put("homeurl", agencyProps.get(agencyName + ".firstpage"));
                        doc.put("topic", agencyProps.get(agencyName + ".topic"));
                        doc.put("posite", agencyProps.get(agencyName + ".posite"));
                        doc.put("homeurl", agencyProps.get(agencyName + ".firstpage"));
                        doc.put("newsurl", s.trim());
                        doc.put("title", normal.run(rxt.textOfXpath(agencyProps.get(agencyName + ".title"))).trim());
                        doc.put("description", normal.run(rxt.textOfXpath(agencyProps.get(agencyName + ".description"))).trim());
                        String content=normal.run(rxt.textOfXpath(agencyProps.get(agencyName + ".content"))).trim();
;                        if(content.length()>200)
                            doc.put("content", content);
                        else
                            doc.put("content", " ");
//                        writeUTFToFile("title: " + normal.run(rxt.textOfXpath(agencyProps.get(agencyName + ".title"))) + "\n");
//                        writeUTFToFile("description: " + normal.run(rxt.textOfXpath(agencyProps.get(agencyName + ".description"))) + "\n");
//                        writeUTFToFile("content: " + normal.run(rxt.textOfXpath(agencyProps.get(agencyName + ".content"))) + "\n");
//                        writeUTFToFile("time text: " + rxt.textOfXpath(agencyProps.get(agencyName + ".publisheddatetime")) + "\n");
                        TextToTime ttt = new TextToTime();
                        String pt;
                        LOGGER.info("seperated:  " + agencyProps.getOrDefault(agencyName + ".seperateddatetime","false"));
                        LOGGER.info(Boolean.parseBoolean(agencyProps.getOrDefault(agencyName + ".seperateddatetime","false")));
                        if(Boolean.parseBoolean(agencyProps.getOrDefault(agencyName + ".seperateddatetime","false"))==false)
                             pt = ttt.returnMilliseconds(agencyName, rxt.textOfXpath(agencyProps.get(agencyName + ".publisheddatetime"))).trim();
                        else
                             pt = ttt.returnMilliseconds(agencyName, rxt.textOfXpath(agencyProps.get(agencyName + ".publisheddate").trim()) +"-" + rxt.textOfXpath(agencyProps.get(agencyName + ".publishedtime").trim()));
                        LOGGER.info("news date time: " + pt);
//                        writeUTFToFile("publishdatetime: " + pt + "\n");
                        if (!pt.equalsIgnoreCase("-1")) {
//                            LOGGER.info("original date detected: " + pt + " newsurl: " + s + " extracted date: "  + rxt.textOfXpath(agencyProps.get(agencyName + ".publisheddatetime")).trim());
                            doc.put("publishtime", pt);
                        } else {
                            LOGGER.info("original date not detected: " + pt + " newsurl: " + s + " extracted date: " );
                            doc.put("publishtime", Long.toString(System.currentTimeMillis()));
                        }

                        doc.put("fetchtime", Long.toString(System.currentTimeMillis()));
                        doc.put("processed", "false");
//                        IndexResponse ir = ia.indexSync("fetchednews", doc);
                        doc.clear();
                    }
                } catch (Exception e) {
                    doc.clear();
                    Map<String,Object> fu=new HashMap<String,Object>();
                   fu.put("url", s);
//                   IndexResponse ir;
//                    ir = ia.indexSync("faulturls", fu);
                    fu.clear();
                    LOGGER.info("link failed");
                    LOGGER.error(s);
                    LOGGER.error(e);
                    e.printStackTrace();
                    doc.clear();
                }
            }
            LOGGER.info("job " + agencyName + " accomplished");
            TimeUnit.SECONDS.sleep(1);
            return;
        } catch (Exception e) {
            LOGGER.info("job   failed");
            LOGGER.error(e);
            LOGGER.error(e.getMessage());
            e.printStackTrace();

//            Thread.interrupted();
//            return;
        } finally {

//            Thread.interrupted();
//            return;
        }
    }

    @SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> copy(
            Map<String, Map<String, String>> original) {
        Map<String, Map<String, String>> copy = new HashMap<String, Map<String, String>>();
        for (@SuppressWarnings("rawtypes") Map.Entry entry : original.entrySet()) {
            copy.put((String) entry.getKey(), new HashMap<String, String>((Map<? extends String, ? extends String>) entry.getValue()));
        }
        return copy;
    }
    public void writeUTFToFile(String s) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        try (OutputStream outputStream = new FileOutputStream("c:\\log\\output.txt",true)) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(s);
            outputStreamWriter.close();
        }
    }

}
