package eu.alertproject.iccs.mlsensor.connector.producer;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:13
 */
public class MailMessageCreator implements MessageCreator {

    private String message;

    public MailMessageCreator(String message){
        this.message = message;

    }

    @Override
    public Message createMessage(Session session) throws JMSException {

        TextMessage m= session.createTextMessage();


        /*
        {
            "profile":{},
            "action":{
                "from":"Fotis Paraskevopoulos <hello@world.com>",
                "date":"Mon, 15 Sep 2008 15:13:04 +0200",
                "subject":"What is up !",
                "text":"This is a long long text"
            }

        }


         */

        m.setText("        {\n" +
                        "            \"profile\":{},\n" +
                        "            \"action\":{\n" +
                        "                \"from\":\"Fotis Paraskevopoulos <hello@world.com>\",\n" +
                        "                \"date\":\"Mon, 15 Sep 2008 15:13:04 +0200\",\n" +
                        "                \"subject\":\"What is up !\",\n" +
                        "                \"text\":\"This is a long long text\"\n" +
                        "            }\n" +
                        "        \n" +
                        "        }"
        );
        return m;
    }
}
