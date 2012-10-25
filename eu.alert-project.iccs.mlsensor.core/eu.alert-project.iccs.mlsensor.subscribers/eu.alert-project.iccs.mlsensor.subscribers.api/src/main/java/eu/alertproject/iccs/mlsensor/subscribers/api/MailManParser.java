package eu.alertproject.iccs.mlsensor.subscribers.api;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * User: fotis
 * Date: 02/11/11
 * Time: 22:22
 */

@Service("mailParser")
public class MailManParser implements MailParser {

    private Logger logger = LoggerFactory.getLogger(MailManParser.class);

    public static final String END_OF_MESSAGE="-------------- next part --------------";

    @Override
    public void parse(Iterator<String> lines, MailActionVisitor visitor) {

        StringBuffer sb = new StringBuffer();
        while(lines.hasNext()){

            String line = lines.next();
            logger.trace("void parse() Handling line {} ",line);

            if(StringUtils.equals(line,END_OF_MESSAGE)){
                visitor.visit(sb.toString());
                sb = new StringBuffer();
            }else{
                sb.append(line+"\n");
            }

        }

        //the last message in the queue
        visitor.visit(sb.toString());

    }
}
