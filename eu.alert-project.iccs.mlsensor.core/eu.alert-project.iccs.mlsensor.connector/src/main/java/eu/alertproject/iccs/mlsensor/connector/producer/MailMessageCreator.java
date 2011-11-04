package eu.alertproject.iccs.mlsensor.connector.producer;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:13
 */
public class MailMessageCreator implements MessageCreator {

    private String message;

    public MailMessageCreator(String message){
        this.message = message;

    }

    @Override
    public Message createMessage(Session session) throws JMSException {

        Message m= session.createMessage();
        m.setStringProperty("Message",message);
        return m;
    }
}
