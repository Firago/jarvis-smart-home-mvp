package com.dfirago.jarvissmarthome.hub.list.model;

/**
 * Created by dmfi on 29/05/2017.
 */

public class HubModel {

    private String ssid;
    private Integer signalLevel;

    public HubModel() {
    }

    public HubModel(String ssid, Integer signalLevel) {
        this.ssid = ssid;
        this.signalLevel = signalLevel;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(Integer signalLevel) {
        this.signalLevel = signalLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HubModel hubModel = (HubModel) o;
        return ssid.equals(hubModel.ssid);

    }

    @Override
    public int hashCode() {
        return ssid.hashCode();
    }

    @Override
    public String toString() {
        return "HubModel{" +
                "ssid='" + ssid + '\'' +
                ", signalLevel=" + signalLevel +
                '}';
    }
}
