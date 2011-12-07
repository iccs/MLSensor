package eu.alertproject.iccs.mlsensor.mail.api;

import org.springframework.mail.SimpleMailMessage;

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
    List<SimpleMailMessage> getUnreadMessages() throws MessagingException, IOException;
}
