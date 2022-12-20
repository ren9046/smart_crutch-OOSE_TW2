package com.example.tw2ver01.Language;

import java.util.Locale;

public class Language {
    private final String country;
    private final String lang;
    private Strategy strategy;

    public Language(String s) {
        this.lang = s.split("_")[0];
        this.country = s.split("_")[1];
    }

    public void setStrategy(Strategy s) {
        this.strategy = s;
    }

    public Locale executeStrategy() {
        return strategy.LanguageSelect(lang, country);
    }
}
