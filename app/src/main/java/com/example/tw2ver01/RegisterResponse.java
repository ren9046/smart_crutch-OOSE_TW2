package com.example.tw2ver01;

public class RegisterResponse {

//    private int id;
//    private String username;
//    private String email;
//    private String phone;
//    private String call;


    private String contactPerson;//聯絡人
    private String email;
    private String password;
    private String contactNo;//電話
    private String LineToken;//稱謂
    private Device deviceCode;

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLineToken() {
        return LineToken;
    }

    public void setLineToken(String LineToken) {
        this.LineToken = LineToken;
    }

    public Device getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(Device deviceCode) {
        this.deviceCode = deviceCode;
    }
}