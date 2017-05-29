package com.dfirago.jarvissmarthome.hub.list;

import com.dfirago.jarvissmarthome.hub.list.model.HubModel;

import java.util.List;

/**
 * Created by dmfi on 29/05/2017.
 */

public interface HubListView {

    void showHubs(List<HubModel> hubList);

    void onDetectHubsStarted();

    void onDetectHubsFinished();

    void onDetectHubsError();

    void onConnectedHubSelected(String ssid);

    void onNewHubSelected(String ssid);

}
