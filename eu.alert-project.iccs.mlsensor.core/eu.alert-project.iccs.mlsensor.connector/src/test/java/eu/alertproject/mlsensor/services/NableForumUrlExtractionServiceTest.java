package eu.alertproject.mlsensor.services;

import eu.alertproject.iccs.mlsensor.connector.services.NableForumUrlExtractionService;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/30/12
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class NableForumUrlExtractionServiceTest {

    @Test
    public void testExtraction() throws IOException {

        NableForumUrlExtractionService nableForumUrlExtractionService = new NableForumUrlExtractionService("http://forum.petalslink.com/");

        String s = nableForumUrlExtractionService.extractUrl(IOUtils.toString(NableForumUrlExtractionService.class.getResourceAsStream("/sampleMail.txt")));
        Assert.assertNotNull(s);

        Assert.assertEquals("http://forum.petalslink.com/bc-soap-4-1-consume-mode-JSON-tp3988255p4025049.html",s);


    }
}
