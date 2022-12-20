package com.example.tw2ver01.RegisterVerification;

import java.util.regex.Pattern;

public class emailRearStringExpression implements Expression {
    @Override
    public boolean interpret(String context) {
        String regex = "^.*@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
        Pattern p = Pattern.compile(regex);
        return (p.matcher(context).find());
    }
}
