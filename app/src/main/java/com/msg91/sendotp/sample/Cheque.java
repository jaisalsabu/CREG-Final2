package com.msg91.sendotp.sample;

public class Cheque {

    private String Trackid;
    private String Form;
    private String Status;



    public Cheque(String Trackid,String Form,String Status) {

        this.Trackid = Trackid;
        this.Form = Form;
        this.Status= Status;


    }

    public Cheque() {
    }


    public String getTrackid() {
        return Trackid;
    }
    public String getForm() {
        return Form;
    }
    public String getStatus() {
        return Status;
    }
    }
