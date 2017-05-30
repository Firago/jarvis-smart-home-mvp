package com.dfirago.jarvissmarthome.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dfirago.jarvissmarthome.R;
import com.dfirago.jarvissmarthome.databinding.ActivityModuleListBinding;
import com.dfirago.jarvissmarthome.module.adapters.ModuleListAdapter;
import com.dfirago.jarvissmarthome.module.model.ModuleModel;
import com.dfirago.jarvissmarthome.web.HubService;
import com.dfirago.jarvissmarthome.web.utils.RestServiceFactory;

import java.util.List;

/**
 * Created by dmfi on 29/05/2017.
 */

public class ModuleListActivity extends AppCompatActivity implements ModuleListView {

    private ModuleListAdapter moduleAdapter;
    private ModuleListPresenter modulePresenter;
    private ActivityModuleListBinding binding;

    public static Intent getIntent(Activity callingActivity) {
        return new Intent(callingActivity, ModuleListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_module_list);

        modulePresenter = new ModuleListPresenter(
                RestServiceFactory.createService(HubService.class));

        moduleAdapter = new ModuleListAdapter(modulePresenter);

        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        binding.moduleRecyclerView.setAdapter(moduleAdapter);
        binding.moduleRecyclerView.setLayoutManager(layoutManager);
        binding.moduleSwipeRefresh.setRefreshing(false);
        binding.moduleSwipeRefresh.setOnRefreshListener(() -> modulePresenter.detectModules());
    }

    @Override
    protected void onResume() {
        super.onResume();
        modulePresenter.attachView(this);
        modulePresenter.detectModules();
    }

    @Override
    protected void onPause() {
        modulePresenter.detachView();
        super.onPause();
    }

    @Override
    public void showModules(List<ModuleModel> moduleList) {
        moduleAdapter.setData(moduleList);
        moduleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetectModulesStarted() {
        binding.moduleSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void onDetectModulesFinished() {
        binding.moduleSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onDetectModulesError() {
        Toast.makeText(this, "Failed to detect modules!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onModuleSelected(String ssid) {
        final EditText deviceNameEditText = new EditText(this);
        deviceNameEditText.setHint("Enter module name");
        new AlertDialog.Builder(this)
                .setTitle("Adding new module")
                .setView(deviceNameEditText)
                .setPositiveButton("Add", (dialog, whichButton) -> {
                    String deviceName = deviceNameEditText.getText().toString();
                    modulePresenter.registerModule(ssid, deviceName);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> dialog.cancel())
                .show();
    }

    @Override
    public void onRegisterModuleStarted() {
        binding.moduleSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void onRegisterModuleFinished() {
        binding.moduleSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRegisterModuleSuccess() {
        Toast.makeText(getApplicationContext(), "Module added successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterModuleError() {
        Toast.makeText(getApplicationContext(), "Failed to new module", Toast.LENGTH_LONG).show();
    }
}
