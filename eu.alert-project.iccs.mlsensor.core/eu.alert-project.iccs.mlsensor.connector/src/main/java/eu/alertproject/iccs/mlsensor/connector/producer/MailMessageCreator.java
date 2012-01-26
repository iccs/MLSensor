package eu.alertproject.iccs.mlsensor.connector.producer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.io.JsonStringEncoder;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:13
 */
public class MailMessageCreator implements MessageCreator {

    private Logger logger = LoggerFactory.getLogger(MailMessageCreator.class);

    private String message;

    public MailMessageCreator(String message){
        this.message = message;
    }


    @Override
    public Message createMessage(Session session) throws JMSException {

        TextMessage m= session.createTextMessage();

        logger.trace(message);

        Scanner scanner = new Scanner(message);

        String from ="";
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            if(s.startsWith("From:")){
                from =StringUtils.substringAfter(s,"From: ");
                break;
            }
        }

        if(StringUtils.isEmpty(from)){
            //skip
            logger.warn("Discarding {} ",message);
            throw new JMSException("Couldn't parse the message");
        }

        String when= StringUtils.substringAfter(scanner.nextLine(), "Date: ");
        String subject = StringUtils.substringAfter(scanner.nextLine(), "Subject: ");
        String messageId = "";


        StringBuffer sb = null;
        while(scanner.hasNextLine()){

            String s = scanner.nextLine();

            //it's time to wake up
            if(s.startsWith("Message-ID: ")){
                messageId= StringUtils.substringAfter(s,"Message-ID: ");
                sb = new StringBuffer();
                //we need to move one ahead because it leaves an empty line
                scanner.nextLine();
            }else if(sb != null ){
                sb.append(s).append("\n");
            }

        }

        MailingListAction ml = new MailingListAction();
        ObjectMapper mapper = new ObjectMapper();

        try {
            ml.setFrom(from);
            ml.setDate(when);
            ml.setSubject(subject);
            ml.setText(sb != null ? sb.toString(): "");
            if(mapper.canSerialize(MailingListAction.class)){
                String s = mapper.writeValueAsString(ml);


                String context = "{" +
                        "\"profile\":{}," +
                        "\"action\": "+s+"}";

                logger.trace("Message createMessage() {} ",context);
                m.setText(context);
            }
        } catch (IOException e) {
            logger.warn("Could create json string");
//        } catch (ParseException e) {
//            logger.warn("Could create json string failed to parse date {} ",when);
        }

        logger.trace("---------------- START --------------  " +
                    "Create json string " +
                    "\n\n\n" +
                    "{}" +
                    "\n\n---------------- END -------------- \n",m.getText());


        return m;
    }
}
