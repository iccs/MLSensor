package eu.alertproject.iccs.mlsensor.connector.producer;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: fotis
 * Date: 17/04/12
 * Time: 17:47
 */

public class StaticSequenceService{

    static  AtomicInteger seq = new AtomicInteger();

    public static Integer getNextSequence() {
        return seq.incrementAndGet();
    }
}
