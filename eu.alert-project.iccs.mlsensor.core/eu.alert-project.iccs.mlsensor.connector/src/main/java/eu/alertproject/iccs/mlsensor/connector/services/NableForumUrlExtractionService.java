package eu.alertproject.iccs.mlsensor.connector.services;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/30/12
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class NableForumUrlExtractionService implements ForumUrlExtractionService{

    private Logger logger = LoggerFactory.getLogger(NableForumUrlExtractionService.class);

    private Pattern p;

    public NableForumUrlExtractionService(String forumUrl) {

        String property = forumUrl.replaceAll("\\.", "\\\\.");

        //"^"+property +".*$"
        String pattern = "^If you reply to this email, your message will be added to the discussion below:\n" +
                "("+property+"/)\n" +
                "To unsubscribe from .*, click here.\n" +
                "NAML$";
        p = Pattern.compile(pattern, Pattern.MULTILINE);


    }

    @Override
    public String extractUrl(String content){

        String ret = null;
        //get the last three lines of the message

        String[] split = content.split("\n");

        if(split != null && split.length > 3){
            //get the last three lines
            ret = split[split.length-3].trim();
        }


        return ret;

    }
}
