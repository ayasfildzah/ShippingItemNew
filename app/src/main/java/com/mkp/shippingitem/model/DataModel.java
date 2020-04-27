package com.mkp.shippingitem.model;

public class DataModel {
    private String status;
    private String message;
    //    private DataModel2 dm2;
    private int id;
    private String delivery_number;
    private String creator;
    private String sttus;
    private String note;
    private String created_at;
    private String phone;
    private String location;

    public DataModel() {

    }

    public DataModel(String status, String message, int id, String delivery_number, String creator, String sttus, String note, String created_at, String phone, String location) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.delivery_number = delivery_number;
        this.creator = creator;
        this.sttus = sttus;
        this.note = note;
        this.created_at = created_at;
        this.phone = phone;
        this.location = location;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(String delivery_number) {
        this.delivery_number = delivery_number;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSttus() {
        return sttus;
    }

    public void setSttus(String sttus) {
        this.sttus = sttus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}