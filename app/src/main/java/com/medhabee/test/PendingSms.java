package com.medhabee.test;

/**
 * Created by gaangchil on 9/18/16.
 */
public class PendingSms {
    private String sms;
    private String phone;
    private String timestamp;

    public PendingSms(String phone, String sms, String timestamp) {
        this.phone = phone;
        this.sms = sms;
        this.timestamp = timestamp;
    }

    public String getSms() {
        return sms;
    }

    public String getPhone() {
        return phone;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
