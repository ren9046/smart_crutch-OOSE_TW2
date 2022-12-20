package com.example.tw2ver01.Language;

import java.util.Locale;

public class SelectKorean implements Strategy {
    static Locale myLocale;

    @Override
    public Locale LanguageSelect(String lang, String country) {
        System.out.println("lang:" + lang + ",country:" + country);
        if (lang.equals("ko") && country.equals("KR")) {
            myLocale = new Locale("ko", "KR");
            System.out.println(myLocale);
            return myLocale;
        } else {
            return null;
        }
    }
}
