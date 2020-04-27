package com.mkp.shippingitem;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager minst;
    private static Context mct;


    private static final String SHARD_PERFNAME="CurrentUser";
    private static final String KEY_ID="id";
    private static final String KEY_USERNAME="name";
    private static final String KEY_EMAIL="email";
    private SharedPrefManager(Context context){
        mct=context;
    }
    public static synchronized SharedPrefManager getInstans(Context context){
        if (minst==null){
            minst=new SharedPrefManager(context);
        }
        return minst;
    }
    public boolean userLogin(String id,String name,String email){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ID,id);
        editor.putString(KEY_USERNAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.apply();
        return true;
    }
    public boolean isLogin(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_ID,null)!=null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public String getUserId(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,null);

    }
    public String getUsername(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);

    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);

    }
}