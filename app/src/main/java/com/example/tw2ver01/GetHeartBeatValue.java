package com.example.tw2ver01;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetHeartBeatValue extends AsyncTask<Void, Void, String> {
    private static String value;
    OkHttpClient client = new OkHttpClient();

    @Override
    public String doInBackground(Void... voids) {
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
            Double hbvalue = Math.round(Float.parseFloat(result) * 100.0) / 100.0;
            MainActivity.setHBvalue(hbvalue);
            MainActivity.isDectect();
        }
    }
}
