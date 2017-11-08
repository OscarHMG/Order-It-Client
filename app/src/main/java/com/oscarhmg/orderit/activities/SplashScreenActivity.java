package com.oscarhmg.orderit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oscarhmg.orderit.utils.SessionManager;

/**
 * Created by OscarHMG on 07/11/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        session = new SessionManager(this);
        session.checkLogin();
    }
}
