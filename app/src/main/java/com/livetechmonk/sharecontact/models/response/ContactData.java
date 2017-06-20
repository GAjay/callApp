package com.livetechmonk.sharecontact.models.response;

/**
 * Created by ajay on 14/6/17.
 */

public class ContactData {

    String name;
    String contactNo;
    public ContactData(String name, String contactNo){
        this.name = name;
        this.contactNo = contactNo;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "{" +
                "name:'" + name + '\'' +
                ", contactNo:'" + contactNo + '\'' +
                '}';
    }
}
