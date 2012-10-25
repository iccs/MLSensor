package eu.alertproject.iccs.mlsensor.connector.producer;

import eu.alertproject.iccs.events.api.ActiveMQMessageBroker;
import eu.alertproject.iccs.events.api.DataMessageCreator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 21:17
 */
public abstract class JavaxMailMessageCreator implements DataMessageCreator {
    private Logger logger = LoggerFactory.getLogger(JavaxMailMessageCreator.class);

    private javax.mail.Message message;
    private ActiveMQMessageBroker broker;


    public JavaxMailMessageCreator(javax.mail.Message message, ActiveMQMessageBroker broker) {
        this.message = message;
        this.broker = broker;
    }

    @Override
    public Message createMessage(Session session) throws JMSException{


        TextMessage m = session.createTextMessage();
        long start = System.currentTimeMillis();
        try {


            Address[] from1 = message.getFrom();
            String from = ((InternetAddress)from1[0]).toString();
            //Sun, 4 Dec 2011 12:38:00 -0200
            String when = DateFormatUtils.format(message.getSentDate(), "EEE, d MMM yyyy HH:mm:ss Z");
            Date date = DateUtils.parseDate(when, new String[]{"EEE, dd MMM yyyy HH:mm:ss Z"});

            String subject = message.getSubject();


            String content = "Couldn not determine e-mail body";
            Object originalContent = message.getContent();
            if(originalContent instanceof MimeMultipart){
                MimeMultipart mm= (MimeMultipart) originalContent;

                int count = mm.getCount();
                StringBuffer contentBuffer= new StringBuffer();
                for(int i =0; i<count;i++){

                    BodyPart bodyPart = mm.getBodyPart(i);
                    if(bodyPart.getContentType().toLowerCase().startsWith("text/plain")){
                        contentBuffer.append(bodyPart.getContent());
                    }
                }
                content= contentBuffer.toString();
                
            }else if(originalContent instanceof String){
                content = (String)originalContent;
            }

            String[] header = message.getHeader("Message-ID");
            String messageId="";
            if(!ArrayUtils.isEmpty(header)){
                messageId = header[0];
            }
            header = message.getHeader("References");
            String references="";
            if(!ArrayUtils.isEmpty(header)){
                references=header[0];
            }

            m = handle(
                    session,
                    broker.requestEventId(),
                    broker.requestSequence(),
                    start,
                    from,
                    date,
                    subject,
                    content,
                    messageId,
                    references
            );




        }catch (ParseException e){
            logger.warn("Couldn't parse date");
        } catch (MessagingException e) {
            logger.warn("Couldn't read the message");
        } catch (IOException e) {
            logger.warn("Couldn't read the message");
        }


        return m;
    }

    public abstract TextMessage handle(Session session, int id, int sequence, long start, String from, Date date, String subject, String content, String messageId, String references) throws JMSException;


}
