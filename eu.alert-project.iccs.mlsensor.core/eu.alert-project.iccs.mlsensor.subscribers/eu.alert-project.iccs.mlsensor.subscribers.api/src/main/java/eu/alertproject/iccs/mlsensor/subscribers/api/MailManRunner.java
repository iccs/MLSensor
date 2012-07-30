package eu.alertproject.iccs.mlsensor.subscribers.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/31/12
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailManRunner implements Runner {

    private static Logger logger = LoggerFactory.getLogger(MailManRunner.class);

    @Autowired
    MailDownloader mailDownloader;

    @Override
    public void run(Properties properties){
        logger.info("init() Initializing ");

        if(!properties.containsKey("subscribers.mailman.url")){
            throw new IllegalArgumentException("There is no subscribers.mailman.url property");
        }

        String url = properties.getProperty("subscribers.mailman.url");
        try {
            List<URL> urls = mailDownloader.fetchUrls(url);
            mailDownloader.loadMessages(urls);

        } catch (Exception e) {
            logger.error("Couldn't load url {}", url, e);
        }
    }
}
