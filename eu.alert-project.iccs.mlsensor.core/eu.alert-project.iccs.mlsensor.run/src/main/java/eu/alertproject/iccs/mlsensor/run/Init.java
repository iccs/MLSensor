package eu.alertproject.iccs.mlsensor.run;

import eu.alertproject.iccs.mlsensor.subscribers.api.MailDownloader;
import eu.alertproject.iccs.mlsensor.subscribers.api.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:49
 */
public class Init {

    private static Logger logger = LoggerFactory.getLogger(Init.class);

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "/ml-sensor/applicationContext.xml"
        );


        Properties systemProperties = (Properties) context.getBean("systemProperties");
        Runner runner = (Runner) context.getBean("runner");
        logger.info("init() Initializing ");
        runner.run(systemProperties);

    }

}
