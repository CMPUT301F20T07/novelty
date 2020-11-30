package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.novelty.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    SearchView searchView;
    Geocoder geo;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        searchView = findViewById(R.id.searchView);

        MapView mMap = (MapView) findViewById(R.id.map);
        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS != errorCode) {
            GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0).show();
        } else {
            mMap.getMapAsync(this);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double lat = 40.73;
        double lng = -73.99;
        LatLng appointLoc = new LatLng(lat, lng);
        // 移动地图到指定经度的位置
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(appointLoc));

        //添加标记到指定经纬度
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker")
                  .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_back)));
        if (map != null) {
            geo = new Geocoder(MapActivity.this, Locale.getDefault());
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    try {
                        map.clear();
                        if (geo == null) {
                            geo = new Geocoder(MapActivity.this, Locale.getDefault());
                        }
                        List<Address> address = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        if (address.size() > 0) {
                            map.addMarker(new MarkerOptions().position(latLng));
                            String add = address.get(0).getAddressLine(0);

                        }
                    } catch (IOException ex) {
                        if (ex != null)
                            Toast.makeText(MapActivity.this, "Error:" + ex.getMessage().toString()
                                      , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    public void send(View view) {

    }
}