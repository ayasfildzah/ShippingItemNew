package com.mkp.shippingitem.util;

import com.mkp.shippingitem.service.AbsentPresentApi;
import com.mkp.shippingitem.service.InsertMarker;
import com.mkp.shippingitem.service.InvoiceApi;
import com.mkp.shippingitem.service.InvoiceDetailsApi;
import com.mkp.shippingitem.service.ListInvoice;
import com.mkp.shippingitem.service.ListInvoiceDetails;
import com.mkp.shippingitem.service.LoginPresenterApi;
import com.mkp.shippingitem.service.ShippingInsertPresenterApi;
import com.mkp.shippingitem.service.ShippingPresenterApi;

public class AppConstant {


    public static final String BASE_URL = "https://alita.massindo.com/api/v1/";
    public static final int READ_TIMEOUT = 30000;
    public static final int CONNECTION_TIMEOUT = 30000;
    public static String RESULT = "RESULT";
    private static ShippingPresenterApi svcApi;
    private static LoginPresenterApi loginApi;
    private static ShippingInsertPresenterApi shippingInsertPresenterApi;
    private static InsertMarker insertMarker;
    private static AbsentPresentApi absApi;
    private static InvoiceApi invoiceInsert;
    private static ListInvoice listInvoice;
    private static InvoiceDetailsApi invoiceDetailsApi;
    private static ListInvoiceDetails listInvoiceDetails;

    public static ShippingPresenterApi getApiShippingList() {
        if (svcApi != null) {
            return svcApi;
        } else {
            svcApi = new ShippingPresenterApi();
        }
        return svcApi;
    }

    public static ListInvoiceDetails getListInvoiceDetails() {
        if (listInvoiceDetails != null) {
            return listInvoiceDetails;
        } else {
            listInvoiceDetails = new ListInvoiceDetails();
        }
        return listInvoiceDetails;
    }
    public static ListInvoice getListInvoice() {
        if (listInvoice != null) {
            return listInvoice;
        } else {
            listInvoice = new ListInvoice();
        }
        return listInvoice;
    }


    public static AbsentPresentApi getAbsentPresentApi() {
        if (absApi != null) {
            return absApi;
        } else {
            absApi = new AbsentPresentApi();
        }
        return absApi;
    }


    public static LoginPresenterApi getLoginApi() {
        if (loginApi != null) {
            return loginApi;
        } else {
            loginApi = new LoginPresenterApi();
        }
        return loginApi;
    }

    public static ShippingInsertPresenterApi getShippingInsertPresenterApi() {
        if (shippingInsertPresenterApi != null) {
            return shippingInsertPresenterApi;
        } else {
            shippingInsertPresenterApi = new ShippingInsertPresenterApi();
        }
        return shippingInsertPresenterApi;
    }

    public static InvoiceDetailsApi getInvoiceDetailsApi() {
        if (invoiceDetailsApi != null) {
            return invoiceDetailsApi;
        } else {
            invoiceDetailsApi = new InvoiceDetailsApi();
        }
        return invoiceDetailsApi;
    }

    public static InvoiceApi getInvoiceInsert() {
        if (invoiceInsert != null) {
            return invoiceInsert;
        } else {
            invoiceInsert= new InvoiceApi();
        }
        return invoiceInsert;
    }

    public static InsertMarker getInsertMarker() {
        if (insertMarker != null) {
            return insertMarker;
        } else {
            insertMarker = new InsertMarker();
        }
        return insertMarker;
    }
}