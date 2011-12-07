package eu.alertproject.iccs.mlsensor.connector.producer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.lf5.util.DateFormatManager;
import org.codehaus.jackson.io.JsonStringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 21:17
 */
public class JavaxMailMessageCreator implements MessageCreator {
    private Logger logger = LoggerFactory.getLogger(JavaxMailMessageCreator.class);

    private SimpleMailMessage message;

    public JavaxMailMessageCreator(SimpleMailMessage message) {
        this.message = message;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {

        TextMessage m = session.createTextMessage();
        String from = this.message.getFrom();
        //Sun, 4 Dec 2011 12:38:00 -0200
        String when = DateFormatUtils.format(this.message.getSentDate(), "EEE, d MMM yyyy HH:mm:ss Z");
        String subject = this.message.getSubject();
        String content = this.message.getText();

        String s =
                //create json string
                "        {\n" +
                        "            \"profile\":{},\n" +
                        "            \"action\":{\n" +
                        "                \"from\":\"" + from + "\",\n" +
                        "                \"date\":\"" + when + "\",\n" +
                        "                \"subject\":\"" + subject + "\",\n" +
                        "                \"text\":\"" + JsonStringEncoder.getInstance().quoteAsString(content.toString()) + "\"\n" +
                        "            }\n" +
                        "        \n" +
                        "        }";

        logger.trace("---------------- START --------------  " +
                "Create json string " +
                "\n\n\n" +
                "{}" +
                "\n\n---------------- END -------------- \n", s);
        m.setText(s);
        return m;
    }
}
