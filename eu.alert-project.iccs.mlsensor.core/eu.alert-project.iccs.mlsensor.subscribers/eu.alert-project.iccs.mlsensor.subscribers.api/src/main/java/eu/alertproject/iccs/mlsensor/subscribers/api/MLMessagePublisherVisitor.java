package eu.alertproject.iccs.mlsensor.subscribers.api;

import eu.alertproject.iccs.mlsensor.connector.producer.MLMessagePublisher;
import eu.alertproject.iccs.mlsensor.parsers.MailActionVisitor;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:23
 */
public class MLMessagePublisherVisitor  implements MailActionVisitor {


    private MLMessagePublisher publisher;

    public MLMessagePublisherVisitor(MLMessagePublisher publisher){


        this.publisher = publisher;


    }

    @Override
    public void visit(String message) {

        publisher.sendMessage(message);

    }
}
