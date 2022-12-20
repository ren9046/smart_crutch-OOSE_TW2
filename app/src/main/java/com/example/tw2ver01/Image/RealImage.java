package com.example.tw2ver01.Image;

import com.example.tw2ver01.Device;

import okhttp3.HttpUrl;

//Implement the setImage method to provide live image
public class RealImage implements Image {
    private HttpUrl image;

    @Override
    public void setImage() {
        image = HttpUrl.parse("http://20.194.172.51/api/Image/now/" + Device.getDeviceCode());
    }

    public HttpUrl getImage() {
        return image;
    }
}
