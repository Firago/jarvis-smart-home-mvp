package com.dfirago.jarvissmarthome.module;

import com.dfirago.jarvissmarthome.BasePresenter;
import com.dfirago.jarvissmarthome.module.model.ModuleModel;
import com.dfirago.jarvissmarthome.web.HubService;
import com.dfirago.jarvissmarthome.web.model.RegisterModuleRequest;

import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmfi on 29/05/2017.
 */

public class ModuleListPresenter extends BasePresenter<ModuleListView> {

    private final HubService hubService;

    public ModuleListPresenter(HubService hubService) {
        this.hubService = hubService;
    }

    public void detectModules() {
        hubService.getModules()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view().onDetectModulesStarted())
                .subscribe(result -> {
                    view().onDetectModulesFinished();
                    view().showModules(
                            result.getDevices()
                                    .stream()
                                    .map(ModuleModel::new)
                                    .collect(Collectors.toList())
                    );
                }, error -> {
                    view().onDetectModulesFinished();
                    view().onDetectModulesError();
                });
    }

    public void onModuleSelected(String ssid) {
        view().onModuleSelected(ssid);
    }

    public void registerModule(String ssid, String name) {
        RegisterModuleRequest request = new RegisterModuleRequest();
        request.setDeviceSsid(ssid);
        request.setDeviceName(name);

        hubService.registerModule(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view().onRegisterModuleStarted())
                .subscribe(result -> view().onRegisterModuleFinished(), error -> {
                    view().onRegisterModuleFinished();
                    view().onRegisterModuleError();
                });
    }

    @Override
    protected Class viewClass() {
        return ModuleListView.class;
    }
}
