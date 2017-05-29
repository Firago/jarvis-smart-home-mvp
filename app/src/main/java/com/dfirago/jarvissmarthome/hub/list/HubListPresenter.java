package com.dfirago.jarvissmarthome.hub.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.dfirago.jarvissmarthome.BasePresenter;
import com.dfirago.jarvissmarthome.hub.list.model.HubModel;
import com.dfirago.jarvissmarthome.utils.Constants;
import com.dfirago.jarvissmarthome.utils.WifiUtils;

import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmfi on 29/05/2017.
 */

public class HubListPresenter extends BasePresenter<HubListView> {

    private final Context context;
    private final WifiManager wifiManager;

    public HubListPresenter(Context context) {
        this.context = context;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public void detectHubs() {
        context.registerReceiver(new ScanResultsReceiver(),
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    public void onHubSelected(String ssid) {
        if (WifiUtils.isConnected(context, ssid)) {
            view().onConnectedHubSelected(ssid);
        } else {
            view().onNewHubSelected(ssid);
        }
    }

    private class ScanResultsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context c, Intent intent) {
            Single.just(wifiManager.getScanResults()
                    .stream()
                    .filter(scanResult -> scanResult.SSID.startsWith(Constants.HUB_NAME_PREFIX))
                    .map(scanResult -> new HubModel(scanResult.SSID, getSignalLevel(scanResult)))
                    .sorted((o1, o2) -> Integer.compare(o2.getSignalLevel(), o1.getSignalLevel()))
                    .collect(Collectors.toList()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> view().onDetectHubsStarted())
                    .subscribe(result -> {
                        view().onDetectHubsFinished();
                        view().showHubs(result);
                    }, error -> {
                        view().onDetectHubsFinished();
                        view().onDetectHubsError();
                    });
        }

        private int getSignalLevel(ScanResult scanResult) {
            return WifiManager.calculateSignalLevel(scanResult.level, Constants.SIGNAL_LEVELS);
        }
    }

    @Override
    protected Class viewClass() {
        return HubListView.class;
    }
}
