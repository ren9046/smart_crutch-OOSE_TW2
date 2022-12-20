package com.example.tw2ver01.ButtonFunction;

import android.content.Intent;

import com.example.tw2ver01.MainActivity;
import com.example.tw2ver01.page_heartbeat;

public class hebtbtnCommand implements Command {
    private final MainActivity hebtbtn;


    public hebtbtnCommand(MainActivity b) {
        hebtbtn = b;
    }

    @Override
    public void execute() {
        Intent intent = new Intent();
        intent.setClass(hebtbtn, page_heartbeat.class);
        hebtbtn.startActivity(intent);
    }
}

