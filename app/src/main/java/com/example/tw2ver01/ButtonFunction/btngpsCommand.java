package com.example.tw2ver01.ButtonFunction;

import android.content.Intent;

import com.example.tw2ver01.MainActivity;
import com.example.tw2ver01.page_maps1;

public class btngpsCommand implements Command {
    private final MainActivity btngps;

    public btngpsCommand(MainActivity b) {
        btngps = b;
    }

    @Override
    public void execute() {
        Intent intent = new Intent();
        intent.setClass(btngps, page_maps1.class);
        btngps.startActivity(intent);

    }


}

