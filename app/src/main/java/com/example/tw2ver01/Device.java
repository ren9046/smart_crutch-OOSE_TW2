package com.example.tw2ver01;

public class Device {
    private static long deviceCode;
    private boolean bind;

    /* public boolean getBind() {
         return  this.bind;
     }*/
    public static long getDeviceCode() {
        return deviceCode;
    }

    /*public void setBind(boolean bind) {
        this.bind = bind;
    }*/
    public static void setDeviceCode(long deviceNo) {
        deviceCode = deviceNo;
    }

}
