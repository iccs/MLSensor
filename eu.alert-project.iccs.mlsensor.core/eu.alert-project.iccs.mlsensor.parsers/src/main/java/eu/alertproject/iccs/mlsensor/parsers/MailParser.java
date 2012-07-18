package eu.alertproject.iccs.mlsensor.parsers;

import java.util.Iterator;
import java.util.List;

/**
 * User: fotis
 * Date: 02/11/11
 * Time: 22:22
 */
public interface MailParser {

    public void parse(Iterator<String> lines, MailActionVisitor visitor);
}
