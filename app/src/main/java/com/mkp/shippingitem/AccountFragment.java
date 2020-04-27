package com.mkp.shippingitem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.mkp.shippingitem.Attadance.AbsenSakit;
import com.mkp.shippingitem.Attadance.Checkin;
import com.mkp.shippingitem.Attadance.IzinActivity;
import com.mkp.shippingitem.Attadance.ShowAbsen;
import com.mkp.shippingitem.Invoice.InvoiceInsert;
import com.mkp.shippingitem.model.InvoiceData;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AccountFragment extends Fragment {

    TextView username, useremail, LgUser, noHP, iduser, alamat;
    TextView txt_hasil;
    EditText number;
    public String userLg, phoneUser, email, UserId;
    private SharedPreferences mPreferences;
    ImageView qrcode, barcode ;
    ImageView Checkinn,sakit,ijin;
        TextView invoice;
                TextView listabsen,logout;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private String enteredNumber, enteredCreator;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_account_fragment, container, false);
        LgUser = view.findViewById(R.id.LgUser);
        noHP = view.findViewById(R.id.noHp);
        number = view.findViewById(R.id.txt_number);
        txt_hasil = view.findViewById(R.id.txt_hasil);

        Checkinn = view.findViewById(R.id.Checkinn);
        Checkinn.setOnClickListener(check);
        useremail = view.findViewById(R.id.Email);
        iduser = view.findViewById(R.id.Iduser);
        qrcode = view.findViewById(R.id.qrcode);
        qrcode.setOnClickListener(qrbarcode);
        barcode = view.findViewById(R.id.barcode);
        barcode.setOnClickListener(barcodee);
        listabsen = view.findViewById(R.id.listabsen);
        listabsen.setOnClickListener(lisst);
        sakit = view.findViewById(R.id.btnsakit);
        sakit.setOnClickListener(sakitt);
        ijin = view.findViewById(R.id.btnijin);
        ijin.setOnClickListener(ijinn);

        invoice = view.findViewById(R.id.Invoice);
        invoice.setOnClickListener(invoiec);

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(out);

        mPreferences = getActivity().getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //get SharedPreferences dari Login
        userLg = mPreferences.getString("name", "");
        phoneUser = mPreferences.getString("phone", "");
        email = mPreferences.getString("email", "");
        UserId = mPreferences.getString("id", "");

        LgUser.setText(userLg);
        noHP.setText(phoneUser);
        useremail.setText(email);
        iduser.setText(UserId);
        return view;

    }

    private View.OnClickListener qrbarcode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), QrBarcode.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener lisst = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ShowAbsen.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener invoiec = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),InvoiceInsert.class);
            startActivity(intent);
        }
    };

   /* private void DialogForm() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_add, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Biodata");

        Button AddButton = dialogView.findViewById(R.id.btnsubmit);
        //// insert button
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Invoice Number wajib di isi", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    new AccountFragment.insert().execute();
                    Intent intent = new Intent(getActivity(), InvoiceInsert.class);
                    Toast.makeText(getActivity(),"Berhasil",Toast.LENGTH_SHORT).show();
                    intent.putExtra("data2", number.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }*/

    private View.OnClickListener out = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            editor.commit();
            setLoginState(true);
            Log.d(TAG, "Now log out and start the activity login");
            Intent intent = new Intent(getActivity(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        private void setLoginState(boolean status) {

            SharedPreferences.Editor ed = mPreferences.edit();
            ed.putBoolean("setLoggingOut", status);
            ed.clear();
            ed.commit();

        }

    };


    private View.OnClickListener barcodee = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), Barcode.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener check = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), Checkin.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener sakitt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AbsenSakit.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener ijinn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IzinActivity.class);
            startActivity(intent);
        }
    };

    private class insert extends AsyncTask<String, Void, InvoiceData> {
        private ProgressDialog pgDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {

            pgDialog.setMessage("\tMohon Tunggu");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected InvoiceData doInBackground(String... strings) {
            enteredNumber = number.getText().toString();
            enteredCreator = LgUser.getText().toString();

            try {
                return AppConstant.getInvoiceInsert().insert(getActivity(), enteredNumber,enteredCreator);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(InvoiceData result) {
            super.onPostExecute(result);
            pgDialog.dismiss();
            try {

                LogHelper.verbose(TAG,"RESULT :"+result);

            } catch (NullPointerException e) {
                e.printStackTrace();
                LogHelper.verbose(TAG, e.getMessage());
            }
        }
    }
}