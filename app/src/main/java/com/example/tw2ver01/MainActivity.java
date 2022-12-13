package com.example.tw2ver01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    LoginResponse loginResponse;
    Button live, btngps, hebtbtn;


    public static String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        live = findViewById(R.id.live);
        btngps = findViewById(R.id.btngps);
        hebtbtn = findViewById(R.id.hebtbtn);

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, page_Liveimage.class));
            }
        });

        btngps.setOnClickListener(new View.OnClickListener() {
            private String data;
            private Double longitude;
            private Double latitude;
            Bundle bundle = new Bundle();


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, page_maps1.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        hebtbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, page_heartbeat.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            Log.e("Tag", "=====>" + loginResponse.getEmail());
        }
    }


    final Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "登入失敗", Toast.LENGTH_LONG).show();
            }
        }
    };
}

