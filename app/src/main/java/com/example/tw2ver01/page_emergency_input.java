package com.example.tw2ver01;


import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
//import okhttp3.MediaType;

public class page_emergency_input extends AppCompatActivity {
    public Button btnConfirm, btnBackmain;
    public static final String EXTRA_MESSAGE = "com.example.tw2ver01.MESSAGE";
    public static final String EXTRA_MESSAGE1 = "com.example.tw2ver01.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.tw2ver01.MESSAGE";
    private String http="https://c05b-2001-b011-b800-d21e-c0d0-aa95-1cdf-229d.ngrok.io";
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_page_emergency_input);
        btnConfirm = findViewById(R.id.button);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                Bundle bundle = new Bundle();
                EditText bname = findViewById(R.id.bname);
                EditText baddress = findViewById(R.id.baddress);
                EditText bdeviceCode = findViewById(R.id.bdeviceCode);
                String name1 = bname.getText().toString();
                String call1 = baddress.getText().toString();
                String phone1 = bdeviceCode.getText().toString();

                bundle.putString("username", name1);
                bundle.putString("address", call1);
                bundle.putString("bdeviceCode", phone1);

                try {
                    jsonObject.put("deviceCode", bdeviceCode.getText().toString());
                    jsonObject.put("username", bname.getText().toString());
                    jsonObject.put("address", baddress.getText().toString());
                    System.out.println(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
                Request request = new Request.Builder()
                        .url(http+"/api/account/finduser/")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // 連線成功
                        String result = response.body().string();
                        Log.d("OkHttp result", result);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(request);
                    }
                });


                Intent intent = new Intent(page_emergency_input.this, page_emergency_outcome.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
