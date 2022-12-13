package com.example.tw2ver01;

import org.json.JSONObject;

public class RegisterRequest {
    private JSONObject data = new JSONObject();

    private String contactPerson;//聯絡人
    private String email;
    private String password;
    private String contactNumber;//電話
    private String lineToken;//LineToken
    private Long deviceCode;

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
        return contactNumber;
    }

    public void setContactNo(String contactNo) {
        this.contactNumber = contactNo;
    }

    public String getLineToken() {
        return lineToken;
    }

    public void setLineToken(String LineToken) {
        this.lineToken = LineToken;
    }


    public void setDeviceCode(Long deviceCode) {

        this.deviceCode = deviceCode;
    }
/* for debug use*/
    public JSONObject getJsonString() {
        try {
        data.put("contactNumber", contactNumber.toString());
        data.put("contactPerson", contactPerson.toString());
        data.put("deviceCode", deviceCode.toString());
        data.put("email", email.toString());
        data.put("password", password.toString());
        data.put("lineToken", lineToken.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}