package com.example.tw2ver01.RegisterVerification;

import java.util.regex.Pattern;

//w{1,63}的意思等於[a-zA-Z0-9_]{1,63}，就是允許大小寫字母，數字和底線，至少1到63個字。
public class emailFrontStringExpression implements Expression {
    @Override
    public boolean interpret(String context) {
        String regex = "^\\w{1,63}@.*?";
        Pattern p = Pattern.compile(regex);
        return (p.matcher(context).find());
    }
}
