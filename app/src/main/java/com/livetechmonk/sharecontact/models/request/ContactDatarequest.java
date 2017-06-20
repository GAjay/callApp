package com.livetechmonk.sharecontact.models.request;

import com.livetechmonk.sharecontact.models.response.ContactData;

import java.util.ArrayList;

/**
 * Created by ajay on 20/6/17.
 */

public class ContactDatarequest {
    String deviceId;
    ArrayList<ContactData> contactData = new ArrayList<>();

    public ContactDatarequest(String deviceId, ArrayList<ContactData> contactDatas) {
        this.deviceId = deviceId;
        this.contactData = contactDatas;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ArrayList<ContactData> getContactDatas() {
        return contactData;
    }

    public void setContactDatas(ArrayList<ContactData> contactDatas) {
        this.contactData = contactDatas;
    }

    @Override
    public String toString() {
        return "ContactDatarequest{" +
                "deviceId='" + deviceId + '\'' +
                ", contactData=" + contactData +
                '}';
    }
}
