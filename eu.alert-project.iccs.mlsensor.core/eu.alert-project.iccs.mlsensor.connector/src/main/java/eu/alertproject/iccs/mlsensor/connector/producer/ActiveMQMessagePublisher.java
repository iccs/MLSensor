package eu.alertproject.iccs.mlsensor.connector.producer;

import eu.alertproject.iccs.events.api.ActiveMQMessageBroker;
import eu.alertproject.iccs.events.api.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;


/**
 * User: fotis
 * Date: 04/11/11
 * Time: 19:41
 */
public class ActiveMQMessagePublisher implements MLMessagePublisher{

    private Logger logger = LoggerFactory.getLogger(ActiveMQMessagePublisher.class);

    @Autowired
    ActiveMQMessageBroker messageBroker;

    @Override
    public void sendMessage(String message){
        try {

            MailMessageCreator messageCreator = new MailMessageCreator(message);
            messageBroker.sendTextMessage(
                    Topics.ALERT_MLSensor_Mail_New,messageCreator.createMessage(messageBroker)
            );


        } catch (JMSException e) {
            logger.error("Error creating message {} ", message, e);
        }

    }

}
