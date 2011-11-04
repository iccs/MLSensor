package eu.alertproject.iccs.mlsensor.web;

import eu.alertproject.iccs.mlsensor.subscribers.kde.api.KdeDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:49
 */
@Component
public class Init {

    private Logger logger = LoggerFactory.getLogger(Init.class);

    @Autowired
    private KdeDownloader kdeDownloader;

    @PostConstruct
    public void init() {
        logger.info("init() Initializing ");

        String url = "http://mail.kde.org/pipermail/kde-hardware-devel";
        try {

            List<URL> urls = kdeDownloader.fetchUrls(url);
            kdeDownloader.loadMessages(urls);

        } catch (Exception e) {
            logger.error("Couldn't load url {}", url, e);
        }
    }

}
