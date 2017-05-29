package com.dfirago.jarvissmarthome.hub.connect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.dfirago.jarvissmarthome.BasePresenter;
import com.dfirago.jarvissmarthome.utils.WifiUtils;

import java.util.List;

/**
 * Created by dmfi on 29/05/2017.
 */

public class HubConnectPresenter extends BasePresenter<HubConnectView> {

    private final Context context;

    public HubConnectPresenter(Context context) {
        this.context = context;
    }

    public void connectToHub(String ssid, String password) {
        if (WifiUtils.isConnected(context, ssid)) {
            view().onWifiConnected(ssid);
        } else {
            view().onHubConnectionStarted();
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = "\"" + ssid + "\"";
            wifiConfig.preSharedKey = "\"" + password + "\"";
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.addNetwork(wifiConfig) == -1) {
                view().onHubConnectionFinished();
                view().onHubConnectionError();
            } else {
                List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
                for (WifiConfiguration configuredNetwork : configuredNetworks) {
                    if (configuredNetwork.SSID != null
                            && configuredNetwork.SSID.equals("\"" + ssid + "\"")) {
                        wifiManager.disconnect();
                        wifiManager.enableNetwork(configuredNetwork.networkId, true);
                        context.registerReceiver(new WifiConnectedReceiver(),
                                new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
                        wifiManager.reconnect();
                        break;
                    }
                }
            }
        }
    }

    private class WifiConnectedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info != null && info.isConnected()) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();
                view().onHubConnectionFinished();
                view().onWifiConnected(ssid);
            }
        }
    }

    @Override
    protected Class viewClass() {
        return HubConnectView.class;
    }
}
