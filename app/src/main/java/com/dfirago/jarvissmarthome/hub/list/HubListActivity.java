package com.dfirago.jarvissmarthome.hub.list;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.dfirago.jarvissmarthome.R;
import com.dfirago.jarvissmarthome.databinding.ActivityHubListBinding;
import com.dfirago.jarvissmarthome.hub.connect.HubConnectActivity;
import com.dfirago.jarvissmarthome.hub.list.adapters.HubListAdapter;
import com.dfirago.jarvissmarthome.hub.list.model.HubModel;
import com.dfirago.jarvissmarthome.module.ModuleListActivity;

import java.util.List;


/**
 * Created by dmfi on 29/05/2017.
 */

public class HubListActivity extends AppCompatActivity implements HubListView {

    private HubListAdapter hubAdapter;
    private HubListPresenter hubPresenter;
    private ActivityHubListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_hub_list);

        ActivityCompat.requestPermissions(HubListActivity.this,
                new String[]{
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET
                }, 1);

        hubPresenter = new HubListPresenter(getApplicationContext());

        hubAdapter = new HubListAdapter(hubPresenter);

        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        binding.hubRecyclerView.setAdapter(hubAdapter);
        binding.hubRecyclerView.setLayoutManager(layoutManager);
        binding.hubSwipeRefresh.setRefreshing(false);
        binding.hubSwipeRefresh.setOnRefreshListener(() -> hubPresenter.detectHubs());
    }

    @Override
    protected void onResume() {
        super.onResume();
        hubPresenter.attachView(this);
        hubPresenter.detectHubs();
    }

    @Override
    protected void onPause() {
        hubPresenter.detachView();
        super.onPause();
    }

    @Override
    public void showHubs(List<HubModel> hubList) {
        hubAdapter.setData(hubList);
        hubAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetectHubsStarted() {
        binding.hubSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void onDetectHubsFinished() {
        binding.hubSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onDetectHubsError() {
        Toast.makeText(this, "Failed to detect hubs!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectedHubSelected(String ssid) {
        startActivity(ModuleListActivity.getIntent(this));
    }

    @Override
    public void onNewHubSelected(String ssid) {
        startActivity(HubConnectActivity.forHub(this, ssid));
    }
}
