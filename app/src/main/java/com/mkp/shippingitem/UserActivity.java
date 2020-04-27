package com.mkp.shippingitem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import com.mkp.shippingitem.ShippingAdd.AddJalan;
import com.mkp.shippingitem.ShippingAdd.AddPending;
import com.mkp.shippingitem.ShippingAdd.AddSelesai;
import com.mkp.shippingitem.ShippingAdd.AddTiba;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserActivity extends Fragment {

    SliderLayout sliderLayout;
    ImageView jalan,tiba,selesai,pending,history,maps;
    private SharedPreferences mPreferences;
    public UserActivity() {
        // Required empty public constructor
    }

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_user, container, false);
        jalan = view.findViewById(R.id.jalan);
        jalan.setOnClickListener(addjalan);

        tiba = view.findViewById(R.id.tiba);
        tiba.setOnClickListener(addtiba);

        selesai = view.findViewById(R.id.selesai);
        selesai.setOnClickListener(addselesai);

        pending = view.findViewById(R.id.pending);
        pending.setOnClickListener(addpending);
        history = view.findViewById(R.id.history);
        history.setOnClickListener(addhistory);
        maps = view.findViewById(R.id.maps);
        maps.setOnClickListener(addmaps);

        return view;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logoutt:
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.clear();
                editor.commit();
                setLoginState(true);
                Log.d(TAG, "Now log out and start the activity login");
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return true;

        }
        return false;
    }

    private void setLoginState(boolean status) {

        SharedPreferences.Editor ed = mPreferences.edit();
        ed.putBoolean("setLoggingOut", status);
        ed.clear();
        ed.commit();

    }


    private View.OnClickListener addjalan = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddJalan.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener addtiba = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddTiba.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener addselesai = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddSelesai.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener addpending = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddPending.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener addhistory = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ShowHistory.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener addmaps = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),Maps.class);
            startActivity(intent);
        }
    };
}