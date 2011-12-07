package eu.alertproject.iccs.mlsensor.mail.internal;

import eu.alertproject.iccs.mlsensor.mail.api.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.*;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 20:17
 */
@org.springframework.stereotype.Service("mailService")
public class GMailImapsMailService implements MailService {

    private Logger logger = LoggerFactory.getLogger(GMailImapsMailService.class);

    @Autowired
    private Properties systemProperties;



    @Override
    public List<SimpleMailMessage> getUnreadMessages() throws MessagingException, IOException {


        List<SimpleMailMessage> messages = new ArrayList<SimpleMailMessage>();

        Store store = null;
        try {
            Session session = Session.getDefaultInstance(systemProperties, null);
            store = session.getStore(systemProperties.getProperty("mail.store.protocol"));
            store.connect(
                    systemProperties.getProperty("mail.imaps.host"),
                    systemProperties.getProperty("mail.imaps.username"),
                    systemProperties.getProperty("mail.imaps.password"));


            Folder folder = store.getFolder(systemProperties.getProperty("mail.imaps.folder"));
            folder.open(Folder.READ_WRITE);
            int unreadMessageCount = folder.getUnreadMessageCount();

            if(unreadMessageCount > 0 ){

                FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
                Message[] search = folder.search(ft);

                for(int i=0 ; i < search.length; i++){

                    Message e = search[i];

                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    simpleMailMessage.setFrom(e.getFrom()[0].toString()); //bad assumption fix at some point
                    simpleMailMessage.setSubject(e.getSubject());
                    simpleMailMessage.setSentDate(e.getSentDate());
                    simpleMailMessage.setText(e.getContent().toString());
                    messages.add(simpleMailMessage);
                }





                folder.setFlags(search,new Flags(Flags.Flag.SEEN),true);

            }
            folder.close(false);

        } catch (MessagingException e) {
            logger.error("Couldn't retrieve e-mail message {} ",e);
            throw e;

        } catch (IOException e) {
            logger.error("Couldn't retrieve e-mail message {} ",e);
            throw e;

        } finally {
            if(store != null){
                store.close();
                store = null;
            }
        }

        return messages;


    }


}
