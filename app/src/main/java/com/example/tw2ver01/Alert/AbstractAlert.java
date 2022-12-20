package com.example.tw2ver01.Alert;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public abstract class AbstractAlert {
    String title;
    String message;
    Context context;

    AbstractAlert(Context context) {
        this.context = context;
    }

    public final void AlertDialog() {
        setTitle();
        setMessage();
        setDialog();
    }

    abstract public void setTitle();

    abstract public void setMessage();

    public void setDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(message).setCancelable(false).setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do something
            }
        });
        //Creating dialog box
        AlertDialog dialog = alert.create();
        //Setting the title manually
        dialog.setTitle(title);
        dialog.show();
    }
}
