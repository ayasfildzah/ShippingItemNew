package com.mkp.shippingitem.Attadance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mkp.shippingitem.R;
import com.mkp.shippingitem.model.DataInsert;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddMarker extends AppCompatActivity {
    private static final String TAG = AddMarker.class.getName();

    EditText editNote;
    private String enteredID, enteredStatus,enteredCreator,enteredAlamat,enteredPhone,enteredNote;
    Spinner spinnerStatus;
    TextView LgUser,editalamat,editID,editPhone;
    Button btnScan;
    private String userLg,IDLg,phone;
    private SharedPreferences mPreferences;
    LocationManager locationManager;
    LocationListener locationListener;
    ImageView btn_logout;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);

        LgUser = findViewById(R.id.LgUser);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        editalamat = findViewById(R.id.editTextAlamat);
        editID = findViewById(R.id.UserID);
        editPhone = findViewById(R.id.phone);
        editNote = findViewById(R.id.editNote);


        Button AddButton = findViewById(R.id.btnsubmit);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean isGPS_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPS_enabled) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    try {
                        Geocoder geocoder = new Geocoder(AddMarker.this, Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

                        editalamat.setText(addressList.get(0).getAddressLine(0));


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

        //// insert button
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Toast.makeText(AddMarker.this, " Berhasil",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddMarker.this, ShowAbsen.class);
                startActivity(intent);*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddMarker.this);
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("Confirm Absen..!!!");
                // Icon Of Alert Dialog
                alertDialogBuilder.setIcon(R.drawable.ic_out);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Anda Yakin untuk Ckeck In ?");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        new AddMarker.insertShipping().execute();
                        Intent intent = new Intent(AddMarker.this, ShowAbsen.class);
                        startActivity(intent);
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddMarker.this, "You clicked over No", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } });


            mPreferences =getSharedPreferences("CurrentUser",MODE_PRIVATE);

            //get SharedPreferences dari Login
            userLg =mPreferences.getString("name","");
            IDLg =mPreferences.getString("id","");
            phone =mPreferences.getString("phone","");

        editPhone.setText(phone);
        editID.setText(IDLg);
        LgUser.setText(userLg);


    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                editalamat.setText("Getting Location");
                //  editAlamat.setText("Getting Location");

            } else {
                editalamat.setText("Access not granted");
                //  editAlamat.setText("Access not granted");

            }
        }
    }

    private class insertShipping extends AsyncTask<String, Void, DataInsert> {
        private ProgressDialog pgDialog = new ProgressDialog(AddMarker.this);

        @Override
        protected void onPreExecute() {

            pgDialog.setMessage("\tMohon Tunggu");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected DataInsert doInBackground(String... strings) {

            enteredCreator = LgUser.getText().toString();
            enteredStatus = spinnerStatus.getSelectedItem().toString();
            enteredAlamat = editalamat.getText().toString();
            enteredID = editID.getText().toString();
            enteredPhone = editPhone.getText().toString();
            enteredNote = editNote.getText().toString();

            try {
                return AppConstant.getInsertMarker().insShipping(AddMarker.this,enteredCreator,enteredStatus, enteredAlamat,enteredID,enteredPhone,enteredNote);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(DataInsert result) {
            super.onPostExecute(result);
            pgDialog.dismiss();
            try {

                LogHelper.verbose(TAG,"RESULT SHIPPING :"+result);
            } catch (NullPointerException e) {
                e.printStackTrace();
                LogHelper.verbose(TAG, e.getMessage());
            }
        }
    }
}
