package com.example.tw2ver01.Language;

import java.util.Locale;

public class SelectEnglish implements Strategy {
    static Locale myLocale;

    @Override
    public Locale LanguageSelect(String lang, String country) {
        System.out.println(lang);
        System.out.println(country);
        if (lang.equals("en") && country.equals("US")) {
            myLocale = new Locale("en", "US");
            System.out.println(myLocale);
            return myLocale;
        } else {
            return null;
        }
    }
}
