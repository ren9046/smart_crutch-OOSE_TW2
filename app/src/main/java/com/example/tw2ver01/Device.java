package com.example.tw2ver01;

public class Device {
    private static long deviceCode;
    private boolean bind;
    private static boolean state;

    public static void setDeviceCode(long deviceNo) {
        deviceCode = deviceNo;
    }

    public static long getDeviceCode() {
        return deviceCode;
    }
    public static void setState(boolean s){
        state = s;
    }
    public static boolean isState(){
        return state;
    }

}
