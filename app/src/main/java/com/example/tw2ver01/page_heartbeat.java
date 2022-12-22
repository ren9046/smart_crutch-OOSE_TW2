package com.example.tw2ver01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class page_heartbeat extends AppCompatActivity {
    private String value;
    private Handler handler;
    private TextView heartoutcome;
    private GetHeartBeatValue getHeartBeat;

    private void var() {
        value = ("");
        heartoutcome = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_heartbeat);
        heartoutcome = findViewById(R.id.heartoutcome);
        handler = new Handler();
        getHeartBeat = new GetHeartBeatValue();
        View btnimg = findViewById(R.id.button4);
        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(page_heartbeat.this, page_chart.class));
            }
        });

        new Thread(new Runnable() {
            @Override

            public void run() {

                while (true) {
                    // write code


                    class heartvalueget extends AsyncTask<Void, Void, String> {
                        final OkHttpClient client = new OkHttpClient();

                        @Override
                        protected String doInBackground(Void... voids) {
                            Request request = new Request.Builder()
                                    .url("http://20.194.172.51/api/HeartBeat/now/" + Device.getDeviceCode())
                                    .build();

                            try (Response response = client.newCall(request).execute()) {
                                if (response.code() == 200) {
                                    String result = response.body().string();
                                    JSONObject jsonObject = new JSONObject(result);
                                    value = jsonObject.getString("heartBeatValue");
                                    return value;
                                }
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        protected void onPostExecute(String result) {
                            if (result != null) {
                                Double value = Math.round(Float.parseFloat(result) * 100.0) / 100.0;
                                heartoutcome.setText(value.toString());
                            }
                        }
                    }
                    new heartvalueget().execute();
                    try {
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
