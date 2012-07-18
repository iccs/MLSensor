package eu.alertproject.iccs.mlsensor.connector.producer;

/**
 * Created by IntelliJ IDEA.
 * User: fotis
 * Date: 18/07/12
 * Time: 08:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMLRealTimeMessagePublisher implements MLRealTimeMessagePublisher{

    private boolean realTimeEnabled = false;


    public boolean isRealTimeEnabled() {
        return realTimeEnabled;
    }

    @Override
    public void setRealTimeEnabled(boolean b) {
        realTimeEnabled = b;
    }
}
