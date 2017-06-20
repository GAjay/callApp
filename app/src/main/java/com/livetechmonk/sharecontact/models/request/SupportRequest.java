package com.livetechmonk.sharecontact.models.request;

/**
 * Created by ajay on 20/6/17.
 */

public class SupportRequest {
    String name;
    String message;
    String phone;
    String email;

    public SupportRequest(String name, String phone,String email,String message){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.message = message;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
