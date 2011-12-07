package eu.alertproject.iccs.mlsensor.connector.producer;

import javax.mail.Message;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 19:30
 */
public interface MLMessagePublisher  {


    void sendMessage(String message);

}
