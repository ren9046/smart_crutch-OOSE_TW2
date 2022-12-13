package com.example.tw2ver01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class page_Liveimage extends AppCompatActivity {
    public Button btnimg;
    OkHttpClient client = new OkHttpClient();
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_liveimage);
        imageView = findViewById(R.id.imageView);
        btnimg = findViewById(R.id.button2);
        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class img extends AsyncTask<Void, Void, Bitmap> {

                    @Override
                    protected Bitmap doInBackground(Void... voids) {
                        Request request = new Request.Builder()
                                .url("http://20.194.172.51/api/Image/now/1")
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (response.code() == 200) {
                                String result = response.body().string();
                                JSONObject jsonObject = new JSONObject(result);
                                String value = jsonObject.getString("imageData");
                                byte[] encodeByte = Base64.decode(value, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                System.out.println(value);
                                return bitmap;
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(Bitmap result) {
                        if (result != null) {
                            imageView.setImageBitmap(result);
                        }
                    }
                }
                new img().execute();
            }
        });
    }
}
