package eu.alertproject.iccs.mlsensor.connector.producer;

import eu.alertproject.iccs.events.alert.MailingList;
import eu.alertproject.iccs.events.api.ActiveMQMessageBroker;
import eu.alertproject.iccs.events.api.EventFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:13
 */
public class MailMessageCreator{


    private Logger logger = LoggerFactory.getLogger(MailMessageCreator.class);

    private String message;
    
    public MailMessageCreator(String message){
        this.message = message;
    }

    public String createMessage(ActiveMQMessageBroker broker) throws JMSException {

        long start = System.currentTimeMillis();
        String ret= "";

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


        try {

            MailingList mailingList = new MailingList();
            mailingList.setFrom(from);
            mailingList.setDate(DateUtils.parseDate(when,new String[]{"EEE, dd MMM yyyy HH:mm:ss Z"}));
            mailingList.setSubject(subject);
            if (sb != null) {
                mailingList.setContent(sb.toString());
            }else{
                mailingList.setContent("No content");
            }

            mailingList.setMessageId(messageId);


            long end = System.currentTimeMillis();

            ret = EventFactory.createMlSensorMailNewEvent(
                            broker.requestEventId(),
                            start,
                            end,
                            broker.requestSequence(),
                            mailingList);



        }catch (ParseException e){
            logger.warn("Couldn't parse date");
        }


        return ret;
    }
}
