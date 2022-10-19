package com.example.tw2ver01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class page_emergency_outcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView textview;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_emergency_outcome);//取的intent中的bundle物件
        Bundle bundle0311 =this.getIntent().getExtras();
        String name1 = bundle0311.getString("name");
        String call1 = bundle0311.getString("call");
        String phone1 = bundle0311.getString("phone");
        //顯示發送的字串
        TextView textView = findViewById(R.id.textView);
        textView.setText("緊急聯絡人姓名:" + name1 + "\n稱謂：" + call1 + "\n電話：" + phone1 );

    }

    public void goback(View v) {
        finish();
    }

}