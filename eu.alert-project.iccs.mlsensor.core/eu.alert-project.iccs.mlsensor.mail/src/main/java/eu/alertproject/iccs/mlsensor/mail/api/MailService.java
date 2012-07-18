package eu.alertproject.iccs.mlsensor.mail.api;

import org.springframework.mail.javamail.MimeMailMessage;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 20:39
 */
public interface MailService {
    /**
     * This method acts on an e-mail service such as POP,IMAP etc returning the
     * messages that have been successfully handled by the visitor.
     *
     * @param visitor The handling mechanism for each message
     * @return The successfull messages handled
     */
    List<Message> getUnreadMessages(MailServiceVisitor visitor);
}
