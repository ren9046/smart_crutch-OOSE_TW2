package com.example.tw2ver01.Alert;

import android.content.Context;

public class MapAlert extends AbstractAlert {
    public MapAlert(Context context) {
        super(context);
    }

    @Override
    public void setTitle() {
        title = "Position";
    }

    @Override
    public void setMessage() {
        message = "It will use Google play Service";
    }
}
