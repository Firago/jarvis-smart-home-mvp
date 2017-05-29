package com.dfirago.jarvissmarthome.hub.connect;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dfirago.jarvissmarthome.R;
import com.dfirago.jarvissmarthome.databinding.ActivityHubConnectBinding;
import com.dfirago.jarvissmarthome.module.ModuleListActivity;

/**
 * Created by dmfi on 29/05/2017.
 */

public class HubConnectActivity extends AppCompatActivity implements HubConnectView {

    private static final String EXTRA_HUB_SSID = "extra_selected_hub";

    private HubConnectPresenter hubPresenter;
    private ActivityHubConnectBinding binding;

    public static Intent forHub(Activity callingActivity, String hubSsid) {
        final Intent result = new Intent(callingActivity, HubConnectActivity.class);
        result.putExtra(EXTRA_HUB_SSID, hubSsid);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hub_connect);
        hubPresenter = new HubConnectPresenter(getApplicationContext());
        binding.setPresenter(hubPresenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hubPresenter.attachView(this);
        loadExtraDataIfNeeded();
    }

    @Override
    protected void onPause() {
        hubPresenter.detachView();
        super.onPause();
    }

    @Override
    public void onWifiConnected(String ssid) {
        if (ssid.equals("\"" + binding.getSsid() + "\"")) {
            startActivity(ModuleListActivity.getIntent(this));
        }
    }

    @Override
    public void onHubConnectionStarted() {
        binding.hubConnectionSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void onHubConnectionFinished() {
        binding.hubConnectionSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onHubConnectionError() {
        Toast.makeText(this, "Connection failed. Please, make sure that password is correct.", Toast.LENGTH_SHORT).show();
    }

    private void loadExtraDataIfNeeded() {
        final Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.hasExtra(EXTRA_HUB_SSID)) {
            binding.setSsid(startingIntent.getStringExtra(EXTRA_HUB_SSID));
        } else {
            Toast.makeText(this, "Failed to set hub SSID.", Toast.LENGTH_SHORT).show();
        }
    }
}
