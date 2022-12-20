package com.example.tw2ver01.RegisterVerification;

import java.util.regex.Pattern;

public class passwordStringExpression implements Expression {
    @Override
    public boolean interpret(String context) {
        String regex = "^(?=.*\\d)(?=.*[A-za-z]).{5,10}$";
        Pattern p = Pattern.compile(regex);
        return (p.matcher(context).find());
    }
}
