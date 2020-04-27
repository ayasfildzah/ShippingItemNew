package com.mkp.shippingitem.Attadance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mkp.shippingitem.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.PI;

public class Checkin extends AppCompatActivity {
    TextView lokasiSaya, value;
    EditText latitude, longitude;
    Button hitungJarak,checkin;
    LocationManager locationManager;
    LocationListener locationListener;
    String obj;
    TextView alamat;
    DigitalClock digitalClock1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        ActivityCompat.requestPermissions(Checkin.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        lokasiSaya = (TextView) findViewById(R.id.txt_lokasiSaya);
        value = (TextView) findViewById(R.id.txt_value);
        //  latitude = (EditText) findViewById(R.id.edt_latitude);
        //   longitude = (EditText) findViewById(R.id.edt_longitude);
        checkin = findViewById(R.id.btn_cekin);
        alamat = findViewById(R.id.alamat);
        digitalClock1 = findViewById(R.id.digitalClock);

        digitalClock1.setVisibility(View.VISIBLE);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean isGPS_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPS_enabled) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    try {
                        Geocoder geocoder = new Geocoder(Checkin.this, Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

                        alamat.setText(addressList.get(0).getAddressLine(0));


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                public void onProviderEnabled(String provider) {

                }


                public void onProviderDisabled(String provider) {

                }

            };
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);

        }
       /* hitungJarak = (Button) findViewById(R.id.btn_hitungJarak);
        hitungJarak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation getLocation = new GetLocation(getApplicationContext());
                Location location = getLocation.getLocation();
                if (location != null) {
                    double latitudeSaya = location.getLatitude();
                    double longitudeSaya = location.getLongitude();
                    double latitudeTujuan = -6.344493;
                    double longitudeTujuan = 106.989368;

                    lokasiSaya.setText(latitudeSaya + ", " + longitudeSaya);
                    double jarak = Haversine.getDistance(latitudeTujuan, longitudeTujuan, latitudeSaya, longitudeSaya);
                    value.setText(jarak + " meter");

                }
            }
        });*/


        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation getLocation = new GetLocation(getApplicationContext());
                Location location = getLocation.getLocation();
                double latitudeSaya = location.getLatitude();
                double longitudeSaya = location.getLongitude();
                double latitudeTujuan = -6.344493;
                double longitudeTujuan = 106.989368;

                lokasiSaya.setText(latitudeSaya + ", " + longitudeSaya);
                double jarak = Haversine.getDistance(latitudeTujuan, longitudeTujuan, latitudeSaya, longitudeSaya);
                value.setText(jarak + " meter");
//                double jarak1 = Double.parseDouble(value.getText().toString());
                if (jarak <= 999.555077903167739 ){
                    Intent intent = new Intent(Checkin.this,AddMarker.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Checkin.this,"Jarak masih terlalu jauh",Toast.LENGTH_SHORT).show();
                    // Intent intent = new Intent(MainActivity.this,Dashboard.class);
                    // startActivity(intent);
                }
            }
        });
    }

    public static class Haversine {
        public static Double getDistance(Double latitudeTujuan, Double longitudeTujuan, Double latitudeUser, Double longitudeUser) {
            //VARIABLE
            // Double pi = 3.14159265358979;
            Double lat1 = latitudeTujuan;
            Double lon1 = longitudeTujuan;
            Double lat2 = latitudeUser;
            Double lon2 = longitudeUser;
            Double R = 6371e3;

            Double latRad1 = lat1 * (PI / 180);
            Double latRad2 = lat2 * (PI / 180);
            Double deltaLatRad = (lat2 - lat1) * (PI / 180);
            Double deltaLonRad = (lon2 - lon1) * (PI / 180);

            // RUMUS HAVERSINE
            Double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) + Math.cos(latRad1) * Math.cos(latRad2) * Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Double s = R * c;
            return s;


          /*  Double lat1 = latitudeTujuan;
            Double lon1 = longitudeTujuan;
            Double lat2 = latitudeUser;
            Double lon2 = longitudeUser;
            int R = 6372; // In kilometers
            Double dLat = toRadians(lat2 - lat1);
            Double dLon = toRadians(lon2 - lon1);
            lat1 = toRadians(lat1);
            lat2 = toRadians(lat2);

            Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
           // Double c = 2 * Math.asin(Math.sqrt(a));
            return R * 2 * Math.asin(Math.sqrt(a));
        }

        public static double toRadians(double angle) {
            return Math.PI * angle / 180;
        }*/
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                alamat.setText("Getting Location");
                //  editAlamat.setText("Getting Location");

            } else {
                alamat.setText("Access not granted");
                //  editAlamat.setText("Access not granted");

            }
        }
    }
}