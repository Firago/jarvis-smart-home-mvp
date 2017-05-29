package com.dfirago.jarvissmarthome.module;

import com.dfirago.jarvissmarthome.module.model.ModuleModel;

import java.util.List;

/**
 * Created by dmfi on 29/05/2017.
 */

public interface ModuleListView {

    void showModules(List<ModuleModel> moduleList);

    void onDetectModulesStarted();

    void onDetectModulesFinished();

    void onDetectModulesError();

    void onModuleSelected(String ssid);

    void onRegisterModuleStarted();

    void onRegisterModuleFinished();

    void onRegisterModuleError();
}
