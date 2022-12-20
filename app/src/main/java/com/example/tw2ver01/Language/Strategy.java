package com.example.tw2ver01.Language;

import java.util.Locale;

public interface Strategy {
    Locale LanguageSelect(String lang, String country);
}
