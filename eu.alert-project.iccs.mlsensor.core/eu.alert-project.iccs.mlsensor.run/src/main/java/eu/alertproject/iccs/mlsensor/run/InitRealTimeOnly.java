package eu.alertproject.iccs.mlsensor.run;

import eu.alertproject.iccs.mlsensor.connector.producer.MLRealTimeMessagePublisher;
import eu.alertproject.iccs.mlsensor.subscribers.kde.api.KdeDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;
import java.util.List;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:49
 */
public class InitRealTimeOnly {

    private static Logger logger = LoggerFactory.getLogger(InitRealTimeOnly.class);

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );


        MLRealTimeMessagePublisher realTimeMessagePublisher = (MLRealTimeMessagePublisher) context.getBean("realTimeMessagePublisher");
        realTimeMessagePublisher.setRealTimeEnabled(true);

    }

}
