package com.example.tw2ver01.Language;

import java.util.Locale;

public class SelectChinese implements Strategy {
    static Locale myLocale;

    @Override
    public Locale LanguageSelect(String lang, String country) {
        if (lang.equals("zh")) {
            if (country.equals("TW")) {
                myLocale = new Locale("zh", "TW");
            } else if (country.equals("CN")) {
                myLocale = new Locale("zh", "CN");
            }
            System.out.println(myLocale);
            return myLocale;
        } else {
            return null;
        }
    }
}
