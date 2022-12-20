package com.example.tw2ver01.Language;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;


public class BaseActivity extends AppCompatActivity {
    protected static final String TAG = "BaseAc";
    BroadcastReceiver myBroadcastReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("廣播", "----接收到的是----" + intent.getStringExtra("msg"));
            if (intent.getStringExtra("msg").equals("EVENT_REFRESH_LANGUAGE")) {
                changeAppLanguage();
                recreate();//刷新界面

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ACTION);
        registerReceiver(myBroadcastReceive, intentFilter);
        changeAppLanguage();
    }

    public void changeAppLanguage() {
        String sta = Store.getLanguageLocal(this);
        System.out.println(sta);
        if (sta != null && !"".equals(sta)) {
            Language language = new Language(sta);
            Strategy selectEnglish = new SelectEnglish();
            Strategy selectChinese = new SelectChinese();
            Strategy selectKorean = new SelectKorean();
            switch (sta) {
                case "zh_TW":
                case "zh_CN":
                    language.setStrategy(selectChinese);
                    break;
                case "en_US":
                    language.setStrategy(selectEnglish);
                    break;
                case "ko_KR":
                    language.setStrategy(selectKorean);
                    break;
            }
            Locale myLocale = language.executeStrategy();
            System.out.println(myLocale);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceive);
        EventBus.getDefault().unregister(this);
    }

}