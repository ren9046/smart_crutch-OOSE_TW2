package com.example.tw2ver01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tw2ver01.Alert.LoginAlert;
import com.example.tw2ver01.Language.BaseActivity;
import com.example.tw2ver01.Language.Config;
import com.example.tw2ver01.Language.LanguageUtils;
import com.example.tw2ver01.Language.Store;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class page_login extends BaseActivity {
    Button btnlogin;
    EditText inputemail, inputpwd;
    TextView createAcc;
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    private Handler handler = null;
    //private boolean info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_login);
        handler = new Handler();
        btnlogin = findViewById(R.id.btnlogin);
        inputemail = findViewById(R.id.inputemail);
        inputpwd = findViewById(R.id.inputpwd);
        createAcc = findViewById(R.id.createAcc);
        //Bundle bundle = this.getIntent().getExtras();
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(page_login.this, page_register.class));

            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            private String pwd;
            private String email;
            private String value;

            @Override
            public void onClick(View v) {

                class login extends AsyncTask<Void, Void, String> {
                    final OkHttpClient client = new OkHttpClient();

                    @Override
                    protected String doInBackground(Void... voids) {
                        String result = null;
                        JSONObject jsonObject = new JSONObject();
                        EditText inputemail = findViewById(R.id.inputemail);
                        EditText inputpwd = findViewById(R.id.inputpwd);
                        try {

                            jsonObject.put("email", inputemail.getText().toString());
                            jsonObject.put("password", inputpwd.getText().toString());
                            System.out.println(jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
                        Request request = new Request.Builder()
                                .url("http://20.194.172.51/api/EmergencyContact/login")
                                .method("POST", body)
                                .build();


                        try (Response response = client.newCall(request).execute()) {
                            if (response.code() == 200) {

                                result = response.body().string();
                                jsonObject = new JSONObject(result);
                                JSONObject data = jsonObject.getJSONObject("deviceCode");
                                long devicecode = data.getLong("deviceCode");
                                boolean bind = data.getBoolean("bind");
                                Device.setDeviceCode(devicecode);
//                                String email=
                                if (bind == false) {
                                    OkHttpClient client = new OkHttpClient();
                                    jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("bind", true);
                                        jsonObject.put("deviceCode", devicecode);
                                        System.out.println(jsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    MediaType a = MediaType.parse("application/json; charset=utf-8");
                                    RequestBody b = RequestBody.create(jsonObject.toString(), a);
                                    Request re = new Request.Builder()
                                            .url("http://20.194.172.51/api/Device/create/" + Device.getDeviceCode())
                                            .method("PATCH", b)
                                            .build();
                                    try (Response rp = client.newCall(re).execute()) {
                                        if (rp.code() == 200) {

                                            String result1 = rp.body().string();
                                            jsonObject = new JSONObject(result1);
                                            System.out.println(result1);
                                        }
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                System.out.println("fail");
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                        return result;
                    }

                    protected void onPostExecute(String result) {
                        System.out.println(result);
                        if (result != null) {
                            LoginAlert loginAlert = new LoginAlert(page_login.this);
                            loginAlert.AlertDialog();
                            if (handler != null) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(page_login.this, MainActivity.class));
                                    }
                                }, 1500);
                            }
                        } else {
                            String message = "密碼錯誤";
                            Toast.makeText(page_login.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                new login().execute();

            }
        });
        final String[] cities = {getString(R.string.lan_en_US), getString(R.string.lan_ko_rKR), getString(R.string.lan_zh_rTW), getString(R.string.lan_zh_CN), getString(R.string.Follow_the_system)};
        final String[] locals = {"en_US", "ko_KR", "zh_TW", "zh_CN", "111"};
        Button btnlng = findViewById(R.id.btnlng);
        btnlng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(page_login.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle(R.string.select_language);
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = null;
                        if (which == 4) {
                            language = LanguageUtils.getCurrentLanguage();
                            Log.e(TAG, "onClick: language   --- >" + language);
                        } else {
                            language = locals[which];
                        }
                        Store.setLanguageLocal(page_login.this, language);
                        Intent intent = new Intent(Config.ACTION);
                        intent.putExtra("msg", "EVENT_REFRESH_LANGUAGE");
                        sendBroadcast(intent);

                    }
                });
                builder.show();
            }
        });
    }

}