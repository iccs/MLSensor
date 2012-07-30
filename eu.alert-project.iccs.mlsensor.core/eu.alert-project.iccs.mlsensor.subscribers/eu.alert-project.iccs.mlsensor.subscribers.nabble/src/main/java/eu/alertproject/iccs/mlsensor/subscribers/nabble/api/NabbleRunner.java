package eu.alertproject.iccs.mlsensor.subscribers.nabble.api;

import eu.alertproject.iccs.mlsensor.subscribers.api.Runner;
import eu.alertproject.iccs.mlsensor.subscribers.nabble.internal.NableArchiveHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/31/12
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class NabbleRunner implements Runner {

    private static Logger logger = LoggerFactory.getLogger(NabbleRunner.class);


    @Autowired
    NableArchiveHandler nableArchiveHandler;


    @Override
    public void run(Properties properties) {

        try {
            nableArchiveHandler.handleUrls(properties.getProperty("runner.nable.dir"));
        } catch (IOException e) {
            logger.error("Couldn't open the nable dir ",e);
        } catch (ParserConfigurationException e) {
            logger.error("Couldn't open the nable dir ",e);
        } catch (SAXException e) {
            logger.error("Couldn't read the xml file",e);
        }


    }
}
