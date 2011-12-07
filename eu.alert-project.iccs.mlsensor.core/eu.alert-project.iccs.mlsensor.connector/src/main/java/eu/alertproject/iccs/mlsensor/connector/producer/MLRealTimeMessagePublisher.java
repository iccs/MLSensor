package eu.alertproject.iccs.mlsensor.connector.producer;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * User: fotis
 * Date: 07/12/11
 * Time: 21:02
 */
public interface MLRealTimeMessagePublisher {


    void readAndSend();
    void setRealTimeEnabled(boolean b);

}
