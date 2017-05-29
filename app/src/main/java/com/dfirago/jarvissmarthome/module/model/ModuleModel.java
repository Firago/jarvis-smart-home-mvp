package com.dfirago.jarvissmarthome.module.model;

/**
 * Created by dmfi on 04/01/2017.
 */

public class ModuleModel {

    private String ssid;

    public ModuleModel() {
    }

    public ModuleModel(String ssid) {
        this.ssid = ssid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    @Override
    public String toString() {
        return "ModuleModel{" +
                "ssid='" + ssid + '\'' +
                '}';
    }
}
