package com.example.tw2ver01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import com.google.firebase.database.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Http2Reader;

public class page_heartbeat extends AppCompatActivity {
    private String value;
    private Handler handler;
    private TextView heartoutcome;

    private void ini(){
            var();
    }

    private void var(){
        value=("");
        Handler handler = null;
        heartoutcome = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_heartbeat);
        heartoutcome = findViewById(R.id.heartoutcome);
        handler = new Handler();



        new Thread(new Runnable() {
            @Override

            public void run() {

                while (true) {
                    // write code

                    class heartvalueget extends AsyncTask<Void, Void,String> {
                        OkHttpClient client = new OkHttpClient();

                        @Override
                        protected String doInBackground(Void... voids) {
                            Request request = new Request.Builder()
                                    .url("https://c05b-2001-b011-b800-d21e-c0d0-aa95-1cdf-229d.ngrok.io/api/HeartBeat/now/1")
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
                            if (result != null){
                                heartoutcome.setText(result);
                            }
                        }
                    }

                    new heartvalueget().execute();

                    try { Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
