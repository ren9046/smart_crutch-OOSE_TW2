package com.example.tw2ver01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import com.example.tw2ver01.dao.UserDao;




public class MainActivity extends AppCompatActivity {
    LoginResponse loginResponse;
    Button reg,login,live,btngps,emycbtn,hebtbtn;

    public static final String TAG = FCMService.TAG;
    public static String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        reg = findViewById(R.id.reg);
//        login = findViewById(R.id.login);
        live = findViewById(R.id.live);
        btngps = findViewById(R.id.btngps);
//        emycbtn = findViewById(R.id.emycbtn);
        hebtbtn = findViewById(R.id.hebtbtn);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful())return;
                token = task.getResult();
                Log.d(TAG, "onComplete: "+token);
            }
        });

//        reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,page_register.class));
//            }
//        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new  Intent(MainActivity.this,page_login.class);
//                startActivity(intent);
//            }
//        });

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,page_Liveimage.class));
            }
        });

        btngps.setOnClickListener(new View.OnClickListener() {
            private String data;
            private Double longitude;
            private Double latitude;
            Bundle bundle = new Bundle();


            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(MainActivity.this,page_maps1.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

//        emycbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,page_emergency_input.class));
//            }
//        });

        hebtbtn.setOnClickListener(new View.OnClickListener() {
//            private String heartbeat;
//            Bundle bundle = new Bundle();
//            public void find() {
//                OkHttpClient client = new OkHttpClient().newBuilder()
//                        .build();
//                Bundle bundle = new Bundle();
//                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
//                //        RequestBody body = RequestBody.create( "",mediaType);
//                Request request = new Request.Builder()
//                        .url("https://20a8-2001-b011-b800-d98b-159a-1c02-67ee-c6bd.ngrok.io/api/HeartBeat/now/1")
////                .method("GET", body)
//                        .addHeader("Content-Type", "application/json")
//                        .build();
//                Call call = client.newCall(request);
//
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        // 連線成功
//                        String result = response.body().string();
////                       heartoutcome.setText(result);
//                        System.out.println(result);
//                        JSONObject jsonObject = null;
//                        try {
////                  建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值 heartBeatValue
//                            jsonObject = new JSONObject(result);
//                            heartbeat = jsonObject.getString("heartBeatValue");
//                            System.out.println(heartbeat);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println(request);
//                    }
//                });
//            }

            @Override
            public void onClick(View view) {
//                  find();
//                bundle.putString("heartbeat",heartbeat);
                Intent intent = new  Intent(MainActivity.this,page_heartbeat.class);
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        if(intent.getExtras() != null){
            loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            Log.e("Tag","=====>"+loginResponse.getEmail());
        }
    }



    final Handler hand1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1)
            {
                Toast.makeText(getApplicationContext(),"登入成功",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"登入失敗",Toast.LENGTH_LONG).show();
            }
        }
    };
}

