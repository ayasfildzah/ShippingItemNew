package com.mkp.shippingitem;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QrBarcode extends AppCompatActivity {

    private TextView mInputText;
    private ImageView mImageView;
    private FloatingActionButton mSave;
    private Activity mActivity;
    private Bitmap generatedBitmap;
    private String fileName,Idnoo;
    SharedPreferences mPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_barcode);

        mInputText = findViewById(R.id.Idno);
        mImageView = findViewById(R.id.outputBitmap);
        mSave = findViewById(R.id.save);


        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    mImageView.setImageResource(R.drawable.ic_placeholder);
                } else {
                    try {
                        generateQRcode(charSequence.toString());
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //get SharedPreferences dari Login
        Idnoo = mPreferences.getString("id","");
        mInputText.setText(Idnoo);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage(generatedBitmap);
            }
        });


    }

    private void generateQRcode(String s) throws WriterException {
        fileName = s;
        BitMatrix result;
        result = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 1080, 1080, null);
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 1080, 0, 0, w, h);
        generatedBitmap = bitmap;
        mImageView.setImageBitmap(bitmap);
    }

    private void saveImage(Bitmap generatedBitmap) {
        FileOutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "QRCodeBarcode");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (fileName.contains("/")) {
            fileName = fileName.replace("/", "\\");
        }
        String filePath = (file.getAbsolutePath() + "/" + fileName + ".png");
        try {
            out = new FileOutputStream(filePath);
            generatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(mActivity, "File saved at\n" + filePath, Toast.LENGTH_SHORT).show();
    }
}