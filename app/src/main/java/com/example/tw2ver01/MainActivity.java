package com.example.tw2ver01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tw2ver01.ButtonFunction.Command;
import com.example.tw2ver01.ButtonFunction.btngpsCommand;
import com.example.tw2ver01.ButtonFunction.hebtbtnCommand;
import com.example.tw2ver01.ButtonFunction.liveCommand;
import com.example.tw2ver01.State.HandLeavingAlert_State;
import com.example.tw2ver01.State.WorkingState;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    protected static final int MSG_WHAT = 0;
    public static HandLeavingAlert_State state = new WorkingState();
    private static TextView timeleft;
    private static TextView countdown;
    private static Timer timer;
    private static int time = 60;
    private static boolean isStop = false;
    private static final Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            countdown.setText(time + "sec");
            switch (msg.what) {
                case MSG_WHAT:
                    if (time > 0) {
                        time--;
                    } else {
                        System.out.println("time out");
                        if (timer != null) {
                            countdown.setText("Time out!");
                            Device.setState(isStop);
                            new ChangeDeviceState().execute();
                            timer.cancel();
                            time = 60;
                            timer = null;
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };
    private static Double HBvalue;
    LoginResponse loginResponse;
    Button live, btngps, hebtbtn, stopbtn;

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
                isStop = false;
            } else if (state.getStateType() == "Working") {
                countdown.setText("Heartbeat Detecting...");
            }
        } else { //沒有偵測到心跳
            if (state.getStateType() == "Working") {
                if (!isStop) { //還沒有按下Stop
                    state.switchState();//change to Warning state
                    timer = new Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            mHandler.sendEmptyMessage(MSG_WHAT);
                        }
                    }, 0, 1000);
                }
            }
        }
    }

    public static void setTimeleft(String t) {
        timeleft.setText(t);
    }

    public void stop(View view) {
        if (state.getStateType() == "Warning" && !isStop) {
            if (timer == null) {
                countdown.setText("Send Message to family");
                System.out.println("send message");
            } else {
                timer.cancel();
                timer = null;
                time = 60;
                state.switchState();//change to working state
                countdown.setText("Stop Using crutch");
                System.out.println("Stop");
            }
            isStop = true;
            stopbtn.setText("Restart");
        } else if (state.getStateType() == "Working" && isStop) {
            isStop = false;
            stopbtn.setText("Stop");
            System.out.println("Restart");
            countdown.setText("Loading...");
        } else if (state.getStateType() == "Working" && !isStop) {
            System.out.println("Do nothing");
        } else if (state.getStateType() == "Warning" && isStop) {
            state.switchState(); //change to working state
            stopbtn.setText("Stop");
            isStop = false;
            countdown.setText("Loading...");
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
}

