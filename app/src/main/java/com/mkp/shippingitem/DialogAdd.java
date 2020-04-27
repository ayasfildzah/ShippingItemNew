package com.mkp.shippingitem;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mkp.shippingitem.ShippingAdd.AddJalan;
import com.mkp.shippingitem.ShippingAdd.AddPending;
import com.mkp.shippingitem.ShippingAdd.AddSelesai;
import com.mkp.shippingitem.ShippingAdd.AddTiba;

import java.util.HashMap;

public class DialogAdd extends AppCompatActivity {

    ImageView fab,btn_logout;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_no, txt_rate ;
    TextView txt_hasil,LgUser;
    public String userLg;
    String rate,no;
    SharedPreferences mPreferences;
    private SliderLayout sliderLayout;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        LgUser = findViewById(R.id.LgUser);
        // Load image dari URL
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Massindo Group", "https://www.canva.com/design/DADmSHbNKbY/0w5pcPuVrX9xkqzNpM0wgA/view#1");
        url_maps.put("The Best Spring Air", "https://www.canva.com/design/DADmSHbNKbY/0w5pcPuVrX9xkqzNpM0wgA/view#2");
        url_maps.put("Spesial Award", "https://www.canva.com/design/DADmSHbNKbY/0w5pcPuVrX9xkqzNpM0wgA/view#3");
        // Load Image Dari res/drawable
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Massindo Group", R.drawable.satu);
        file_maps.put("The Best Spring Air", R.drawable.dua);
        file_maps.put("Spesial Award", R.drawable.tiga);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);

        toolbar = findViewById(R.id.toolbar);

        txt_hasil = findViewById(R.id.txt_hasil);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_hasil.setText(null);
                DialogForm();
            }
        });
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //get SharedPreferences dari Login
        userLg = mPreferences.getString("name", "");
        LgUser.setText(userLg);

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(DialogAdd.this, MainActivity.class);
                finish();
                startActivity(intent);


            }
        });
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(DialogAdd.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_add, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.icon_add);
        dialog.setTitle("Form Add");

        dialog.show();
    }

    public void about(View view) {
        Intent intent = new Intent(DialogAdd.this, AboutActivity.class);
        startActivity(intent);
    }

    public void history(View view) {
        Intent intent = new Intent(DialogAdd.this, ShowPesan.class);
        startActivity(intent);
    }

    public void jalan1(View view) {
        Intent intent = new Intent(DialogAdd.this, AddJalan.class);
        startActivity(intent);
    }

    public void tiba1(View view) {
        Intent intent = new Intent(DialogAdd.this, AddTiba.class);
        startActivity(intent);
    }


    public void pending1(View view) {
        Intent intent =new Intent(DialogAdd.this, AddPending.class);
        startActivity(intent);
    }

    public void selesai(View view) {
        Intent intent = new Intent(DialogAdd.this, AddSelesai.class);
        startActivity(intent);
    }

    public void maps(View view) {
        Intent intent = new Intent(DialogAdd.this,Maps.class);
        startActivity(intent);
    }
}