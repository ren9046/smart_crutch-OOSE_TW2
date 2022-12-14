package com.example.tw2ver01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tw2ver01.RegisterVerification.Context;
import com.example.tw2ver01.RegisterVerification.emailFrontStringExpression;
import com.example.tw2ver01.RegisterVerification.emailRearStringExpression;
import com.example.tw2ver01.RegisterVerification.passwordStringExpression;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class page_register extends AppCompatActivity {

    Button rbtn;
    EditText rusername, riptmail, riptpwd, rcpwd, rphone, rcall, rdeviceCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_register);

        rbtn = findViewById(R.id.rbtn);
        rusername = findViewById(R.id.rusername);
        riptmail = findViewById(R.id.riptmail);
        riptpwd = findViewById(R.id.riptpwd);
        rcpwd = findViewById(R.id.rcpwd);

        rphone = findViewById(R.id.rphone);
        rcall = findViewById(R.id.rcall);
        rdeviceCode = findViewById(R.id.rdeviceCode);

        //信箱欄位監聽
        riptmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Context emailadd = new Context(riptmail.getText().toString());
                    emailFrontStringExpression frontStringExp = new emailFrontStringExpression();
                    emailRearStringExpression rearStringExp = new emailRearStringExpression();
                    if (frontStringExp.interpret(emailadd.getContext()) == false && rearStringExp.interpret(emailadd.getContext()) == false) {
                        System.out.println("沒有加上@");
                        Toast.makeText(page_register.this, "沒有加上@", Toast.LENGTH_SHORT).show();
                    } else if (frontStringExp.interpret(emailadd.getContext()) == false) {
                        System.out.println("@前面必須是英文加數字");
                        Toast.makeText(page_register.this, "@前面必須是英文加數字", Toast.LENGTH_SHORT).show();
                    } else if (rearStringExp.interpret(emailadd.getContext()) == false) {
                        System.out.println("@後面必須是 英文數字.英文數字(.英文數字)");
                        Toast.makeText(page_register.this, "@後面必須是 英文數字.英文數字(.英文數字)", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("@後面必須是 英文數字.英文數字(.英文數字)");
                        Toast.makeText(page_register.this, "信箱格式正確", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //密碼欄位監聽
        riptpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Context passwordadd = new Context(riptpwd.getText().toString());
                    passwordStringExpression passwordExp = new passwordStringExpression();
                    if (passwordExp.interpret(passwordadd.getContext()) == false) {
                        System.out.println("密碼格式錯誤，須為英文數字6至10位元");
                        Toast.makeText(page_register.this, "密碼格式錯誤，須為英文數字6至10位元", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("密碼格式正確");
                        Toast.makeText(page_register.this, "密碼格式正確", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(riptmail.getText().toString()) && TextUtils.isEmpty(rusername.getText().toString()) && TextUtils.isEmpty(riptpwd.getText().toString()) && TextUtils.isEmpty(rcpwd.getText().toString()) && TextUtils.isEmpty(rcall.getText().toString()) && TextUtils.isEmpty(rphone.getText().toString())) {

                    String message = "All input required";
                    Toast.makeText(page_register.this, message, Toast.LENGTH_LONG).show();

                } else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(riptmail.getText().toString());
                    registerRequest.setPassword(riptpwd.getText().toString());
                    registerRequest.setContactPerson(rusername.getText().toString());
                    registerRequest.setLineToken(rcall.getText().toString());
                    registerRequest.setContactNo(rphone.getText().toString());
                    registerRequest.setDeviceCode(Long.valueOf(rdeviceCode.getText().toString()));

                    registerUser(registerRequest);
                }
            }
        });
    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClinent.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(page_register.this, "Successful ..", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(page_register.this, page_login.class));
                    finish();

                } else {
                    String message = "An error occurred please try again later ...";
                    Toast.makeText(page_register.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(page_register.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}