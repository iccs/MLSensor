package eu.alertproject.iccs.mlsensor.parsers;

/**
 * User: fotis
 * Date: 02/11/11
 * Time: 22:23
 */
public interface MailActionVisitor {
    public void visit(String message);
}
