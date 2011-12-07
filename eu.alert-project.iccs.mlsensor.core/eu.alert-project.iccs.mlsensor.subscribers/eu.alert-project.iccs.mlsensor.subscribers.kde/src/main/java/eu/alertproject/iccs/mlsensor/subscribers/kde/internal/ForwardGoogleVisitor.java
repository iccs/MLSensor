package eu.alertproject.iccs.mlsensor.subscribers.kde.internal;

import eu.alertproject.iccs.mlsensor.subscribers.api.MailActionVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.jms.JMSException;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 21:45
 */
public class ForwardGoogleVisitor implements MailActionVisitor {

    private Logger logger = LoggerFactory.getLogger(ForwardGoogleVisitor.class);

    private JavaMailSender mailSender;

    public ForwardGoogleVisitor(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void visit(String message) {


        SimpleMailMessageCreator simpleMailMessageCreator = new SimpleMailMessageCreator();
        try {

            SimpleMailMessage message1 = simpleMailMessageCreator.createMessage(message);
            message1.setFrom("fotisp@mail.ntua.gr");
            message1.setTo("iccs.stardom@gmail.com");
            mailSender.send(message1);

        } catch (JMSException e) {
            logger.warn("Wrong message {} ",e);
        }

    }
}
