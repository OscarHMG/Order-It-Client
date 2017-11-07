package com.oscarhmg.orderit.utils;

import android.widget.EditText;

/**
 * Created by user on 07/11/2017.
 */

public class UtilsEditText {


    public static boolean isEmpty(EditText editText){
        String content = editText.getText().toString();
        if (content.trim().equals(""))
            return true;
        else
            return false;
    }



    public static boolean isPhoneCorrectFormat(EditText editText) {
        boolean isEmptyResult = false;
        if (editText.getText().length() == 10) {
            isEmptyResult = true;
        }
        return isEmptyResult;
    }


    public static boolean isPasswordCorrectFormat(EditText editText) {
        boolean isEmptyResult = false;
        if (editText.getText().length() >= 6) {
            isEmptyResult = true;
        }
        return isEmptyResult;
    }
}
