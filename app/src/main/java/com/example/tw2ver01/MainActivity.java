package com.example.tw2ver01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tw2ver01.ButtonFunction.Command;
import com.example.tw2ver01.ButtonFunction.btngpsCommand;
import com.example.tw2ver01.ButtonFunction.hebtbtnCommand;
import com.example.tw2ver01.ButtonFunction.liveCommand;
import com.example.tw2ver01.State.HandLeavingAlert_State;
import com.example.tw2ver01.State.WorkingState;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity {
    LoginResponse loginResponse;
    Button live, btngps, hebtbtn, stopbtn;
    public static HandLeavingAlert_State state = new WorkingState();
    public static String token = null;
    private static TextView timeleft;
    private static TextView countdown;
    private static Timer timer;
    private static int time = 60;
    private static int isStop = 0;
    private static Double HBvalue;
    protected static final int MSG_WHAT = 0;
    private static final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            countdown.setText(time + "sec");
            switch (msg.what) {
                case MSG_WHAT:
                    if (time > 0) {
                        time--;
                    } else {
                        System.out.println("time out");
                        //Toast.makeText(MainActivity.this, "倒计时完成", Toast.LENGTH_SHORT).show();
                        if (timer != null) {
                            OkHttpClient client = new OkHttpClient();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("deviceCode", Device.getDeviceCode());
                                jsonObject.put("state", 1);//Working true or false
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                            RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
                            Request request = new Request.Builder().url("http://20.194.172.51:80/api/Device/UpdateState").method("POST", body).build();
                            try {
                                client.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            countdown.setText("Time out!");
                            timer.cancel();
                            timer = null;
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };

    public static void setHBvalue(Double h) {
        HBvalue = h;
        System.out.println(HBvalue);
    }

    public static void isDectect() {
        System.out.println("detecting");
        if (HBvalue >= 10.0) { //有偵測到心跳
            if (state.getStateType() == "Warning") {
                state.switchState();//change to Working state
                timer.cancel();
                isStop = 0;
            } else if (state.getStateType() == "Working") {
                countdown.setText("Heartbeat Detecting...");
            }
        } else { //沒有偵測到心跳
            if (state.getStateType() == "Working") {
                if (isStop != 1) { //還沒有按下Stop
                    state.switchState();//change to Warning state
                    //if (timer == null) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mHandler.sendEmptyMessage(MSG_WHAT);
                        }
                    }, 0, 1000);
                    //}
                }
            }
        }
    }

    public static void setTimeleft(String t) {
        timeleft.setText(t);
    }

    public void stop(View view) {
        if (state.getStateType() == "Warning" && isStop == 0) {
            isStop = 1;
            timer.cancel();
            timer = null;
            time = 60;
            state.switchState();//change to working state
            countdown.setText("Stop Using crutch");
            System.out.println("Stop");
            stopbtn.setText("Restart");
        } else if (state.getStateType() == "Working" && isStop == 1) {
            isStop = 0;
            stopbtn.setText("Stop");
            System.out.println("Restart");
            countdown.setText("Loading...");
        } else if (state.getStateType() == "Working" && isStop == 0) {
            System.out.println("Do nothing");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        live = findViewById(R.id.live);
        btngps = findViewById(R.id.btngps);
        hebtbtn = findViewById(R.id.hebtbtn);
        timeleft = findViewById(R.id.timeleft);
        timeleft.setText("Working");
        countdown = findViewById(R.id.countdown);
        countdown.setText("Loading...");
        stopbtn = findViewById(R.id.stopbtn);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    new GetHeartBeatValue().execute();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Command livebtn = new liveCommand(MainActivity.this);
                livebtn.execute();
            }
        });

        btngps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Command btngps = new btngpsCommand(MainActivity.this);
                btngps.execute();
            }
        });


        hebtbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Command hebtbtn = new hebtbtnCommand(MainActivity.this);
                hebtbtn.execute();
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

