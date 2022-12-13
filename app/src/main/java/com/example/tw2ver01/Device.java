package com.example.tw2ver01;

public class Device {
    private long deviceCode;
    private boolean bind;

    public void setBind(boolean bind) {
        this.bind = bind;
    }
    public void setDeviceCode(long deviceCode) {
        this.deviceCode = deviceCode;
    }
    public boolean getBind() {
        return  this.bind;
    }
    public long getDeviceCode() {
        return this.deviceCode;
    }

}
