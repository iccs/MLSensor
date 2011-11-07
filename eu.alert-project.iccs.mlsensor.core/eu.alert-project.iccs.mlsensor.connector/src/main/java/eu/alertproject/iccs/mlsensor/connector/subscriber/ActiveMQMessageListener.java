package eu.alertproject.iccs.mlsensor.connector.subscriber;

import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

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

            if(message instanceof TextMessage){
                TextMessage mqmessage = (TextMessage) message;
                logger.trace("void onMessage() {}",mqmessage.getText());
            }else{
                logger.info("This message is not of type TextMessage");
            }
        } catch (JMSException e) {
            logger.error("Error reading contents of text message",e);
        }
    }


}
