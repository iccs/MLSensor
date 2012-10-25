package eu.alertproject.iccs.mlsensor.connector.producer;

import eu.alertproject.iccs.events.alert.MailingList;
import eu.alertproject.iccs.events.api.EventFactory;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.io.JsonStringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.text.ParseException;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 21:17
 */
public class JavaxMailEventMessageCreator implements MessageCreator {
    private Logger logger = LoggerFactory.getLogger(JavaxMailEventMessageCreator.class);

    private SimpleMailMessage message;

    private int id = 0;

    public JavaxMailEventMessageCreator(SimpleMailMessage message) {
        this.message = message;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {

        long start = System.currentTimeMillis();
        TextMessage m= session.createTextMessage();

        String from = this.message.getFrom();
        //Sun, 4 Dec 2011 12:38:00 -0200
        String when = DateFormatUtils.format(this.message.getSentDate(), "EEE, d MMM yyyy HH:mm:ss Z");
        String subject = this.message.getSubject();
        String content = this.message.getText();

        try {

            MailingList mailingList = new MailingList();
            mailingList.setFrom(from);
            mailingList.setDate(DateUtils.parseDate(when, new String[]{"EEE, dd MMM yyyy HH:mm:ss Z"}));
            mailingList.setSubject(subject);
            if (content != null) {
                mailingList.setContent(content);
            }else{
                mailingList.setContent("No content");
            }

            mailingList.setMessageId("0");

            m.setText(
                    EventFactory.createMlSensorMailNewEvent(
                            id++,
                            start,
                            System.currentTimeMillis(),
                            StaticSequenceService.getNextSequence(),
                            mailingList));

        }catch (ParseException e){
            logger.warn("Couldn't parse date");
        }

        logger.trace("---------------- START --------------  " +
                    "Create json string " +
                    "\n\n\n" +
                    "{}" +
                    "\n\n---------------- END -------------- \n",m.getText());

        return m;
    }
}
