package com.mkp.shippingitem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mkp.shippingitem.model.ResponLoginModel;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private final String serverUrl = "http://alita.massindo.com/ShippingPresenterApi/v1/users/sign_in";
    protected EditText usermail;
    protected String enteredUsermail;
    private String enteredPassword;
    private EditText password;
    private SharedPreferences mPreferences;
    public static final String session_status = "session_status";
    Boolean session = false;
    private SharedPreferences.Editor loginPrefsEditor;
    private CheckBox checkBoxRememberMe;
    private Boolean saveLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usermail = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        Button loginButton = findViewById(R.id.loginButton);
        checkBoxRememberMe = findViewById(R.id.ch_rememberme);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        session = mPreferences.getBoolean(session_status, false);
        loginPrefsEditor = mPreferences.edit();

        saveLogin = mPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            usermail.setText(mPreferences.getString("email", ""));
            password.setText(mPreferences.getString("password", ""));
            checkBoxRememberMe.setChecked(true);
        }
       /* if (SharedPrefManager.getInstans(this).isLogin()) {
            finish();
            startActivity(new Intent(MainActivity.this, UserActivity.class));
            return;*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usermail.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                } else if (usermail.getText().toString().length() <= 1 || password.getText().toString().length() <= 1) {
                    Toast.makeText(MainActivity.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
                    return;
                } else if (usermail.getText().toString().length() == 0) {
                    usermail.setError("Tidak boleh Kosong!");
                } else {
                    new sendLog().execute();
                                    }
            }
        });
          }

    private class sendLog extends AsyncTask<String, Void, ResponLoginModel> {
        private ProgressDialog pgDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            pgDialog.setTitle("\tRequest Login");
            pgDialog.setMessage("\tSedang cek data");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }


        @SuppressLint("WrongThread")
        protected ResponLoginModel doInBackground(String... strings) {
            enteredUsermail = usermail.getText().toString();
            enteredPassword = password.getText().toString();
            try {
                return AppConstant.getLoginApi().LoginA(MainActivity.this, enteredUsermail, enteredPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponLoginModel responLoginModel) {
            super.onPostExecute(responLoginModel);
            pgDialog.dismiss();
            try {

                //Jika Kondisi sukses
                if (responLoginModel.getId() != null) {
                    Toast.makeText(MainActivity.this, "" + "Selamat Datang : " + responLoginModel.getName(), Toast.LENGTH_SHORT).show();
                    LogHelper.verbose(TAG, "resultSuksesLogin: " + responLoginModel);
                }
                    //Set data SharedPreferences
                    if (checkBoxRememberMe.isChecked()) {
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putBoolean("saveLogin", true);
                        editor.putString("id", responLoginModel.getId());
                        editor.putString("name", responLoginModel.getName());
                        editor.putString("phone", responLoginModel.getPhone());
                        editor.putString("email", responLoginModel.getEmail());
                        editor.commit();
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this,FragmentBottom.class);
                        startActivity(intent);

                    finish();

                }

                //Jika Kondisi Error
                if (responLoginModel.getError().equals("invalid email and password combination")) {
                    Toast.makeText(MainActivity.this, "" + responLoginModel.getError(), Toast.LENGTH_SHORT).show();
                    LogHelper.verbose(TAG, "resultError: " + responLoginModel.getError());
                   SharedPreferences.Editor editor = mPreferences.edit();
                    editor.remove("id");
                    editor.remove("name");
                    editor.remove("phone");
                    editor.clear();
                    editor.commit();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                LogHelper.verbose(TAG, e.getMessage());
            }
        }
    }
}