/*package com.mkp.shippingitem.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.haltev.mazeid.Fragment.Chats;
import com.haltev.mazeid.Fragment.Contacts;
import com.haltev.mazeid.Fragment.Gallery;
import com.haltev.mazeid.Home;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    //Context, memberi akses activity,fragment, dan service
    // untuk dapat akses data (angka,txt,file,gmbr,dll)
    private Context context;

    public MyFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return Contacts.getInstance();
        }
        else if (position == 1){
            return Chats.getInstance();
        }
        else if (position == 2){
            return Gallery.getInstance();
        }
        else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //Panggil method, getPageTitle untk dapet namaPage per Fragmentnya
    //Ctrl + O ( HURUF O )
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Contacts";
            case 1:
                return "Chats";
            case 2:
                return "Gallery";
        }
        return "";
    }
} */
