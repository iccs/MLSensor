package eu.alertproject.iccs.mlsensor.connector.producer;

import eu.alertproject.iccs.events.api.ActiveMQMessageBroker;
import eu.alertproject.iccs.events.api.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;


/**
 * User: fotis
 * Date: 04/11/11
 * Time: 19:41
 */
public class ActiveMQNabbleMessagePublisher implements MLMessagePublisher{

    private Logger logger = LoggerFactory.getLogger(ActiveMQNabbleMessagePublisher.class);

    @Autowired
    private ActiveMQMessageBroker messageBroker;

    @Override
    public void sendMessage(String message){

        logger.trace("void sendMessage() {} ",message);


        try {
            messageBroker.sendTextMessage(
                    Topics.ALERT_MLSensor_Mail_New,
                    message
            );

        } catch (JmsException e) {
            logger.error("Error sending message {} ",message,e);
        }

    }

}
