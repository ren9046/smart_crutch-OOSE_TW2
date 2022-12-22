package com.example.tw2ver01;

public class Device {
    private static long deviceCode;
    private static boolean state;
    private boolean bind;

    public static long getDeviceCode() {
        return deviceCode;
    }

    public static void setDeviceCode(long deviceNo) {
        deviceCode = deviceNo;
    }

    public static boolean isState() {
        return state;
    }

    public static void setState(boolean s) {
        state = s;
    }

}
