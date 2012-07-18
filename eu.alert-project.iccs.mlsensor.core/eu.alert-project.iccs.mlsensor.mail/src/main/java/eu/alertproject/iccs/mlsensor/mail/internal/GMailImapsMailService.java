package eu.alertproject.iccs.mlsensor.mail.internal;

import eu.alertproject.iccs.mlsensor.mail.api.MailService;
import eu.alertproject.iccs.mlsensor.mail.api.MailServiceVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 20:17
 */
public class GMailImapsMailService implements MailService {

    private Logger logger = LoggerFactory.getLogger(GMailImapsMailService.class);

    @Autowired
    private Properties systemProperties;



    @Override
    public List<Message> getUnreadMessages(MailServiceVisitor visitor){


        List<Message> messages = new ArrayList<Message>();

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
                    Message message = search[i];
                    if(visitor.handle(message)){
                        folder.setFlags(search,new Flags(Flags.Flag.SEEN),true);
                        messages.add(message);
                    }
                }
            }

            folder.close(false);

        } catch (MessagingException e) {
            logger.error("Couldn't retrieve e-mail message {} ",e);
        } finally {
            if(store != null){
                try{ store.close();}catch (MessagingException e){}
                store = null;
            }
        }

        return messages;


    }


}
