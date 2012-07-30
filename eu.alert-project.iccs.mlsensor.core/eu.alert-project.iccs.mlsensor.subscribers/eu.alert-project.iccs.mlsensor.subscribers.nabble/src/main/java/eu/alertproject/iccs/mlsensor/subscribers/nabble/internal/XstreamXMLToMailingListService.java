package eu.alertproject.iccs.mlsensor.subscribers.nabble.internal;

import com.thoughtworks.xstream.XStream;
import eu.alertproject.iccs.events.alert.MailingList;
import eu.alertproject.iccs.events.api.EventFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/31/12
 * Time: 12:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class XstreamXMLToMailingListService implements XMLToMailingListService {

    private static Logger logger = LoggerFactory.getLogger(XstreamXMLToMailingListService.class);


    private Integer handle=0;
    @Override
    public MailingList fromXml(String xml) throws IOException {

        MailingList mailingList = new MailingList();
//        XStream x = new XStream();

//        Object o = x.fromXML(xml);

        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();


            String kind = getValue(xpath, doc, "kind");
            if(!kind.trim().toLowerCase().equals("post")){
                return null;
            }


            String exportId = getValue(xpath, doc, "exportId");
            String ownerEmail = getValue(xpath, doc, "ownerEmail");
            String ownerName = getValue(xpath, doc, "ownerName");
            String subject = getValue(xpath, doc, "subject");
            String message = getValue(xpath, doc, "message");
            String parentId = getValue(xpath, doc, "parentId");
            String messageID = getValue(xpath, doc, "messageID");
            String whenCreated = getValue(xpath, doc, "whenCreated");


            mailingList.setFrom(ownerName + "<"+ownerEmail+">");
            mailingList.setDate(new Date(Long.valueOf(whenCreated).longValue()));
            mailingList.setSubject(subject);
            mailingList.setContent(message);
            mailingList.setMessageId(messageID);
            mailingList.setInReplyTo(parentId);
            mailingList.setReference(exportId);


            IOUtils.write(xml, new FileOutputStream(new File("/tmp/nable-forums-"+(handle++)+".xml")));


        } catch (ParserConfigurationException e) {
            logger.error("Couldn't parse the xml body");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XPathExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
        }


        return mailingList;



    }

    public String getValue(XPath xpath,Document doc, String name) throws XPathExpressionException {


        Object evaluate = xpath.evaluate("//field[@name='"+name+"']/text()", doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) evaluate;
        for (int i = 0; i < nodes.getLength(); i++) {
            return nodes.item(i).getNodeValue();
        }


        return null;
    }
}
