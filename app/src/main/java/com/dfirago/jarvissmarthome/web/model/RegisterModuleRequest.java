package com.dfirago.jarvissmarthome.web.model;

/**
 * Created by dmfi on 02/05/2017.
 */
public class RegisterModuleRequest {

    private String deviceSsid;
    private String deviceName;

    public String getDeviceSsid() {
        return deviceSsid;
    }

    public void setDeviceSsid(String deviceSsid) {
        this.deviceSsid = deviceSsid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "RegisterDeviceRequest{" +
                "deviceSsid='" + deviceSsid + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
