package eu.alertproject.iccs.mlsensor.mail.api;

import javax.mail.Message;

/**
 * Created by IntelliJ IDEA.
 * User: fotis
 * Date: 18/07/12
 * Time: 07:19
 */
public interface MailServiceVisitor {
    boolean handle(Message message);
}
