package com.oscarhmg.orderit.utils;

import android.app.ProgressDialog;


/**
 * Created by OscarHMG on 07/11/2017.
 */

public class UtilsDialog {



    public static void createAndShowDialogProgress(ProgressDialog progressDialog,  String message){
        //progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void dismissDialog(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }
}
