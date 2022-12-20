package com.example.tw2ver01.ButtonFunction;

import android.content.Intent;

import com.example.tw2ver01.MainActivity;
import com.example.tw2ver01.page_Liveimage;

public class liveCommand implements Command {
    private final MainActivity live;

    public liveCommand(MainActivity b) {
        live = b;
    }

    @Override
    public void execute() {
        Intent intent = new Intent();
        intent.setClass(live, page_Liveimage.class);
        live.startActivity(intent);
    }

}

