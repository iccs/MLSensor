package eu.alertproject.iccs.mlsensor.parsers;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * User: fotis
 * Date: 09/11/11
 * Time: 21:36
 */
public class MailManParserTest {

    private ArrayList<String> list;
    private MailManParser parser;

    @Before
    public void setup(){
        list = new ArrayList<String>();
        parser = new MailManParser();

    }


    @Test
    public void parseMessage() throws IOException {

        LineIterator lineIterator = IOUtils.lineIterator(
                getClass().getResourceAsStream("/test-mail-man.txt"),
                "utf-8"
        );

        StoreVisitor storeVisitor = new StoreVisitor();
        parser.parse(lineIterator,      storeVisitor);

        LineIterator resultsIterator = IOUtils.lineIterator(
                getClass().getResourceAsStream("/result-mail-man.txt"),
                "utf-8"
        );

        Iterator<String> iterator = list.iterator();

        while(resultsIterator.hasNext()){
            Assert.assertEquals(resultsIterator.nextLine(),iterator.next());
        }

    }

    class StoreVisitor implements MailActionVisitor{

        @Override
        public void visit(String message) {

            String[] lines = message.split("\n");
            Collections.addAll(list,lines);
        }
    }

}
