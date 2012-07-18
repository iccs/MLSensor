package eu.alertproject.iccs.mlsensor.parsers;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 20:21
 */
public class CompoundVisitor implements MailActionVisitor{
    private MailActionVisitor[] visitors;

    public CompoundVisitor(MailActionVisitor ... visitors){
        this.visitors = visitors;
    }

    @Override
    public void visit(String message) {
        if(visitors ==null || visitors.length <=0){
            return;
        }

        for(MailActionVisitor visitor: visitors){
            visitor.visit(message);
        }

    }
}
