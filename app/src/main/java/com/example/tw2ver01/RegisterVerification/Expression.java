package com.example.tw2ver01.RegisterVerification;

//帳號驗證器
//第1種是@前面必須是英文加數字
//地2種是@後面必須是 英文數字.英文數字(.英文數字)
//密碼驗證器
//至少有一個數字
//至少有一個小寫英文字母
//至少有一個大寫英文字母
//字串長度在 6 ~ 30 個字母之間


public interface Expression {
    boolean interpret(String context);
}
