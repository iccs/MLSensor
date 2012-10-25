package eu.alertproject.iccs.mlsensor.subscribers.nabble;

import eu.alertproject.iccs.events.alert.MailingList;
import eu.alertproject.iccs.mlsensor.subscribers.nabble.internal.XstreamXMLToMailingListService;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/31/12
 * Time: 1:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class XStreamlXMLToMailingListServiceTest {


    @Test
    public void fromXml() throws IOException {

        XstreamXMLToMailingListService xstreamXMLToMailingListService = new XstreamXMLToMailingListService();
        MailingList mailingList = xstreamXMLToMailingListService.fromXml(IOUtils.toString(XStreamlXMLToMailingListServiceTest.class.getResourceAsStream("/nabbleXml.txt")));

        Assert.assertNotNull(mailingList);

        Assert.assertEquals("nfleury<nicholas.fleury@petalslink.com>",mailingList.getFrom());
        Assert.assertEquals("Re: importing Petal BPM as project in Eclipse",mailingList.getSubject());
        Assert.assertEquals(IOUtils.toString(XStreamlXMLToMailingListServiceTest.class.getResourceAsStream("/content.txt")),mailingList.getContent());
        Assert.assertEquals("1334934635566-3926302.post@n3.nabble.com",mailingList.getMessageId());
        Assert.assertEquals("3924865",mailingList.getInReplyTo());
        Assert.assertEquals("3926302",mailingList.getReference());

    }

    @Test
    public void testEncoding() throws SAXParseException{




    }
    @Test
    public void fromXmlIgnore() throws IOException{

        XstreamXMLToMailingListService xstreamXMLToMailingListService = new XstreamXMLToMailingListService();
        MailingList mailingList = xstreamXMLToMailingListService.fromXml(IOUtils.toString(XStreamlXMLToMailingListServiceTest.class.getResourceAsStream("/nabbleXmlIgnore.txt")));

        Assert.assertNull(mailingList);

    }
}
