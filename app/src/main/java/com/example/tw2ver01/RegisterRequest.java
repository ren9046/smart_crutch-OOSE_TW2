package com.example.tw2ver01;


public class RegisterRequest {


    private String contactPerson;//聯絡人
    private String email;
    private String password;
    private String contactNumber;//電話
    private String lineToken;//LineToken
    private Long deviceCode;



    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }



    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }



    public void setContactNo(String contactNo) {
        this.contactNumber = contactNo;
    }



    public void setLineToken(String LineToken) {
        this.lineToken = LineToken;
    }


    public void setDeviceCode(Long deviceCode) {

        this.deviceCode = deviceCode;
    }
}