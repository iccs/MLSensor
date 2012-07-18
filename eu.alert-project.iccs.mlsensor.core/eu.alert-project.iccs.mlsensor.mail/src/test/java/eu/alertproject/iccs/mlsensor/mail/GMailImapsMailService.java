package eu.alertproject.iccs.mlsensor.mail;

import eu.alertproject.iccs.mlsensor.mail.api.MailService;
import eu.alertproject.iccs.mlsensor.mail.api.MailServiceVisitor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 20:25
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GMailImapsMailService{

    private Logger logger = LoggerFactory.getLogger(GMailImapsMailService.class);

    @Autowired
    MailService mailService;

    @Test
    public void testRead(){

        //send a coumple of e-mail to gmail account
        List<Message> unreadMessages = mailService.getUnreadMessages(new MailServiceVisitor() {
            @Override
            public boolean handle(Message message) {
                return true;
            }
        });

        Assert.assertEquals(unreadMessages.size(), 2, 0);

    }


}
