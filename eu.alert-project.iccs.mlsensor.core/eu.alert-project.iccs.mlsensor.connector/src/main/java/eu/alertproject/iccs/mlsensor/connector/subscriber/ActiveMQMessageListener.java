package eu.alertproject.iccs.mlsensor.connector.subscriber;

import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 19:21
 */

public class ActiveMQMessageListener implements MLMessageListener{

    private Logger logger = LoggerFactory.getLogger(ActiveMQMessageListener.class);


    @Override
    public void onMessage(Message message) {

        logger.trace("void onMessage() Received {} ",message);
        try {
            if(message instanceof ActiveMQMessage){
                String text = null;
                text = ((TextMessage) message).getText();
                logger.trace("void onMessage() {}",text);
            }
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
