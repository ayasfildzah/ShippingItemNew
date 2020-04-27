package com.mkp.shippingitem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {
    TextView tvSplash;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        tvSplash = (TextView) findViewById(R.id.tvSplash);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              //  if (mPreferences.getString("name",check)!=null)
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 2000L);
    }
}