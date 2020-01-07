package com.mkp.shippingitem.util;

import com.mkp.shippingitem.service.api;

public class AppConstant {

    public static final String BASE_URL="https://alita.massindo.com";
    public static final int READ_TIMEOUT=30000;
    public static final int CONNECTION_TIMEOUT=30000;
    public static  String RESULT="RESULT";
    private static api svcApi;

    public static api getApi(){
        if(svcApi !=null){
            return svcApi;
        }else{
            svcApi = new api();
        }
        return  svcApi;
    }
}
