package com.kis.mypay.sql;

public class UserInfo {
    public String name;
    public String phone;
    public String token;

    public UserInfo(String name, String phone, String token) {
        this.name = name;
        this.phone = phone;
        this.token = token;
    }

    public UserInfo() {

    }
}
