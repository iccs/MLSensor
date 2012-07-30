package eu.alertproject.iccs.mlsensor.subscribers.nabble.internal;

import eu.alertproject.iccs.events.alert.MailingList;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/31/12
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
public interface XMLToMailingListService {

    MailingList fromXml(String xml) throws IOException;
}
