package com.dfirago.jarvissmarthome.hub.connect;

/**
 * Created by dmfi on 29/05/2017.
 */

public interface HubConnectView {

    void onWifiConnected(String ssid);

    void onHubConnectionStarted();

    void onHubConnectionFinished();

    void onHubConnectionError();
}
