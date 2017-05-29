package com.dfirago.jarvissmarthome.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by dmfi on 29/05/2017.
 */

public class WifiUtils {

    public static boolean isConnected(Context context, String ssid) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        return String.format("\"%s\"", ssid).equals(connectionInfo.getSSID());
    }
}
