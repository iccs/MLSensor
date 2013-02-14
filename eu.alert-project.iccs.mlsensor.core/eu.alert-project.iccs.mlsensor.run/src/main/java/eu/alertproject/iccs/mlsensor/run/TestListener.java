package eu.alertproject.iccs.mlsensor.run;

import eu.alertproject.iccs.events.api.AbstractActiveMQHandler;
import eu.alertproject.iccs.events.api.ActiveMQMessageBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 12/02/13
 * Time: 3:23 PM
 */
@Service("mailTestListener")
public class TestListener extends AbstractActiveMQHandler {

    private Logger logger = LoggerFactory.getLogger(TestListener.class);


    @Override
    public void process(ActiveMQMessageBroker broker, Message message) throws IOException, JMSException {

        logger.trace("void onMessage([message]) {} ",message);

        if(message instanceof TextMessage){
            try {
                logger.trace(((TextMessage) message).getText());
            } catch (JMSException e) {
                logger.error("Couldn't read text message",e);
            }
        }

    }
}
