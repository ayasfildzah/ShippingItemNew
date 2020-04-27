package com.mkp.shippingitem.ShippingAdd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mkp.shippingitem.FragmentBottom;
import com.mkp.shippingitem.MainActivity;
import com.mkp.shippingitem.R;
import com.mkp.shippingitem.ShowPesan;
import com.mkp.shippingitem.model.ResponseShippingInsert;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddSelesai extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private final String serverUrl = "https://alita.massindo.com/api/v1/shipping_items";

    private String enteredDelivery, enteredStatus, enteredNote,enteredCreator,enteredPhone,enteredAlamat;
    private EditText editDeliv, editNote,kode_qr;

    Spinner spinnerStatus;
    TextView LgUser,editalamat,editPhone;
    Button btnScan;
    private String userLg,phoneLg;
    private SharedPreferences mPreferences;
    LocationManager locationManager;
    LocationListener locationListener;
    ImageView btn_logout;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_selesai);

        editDeliv = findViewById(R.id.editTextNumber);

        editNote = findViewById(R.id.editTextNote);
        LgUser = findViewById(R.id.LgUser);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        editalamat = findViewById(R.id.editTextAlamat);
        editPhone = findViewById(R.id.Phone);


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
                        Geocoder geocoder = new Geocoder(AddSelesai.this, Locale.getDefault());
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

                if (editDeliv.getText().toString().equals("")) {
                    Toast.makeText(AddSelesai.this, "Surat Jalan Wajib Diisi", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    new AddSelesai.insertShipping().execute();
                    Intent intent = new Intent(AddSelesai.this, ShowPesan.class);
                    startActivity(intent);
                }
            }
        });
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(AddSelesai.this, MainActivity.class);
                finish();
                startActivity(intent);


            }
        });

        kode_qr = findViewById(R.id.editTextNumber);
        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCameraId(0);
        btnScan = findViewById(R.id.fab);
        btnScan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                intentIntegrator.initiateScan();
            }

        });


        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //get SharedPreferences dari Login
        userLg = mPreferences.getString("name","");
        phoneLg = mPreferences.getString("phone","");
        LgUser.setText(userLg);
        editPhone.setText(phoneLg);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data.
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    editDeliv.setText(obj.getString("textPersonName"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    String contents = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(getBaseContext(), "Hasil :" + contents, Toast.LENGTH_SHORT).show();
                    editDeliv.setText(contents);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void about(View view) {
        Intent intent = new Intent(AddSelesai.this, FragmentBottom.class);
        startActivity(intent);
    }

    public void home(View view) {
        Intent intent = new Intent(AddSelesai.this,FragmentBottom.class);
        startActivity(intent);
    }
    private class insertShipping extends AsyncTask<String, Void, ResponseShippingInsert> {
        private ProgressDialog pgDialog = new ProgressDialog(AddSelesai.this);

        @Override
        protected void onPreExecute() {

            pgDialog.setMessage("\tMohon Tunggu");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected ResponseShippingInsert doInBackground(String... strings) {
            enteredDelivery = editDeliv.getText().toString();
            if (editDeliv.getText().toString().length() == 0) {
                editDeliv.setError("Surat Jalan diperlukan (Tidak boleh Kosong)!"); }
            enteredCreator = LgUser.getText().toString();
            enteredStatus = spinnerStatus.getSelectedItem().toString();
            enteredNote = editNote.getText().toString();
            enteredAlamat = editalamat.getText().toString();
            enteredPhone = editPhone.getText().toString();

            try {
                return AppConstant.getShippingInsertPresenterApi().insShipping(AddSelesai.this, enteredDelivery,enteredCreator,enteredStatus, enteredNote, enteredAlamat,enteredPhone);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(ResponseShippingInsert result) {
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
