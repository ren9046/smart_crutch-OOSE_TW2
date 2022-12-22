package com.example.tw2ver01;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.tw2ver01.Alert.MapAlert;
import com.example.tw2ver01.databinding.ActivityPageMaps1Binding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class page_maps1 extends FragmentActivity implements OnMapReadyCallback {
    private final String http = "http://20.194.172.51";
    OkHttpClient client = new OkHttpClient();
    private GoogleMap mMap;
    private ActivityPageMaps1Binding binding;
    private Double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPageMaps1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //template start
        MapAlert mapAlert = new MapAlert(this);
        mapAlert.AlertDialog();
        //template end
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        longitude = null;
        latitude = null;
        mMap = googleMap;
        class getLocationTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... Void) {
                Request request = new Request.Builder()
                        .url(http + "/api/Gps/now/" + Device.getDeviceCode())
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    if (response.code() == 200) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        longitude = Double.parseDouble(jsonObject.getString("longitude"));
                        latitude = Double.parseDouble(jsonObject.getString("latitude"));
                    }
                    System.out.println(latitude);
                    System.out.println(longitude);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                LatLng location = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(location).title("I here."));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                mMap.setMinZoomPreference(15.0f);
            }
        }
        new getLocationTask().execute();
    }
}
