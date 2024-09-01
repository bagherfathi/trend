/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import xpath.ReturnXpathText;

/**
 *
 * @author baghe
 */
public class config {

    private static final Logger LOGGER = Logger.getLogger(config.class);
    String configPath;
    FileInputStream input;

    public config() throws IOException {
        String path = new File(".").getCanonicalPath();
        configPath = path + "/" + "config.properties";
        input = new FileInputStream(new File(configPath));
    }

    public static void main(String[] args) throws IOException {
        String path = new File(".").getCanonicalPath();
        LOGGER.info(path);
        config c = new config();
        c.checkAgenciesInConfig();
//        Properties props = c.getProperties();
//        Enumeration<Object> valueEnumeration = props.elements();
//        while (valueEnumeration.hasMoreElements()) {
//            LOGGER.info(valueEnumeration.nextElement());
//        }
//        Enumeration<Object> keyEnumeration = props.keys();
//        while (keyEnumeration.hasMoreElements()) {
//            LOGGER.info(keyEnumeration.nextElement());
//        }
//        Map<String, Map<String, String>> p = c.readAgencies();
//        LOGGER.info("Created hashmap is" + p);
//        LOGGER.info("#########################################################################");
//        for (Map.Entry mapElement : p.entrySet()) {
//            String key = (String) mapElement.getKey();
//            Map<String, String> pair = (Map<String, String>) mapElement.getValue();
//            LOGGER.info(key);
//            LOGGER.info("#########################################################################");
//            for (Map.Entry me : pair.entrySet()) {
//                System.out.print(me.getKey());
//                System.out.print(":");
//                LOGGER.info(me.getValue());
//            }
//            LOGGER.info("########################################################################");
//        }
//        // c.saveConfig(props);
    }

    public Properties getProperties() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        return props;
    }

    public void setProperty(String key, String value) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        props.setProperty(key, value);
        props.store(new FileWriter(configPath), "store to properties file");
    }

    public void saveConfig(Properties props) throws IOException {
        String newAppConfigPropertiesFile = configPath;
        props.store(new FileWriter(newAppConfigPropertiesFile), "store to properties file");
    }

    public Map<String, Map<String, String>> readAgencies() throws FileNotFoundException, IOException {
        Map<String, Map<String, String>> agencies = new HashMap<String, Map<String, String>>();
        String[] agencyName;
        Properties props = new Properties();
        props.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        agencyName = props.getProperty("newsagencies").split(",");
        for (int i = 0; i < agencyName.length; i++) {
            Map<String, String> m = new HashMap<String, String>();
            String dt = agencyName[i] + ".publisheddatetime";
            String title = agencyName[i] + ".title";
            String description = agencyName[i] + ".description";
            String content = agencyName[i] + ".content";
            String firstpage = agencyName[i] + ".firstpage";
            String name = agencyName[i] + ".name";
            String topic = agencyName[i] + ".topic";
            String samplepage = agencyName[i] + ".samplepage";
            String posite = agencyName[i] + ".posite";
            String seperateddatetime = agencyName[i] + ".seperateddatetime";
            String publisheddate = agencyName[i] + ".publisheddate";
            String publishedtime = agencyName[i] + ".publishedtime";
            m.put(dt, props.getProperty(dt));
            m.put(title, props.getProperty(title));
            m.put(description, props.getProperty(description));
            m.put(content, props.getProperty(content));
            m.put(firstpage, props.getProperty(firstpage));
            m.put(name, props.getProperty(name));
            m.put(topic, props.getProperty(topic));
            m.put(samplepage, props.getProperty(samplepage));
            m.put(posite, props.getProperty(posite));
            m.put(seperateddatetime, props.getProperty(seperateddatetime));
            m.put(publisheddate, props.getProperty(publisheddate));
            m.put(publishedtime, props.getProperty(publishedtime));
            agencies.put(agencyName[i], m);
        }
        return agencies;
    }

    public void checkAgenciesInConfig() throws FileNotFoundException, IOException {
        String[] agencyName;
        Properties props = new Properties();
        props.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        agencyName = props.getProperty("newsagencies").split(",");
        for (int i = 0; i < agencyName.length; i++) {
            LOGGER.info("----------------------------------------------------------------------------------------------------------------------");
            LOGGER.info("----------------------------------------------------------------------------------------------------------------------");
            LOGGER.info(agencyName[i] + " started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//           String dt;
//           dt = agencyName[i] + ".publisheddatetime";
            String title = agencyName[i] + ".title";
            String description = agencyName[i] + ".description";
            String content = agencyName[i] + ".content";
            String samplepage = agencyName[i] + ".samplepage";
            ReturnXpathText xp = new ReturnXpathText(props.getProperty(samplepage));
            if(Boolean.parseBoolean(props.getProperty(agencyName[i] + ".seperateddatetime","false"))==false)
                            LOGGER.info("publisheddatetime: "  + xp.textOfXpath(props.get(agencyName[i] + ".publisheddatetime").toString(),"publisheddatetime").trim());
            else
                             LOGGER.info("publisheddatetime: "  + xp.textOfXpath(props.getProperty(agencyName[i] + ".publisheddate"),"publisheddate") +"-" + xp.textOfXpath(props.getProperty(agencyName[i] + ".publishedtime"),"publisheddate"));
            LOGGER.info(title + " : " + xp.textOfXpath(props.getProperty(title),"title"));
            LOGGER.info(description + " : " + xp.textOfXpath(props.getProperty(description),"description"));
            LOGGER.info(content + " : " + xp.textOfXpath(props.getProperty(content),"content"));
//            TextToTime ttt=new TextToTime();
//            writeUTFToFile("dt" + " : " + xp.textOfXpath(props.getProperty(dt)));
//            writeUTFToFile("\n");
//            if(Boolean.parseBoolean(props.getProperty(agencyName[i] + ".seperateddatetime","false"))==false)
//                            writeUTFToFile("publisheddatetime: "  + ttt.returnMilliseconds(agencyName[i], xp.textOfXpath(props.get(agencyName[i] + ".publisheddatetime").toString()).trim()));
//            else
//                             writeUTFToFile("publisheddatetime: "  + ttt.returnMilliseconds(agencyName[i], xp.textOfXpath(props.getProperty(agencyName[i] + ".publisheddate").trim()) +"-" + xp.textOfXpath(props.getProperty(agencyName[i] + ".publishedtime").trim())));
//            //writeUTFToFile("millisecond" + " : " + ttt.returnMilliseconds(agencyName[i], xp.textOfXpath(props.getProperty(dt))));
//            writeUTFToFile("\n");
//            writeUTFToFile("title" + " : " + xp.textOfXpath(props.getProperty(title)));
//            writeUTFToFile("\n");
//            writeUTFToFile("description" + " : " + xp.textOfXpath(props.getProperty(description)));
            writeUTFToFile("\n");
            writeUTFToFile("content" + " : " + xp.textOfXpath(props.getProperty(content),"content"));
            writeUTFToFile("\n");
            LOGGER.info(agencyName[i] + " ended!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            LOGGER.info("----------------------------------------------------------------------------------------------------------------------");
            LOGGER.info("----------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void writeUTFToFile(String s) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        try (OutputStream outputStream = new FileOutputStream("c:\\log\\output.txt",true)) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(s);
            outputStreamWriter.close();
        }
    }
}
