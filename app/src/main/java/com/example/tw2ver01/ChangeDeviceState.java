package com.example.tw2ver01;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeDeviceState extends AsyncTask<Void, Void, String> {
    final OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(Void... voids) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("deviceCode", Device.getDeviceCode());
            jsonObject.put("state", Device.isState());//Working true or false
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
        Request request = new Request.Builder().url("http://20.194.172.51:80/api/Device/UpdateState").method("POST", body).build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("response info:\n" + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
