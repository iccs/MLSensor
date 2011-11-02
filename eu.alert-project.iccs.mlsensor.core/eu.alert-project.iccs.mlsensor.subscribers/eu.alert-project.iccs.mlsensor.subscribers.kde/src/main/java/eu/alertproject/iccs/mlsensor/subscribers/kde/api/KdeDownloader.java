package eu.alertproject.iccs.mlsensor.subscribers.kde.api;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * User: fotis
 * Date: 02/11/11
 * Time: 20:14
 */
public interface KdeDownloader {
    List<URL> fetchUrls(String url) throws IOException;
    void loadMessages(List<URL> urls) throws IOException;
}
