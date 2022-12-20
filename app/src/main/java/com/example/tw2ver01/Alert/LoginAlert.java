package com.example.tw2ver01.Alert;

import android.content.Context;

public class LoginAlert extends AbstractAlert {
    public LoginAlert(Context context) {
        super(context);
    }

    @Override
    public void setMessage() {
        message = "Welcome!!";
    }

    @Override
    public void setTitle() {
        title = "Login Success!!";
    }
}
