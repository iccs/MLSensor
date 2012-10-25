package eu.alertproject.iccs.mlsensor.subscribers.kde.api;

/**
 * User: fotis
 * Date: 02/11/11
 * Time: 21:10
 */
public class ContentException extends Exception {

    private String content;


    public ContentException(String content){
        super("The content to extract urls from is invalid call getContent() to access the string");
        this.content =content;

    }

    public String getContent() {
        return content;
    }
}
