package eu.alertproject.iccs.mlsensor.connector.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;


/**
 * User: fotis
 * Date: 04/11/11
 * Time: 19:41
 */
@Component("mlMessagePublisher")
public class ActiveMQMessagePublisher implements MLMessagePublisher{
    private Logger logger = LoggerFactory.getLogger(ActiveMQMessagePublisher.class);

    @Autowired
    private JmsTemplate template;

    @Autowired
    private Destination cscoDest;

    @Override
    public void sendMessage(String message){

        logger.trace("void sendMessage()");
        template.send(
                cscoDest,
                new MailMessageCreator(message)
        );
    }

}
