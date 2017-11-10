package com.oscarhmg.orderit.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class UtilsFormat {

    public static String stringToDollarFormat(int value){
        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(value);
    }

    public static int parseStringToInt(String value){
        return Integer.parseInt(value);
    }
}
