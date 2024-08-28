/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import config.config;

/**
 *
 * @author baghe
 */
public class fetchThreadPool {

    private static final Logger LOGGER = Logger.getLogger(fetchThreadPool.class);

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws IOException, InterruptedException {
        int numberOfTreads = 10;
        Map<String, Map<String, String>> agencies;
        Map<String, Map<String, String>> agency = new HashMap<String, Map<String, String>>();
        config cnf = new config();
        agencies = cnf.readAgencies();
        Properties prop = cnf.getProperties();
        numberOfTreads = Integer.parseInt(prop.getProperty("numberOfThreads", "10").trim());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfTreads);
        //Iterator agenciesIterator=agencies.entrySet().iterator();
        //while (agenciesIterator.hasNext()) 
        for (Map.Entry me : agencies.entrySet()) {
            String key = (String) me.getKey();
            Map<String, String> pair = (Map<String, String>) me.getValue();
            agency.put(key, pair);
            fetchTask task = new fetchTask(agency);
            executor.execute(task);
            LOGGER.info(agency);
            pair.clear();
            agency.clear();
            LOGGER.info("active thread count: " + executor.getActiveCount());
        }
//        executor.awaitTermination(5L, TimeUnit.MINUTES);
        while(executor.getActiveCount()>0);
        executor.shutdown();
        LOGGER.info("all job finished");
        LOGGER.info(executor.getActiveCount());
        LOGGER.info(executor.getCompletedTaskCount());
        LOGGER.info(executor.getTaskCount());
        LOGGER.info(executor.isShutdown());
        LOGGER.info(executor.isTerminated());
        LOGGER.info(executor.isTerminating());
   //      System.exit(0);
        return;
    }
}
