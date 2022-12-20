package com.example.tw2ver01.Image;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tw2ver01.R;

import okhttp3.HttpUrl;

//Provide the default image by implementing the setImage method
//Wait for the user to press the button and then load the live image
public class ProxyImage extends AppCompatActivity implements Image {
    private int image;
    private RealImage liveimg;

    @Override
    public void setImage() {
        if (liveimg == null) {
            liveimg = new RealImage();
        }
        image = R.drawable.defaultimage;
    }

    public HttpUrl LoadLiveImage() {
        liveimg.setImage();
        return liveimg.getImage();
    }

    public int getImage() {
        return image;
    }

}
