package eu.alertproject.iccs.mlsensor.subscribers.nabble.internal;

import eu.alertproject.iccs.events.alert.MailingList;
import eu.alertproject.iccs.events.api.EventFactory;
import eu.alertproject.iccs.mlsensor.connector.producer.MLMessagePublisher;
import eu.alertproject.iccs.mlsensor.connector.producer.MLRealTimeMessagePublisher;
import eu.alertproject.iccs.mlsensor.parsers.CompoundVisitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created with IntelliJ IDEA.
 * User: fotis
 * Date: 7/30/12
 * Time: 11:57 PM
 */
@Service("nableArchiveHandler")
public class NableArchiveHandler {

    private Logger logger = LoggerFactory.getLogger(NableArchiveHandler.class);

    private Pattern compile = Pattern.compile("^\\s+<td><A href=\"(.*)\">.*</a></td>");


    private int id = 123412341;
    private int sequence = 123412341;


    @Autowired
    MLRealTimeMessagePublisher realTimeMessagePublisher;

    @Autowired
    MLMessagePublisher mlMessagePublisher;

    @Autowired
    XMLToMailingListService xmlToMailingListService;


    public void handleUrls(String directory) throws IOException, ParserConfigurationException, SAXException {

        //list all the file
        Collection<File> zip = FileUtils.listFiles(new File(directory), new SuffixFileFilter("zip"), FalseFileFilter.FALSE);

        int elementCounter = 0;
        for(File f: zip){

            //get the content
            ZipFile zipFile = new ZipFile(f.getAbsolutePath());

            Enumeration<? extends ZipEntry> entries = zipFile.entries();


            while(entries.hasMoreElements()) {

                long start = System.currentTimeMillis();
                ZipEntry z = entries.nextElement();

                logger.trace("void start() {} ",z.getName());
                if(z.getName().endsWith(".xml")){

                    String xml = IOUtils.toString(zipFile.getInputStream(z));

                    MailingList mailingList = xmlToMailingListService.fromXml(xml);
                    if(mailingList != null){

                        String mlSensorMailNewEvent = EventFactory.createMlSensorForumNewEvent(
                                id,
                                start,
                                System.currentTimeMillis(),
                                sequence,
                                mailingList);

                        logger.trace("TextMessage handle() Generated {} ",mlSensorMailNewEvent);
                        mlMessagePublisher.sendMessage(mlSensorMailNewEvent);


                    }else{
                        logger.info("Ignoring {} ",xml);
                    }

                }else{
                    logger.info("void start() Not an xml file {}", z.getName());
                }


            }

            logger.trace("void start() Handled {} events",elementCounter);

        }

    }
}
