package com.example.tw2ver01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class page_register extends AppCompatActivity {

    Button rbtn;
    EditText rusername,riptmail,riptpwd,rcpwd,rphone,rcall,rdeviceCode;
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


        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(riptmail.getText().toString()) && TextUtils.isEmpty(rusername.getText().toString()) && TextUtils.isEmpty(riptpwd.getText().toString()) && TextUtils.isEmpty(rcpwd.getText().toString()) && TextUtils.isEmpty(rcall.getText().toString()) && TextUtils.isEmpty(rphone.getText().toString())){

                    String message = "All input required";
                    Toast.makeText(page_register.this,message,Toast.LENGTH_LONG).show();

                }else{
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(riptmail.getText().toString());
                    registerRequest.setPassword(riptpwd.getText().toString());
                    registerRequest.setContactPerson(rusername.getText().toString());
                    registerRequest.setRelationship(rcall.getText().toString());
                    registerRequest.setContactNo(rphone.getText().toString());

                    registerRequest.setDeviceCode(Long.valueOf(rdeviceCode.getText().toString()));

//                    registerUser(registerRequest);
                }
            }
        });
    }

//    public void registerUser(RegisterRequest registerRequest){
//        Call<RegisterResponse> registerResponseCall = ApiClinent.getService().registerUser(registerRequest);
//        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
//
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//
//                if(response.isSuccessful()){
////                    String message = "Successful ..";
//                    Toast.makeText(page_register.this, "Successful ..",Toast.LENGTH_LONG).show();
//
//                    startActivity(new Intent(page_register.this,page_login.class));
//                    finish();
//
//                }else{
//                    String message = "An error occurred please try again later ...";
//                    Toast.makeText(page_register.this,message,Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                String message = t.getLocalizedMessage();
//                Toast.makeText(page_register.this,message,Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}