package com.example.tw2ver01.Language;

import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class LanguageUtils {

    public static String getCurrentLanguage() {
        try {
            Locale locale = getSysPreferredLocale();
            String language = locale.getLanguage();
            if (language.equals("en")) {
                return "en_US";
            } else if (language.equals("zh")) {
                if (locale.getCountry().equals("TW") || locale.getCountry().equals("HK") || locale.toString().contains("#Hant")) {
                    return "zh_TW";
                } else {
                    return "zh_CN";
                }

            } else if (language.equals("ko")) {
                return "ko_KR";

            } else {
                return "en_US";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "en_US";
        }


    }

    public static Locale getSysPreferredLocale() {
        Locale locale;
        //7.0以下直接獲取系统默認語言
        if (Build.VERSION.SDK_INT < 24) {
            locale = Locale.getDefault();
        } else {
            locale = LocaleList.getDefault().get(0);
        }
        return locale;
    }

}
