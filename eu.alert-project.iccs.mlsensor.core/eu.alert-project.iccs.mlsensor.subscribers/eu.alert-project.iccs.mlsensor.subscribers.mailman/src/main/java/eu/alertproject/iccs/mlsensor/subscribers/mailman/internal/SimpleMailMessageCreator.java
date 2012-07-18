package eu.alertproject.iccs.mlsensor.subscribers.mailman.internal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.jms.JMSException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:13
 */
public class SimpleMailMessageCreator {

    private Logger logger = LoggerFactory.getLogger(SimpleMailMessageCreator.class);

    public SimpleMailMessageCreator(){
    }

    public SimpleMailMessage createMessage(String message) throws JMSException {


        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();

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

        logger.trace("SimpleMailMessage createMessage() {} ",from);
        from = from.replace(" at ","@");
        from = from.replaceAll(" ",from);
        simpleMailMessage.setFrom(from);
        try {
            simpleMailMessage.setSentDate(DateUtils.parseDate(when,new String[]{"EEE, d MMM yyyy HH:mm:ss Z"}));
        } catch (ParseException e) {
            logger.warn("Couldn't create the date");
        }

        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(sb==null ? "None" : sb.toString());

        return simpleMailMessage;

    }
}
