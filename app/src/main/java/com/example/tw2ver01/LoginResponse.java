package com.example.tw2ver01;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String user_id;
    private String email;
    private String username;

    public String getEmail() {
        return email;
    }
}
