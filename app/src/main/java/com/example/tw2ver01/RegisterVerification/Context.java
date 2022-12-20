package com.example.tw2ver01.RegisterVerification;

public class Context {
    private final String emailAddress;

    public Context(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContext() {
        return this.emailAddress;
    }
}
