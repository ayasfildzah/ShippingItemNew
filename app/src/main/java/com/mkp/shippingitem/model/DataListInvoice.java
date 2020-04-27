package com.mkp.shippingitem.model;

public class DataListInvoice {

    private String status;
    private String message;
    private String id;
    private String created_at;
    private String updated_at;
    private String invoice_recap_number;
    private String creator;
    private String statuss;
    private String ar_confirm_recap;

    public DataListInvoice(String status, String message, String id, String created_at, String updated_at, String invoice_recap_number, String creator, String statuss, String ar_confirm_recap) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.invoice_recap_number = invoice_recap_number;
        this.creator = creator;
        this.statuss = statuss;
        this.ar_confirm_recap = ar_confirm_recap;
    }

    public DataListInvoice() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getInvoice_recap_number() {
        return invoice_recap_number;
    }

    public void setInvoice_recap_number(String invoice_recap_number) {
        this.invoice_recap_number = invoice_recap_number;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public String getAr_confirm_recap() {
        return ar_confirm_recap;
    }

    public void setAr_confirm_recap(String ar_confirm_recap) {
        this.ar_confirm_recap = ar_confirm_recap;
    }
}

