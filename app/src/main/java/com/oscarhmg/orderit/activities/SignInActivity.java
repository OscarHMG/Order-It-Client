package com.oscarhmg.orderit.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.model.User;
import com.oscarhmg.orderit.utils.UtilsDialog;
import com.oscarhmg.orderit.utils.UtilsEditText;

public class SignInActivity extends AppCompatActivity {

    private EditText editTextPhone, editTextPassword;
    private Button signIn;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        signIn = (Button) findViewById(R.id.signIn);

        progressDialog = new ProgressDialog(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableUser = database.getReference("User");


        signIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {






                tableUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get user information

                        if (validateEditTexts()){
                            UtilsDialog.createAndShowDialogProgress(progressDialog,  getString(R.string.verify_login_info));

                            if(dataSnapshot.child(editTextPhone.getText().toString()).exists()) {
                                UtilsDialog.dismissDialog(progressDialog);
                                User user = dataSnapshot.child(editTextPhone.getText().toString()).getValue(User.class);
                                validateUser(user);
                            }else{
                                UtilsDialog.dismissDialog(progressDialog);
                                Toast.makeText(SignInActivity.this, getString(R.string.user_not_exist), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    /**
     * Validate user, if all is correct, proceed to register user in DB Firebase
     * @param user
     */
    public void validateUser(User user){
        if(user.getPassword().equals(editTextPassword.getText().toString()))
            Toast.makeText(this, getString(R.string.login_succesfully), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,  getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }


    /**
     * Validate editexts to avoid errors in the storage in DB
     * @return
     */
    public boolean validateEditTexts(){
        if(UtilsEditText.isEmpty(editTextPhone) || UtilsEditText.isEmpty(editTextPassword)){
            Toast.makeText(this, getString(R.string.field_empties), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!UtilsEditText.isPhoneCorrectFormat(editTextPhone)){
            Toast.makeText(this, getString(R.string.field_phone_error), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!UtilsEditText.isPasswordCorrectFormat(editTextPassword)){
            Toast.makeText(this, getString(R.string.field_password_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
