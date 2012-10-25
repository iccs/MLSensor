package eu.alertproject.iccs.mlsensor.subscribers.api;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: fotis
 * Date: 11/06/12
 * Time: 21:24
 */
public class SaveEmailMessageVisitor  implements MailActionVisitor{
    private Integer counter=0;

    @Override
    public void visit(String message) {

        try {
            IOUtils.write(message,new FileOutputStream(new File("/tmp/mail/"+counter+".txt")));
            counter++;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
