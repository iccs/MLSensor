package eu.alertproject.iccs.mlsensor.connector.producer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.io.JsonStringEncoder;
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
                sb.append(s+"\n");
            }

        }

        String s =
        //create json string
        "        {\n" +
            "            \"profile\":{},\n" +
            "            \"action\":{\n" +
            "                \"from\":\""+from+"\",\n" +
            "                \"date\":\""+when+"\",\n" +
            "                \"subject\":\""+subject+"\",\n" +
            "                \"text\":\""+JsonStringEncoder.getInstance().quoteAsString(sb.toString())+"\"\n" +
            "            }\n" +
            "        \n" +
            "        }";

        logger.trace("---------------- START --------------  " +
                    "Create json string " +
                    "\n\n\n" +
                    "{}" +
                    "\n\n---------------- END -------------- \n",s);
        m.setText(s);

        return m;
    }
}
