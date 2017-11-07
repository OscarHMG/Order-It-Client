package com.oscarhmg.orderit.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
//import com.oscarhmg.orderit.utils.UtilsDialog;
import com.oscarhmg.orderit.utils.UtilsDialog;
import com.oscarhmg.orderit.utils.UtilsEditText;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextName, editTextPhone, editTextPassword;
    private Button signUp;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        signUp = (Button) findViewById(R.id.signUp);
        progressDialog = new ProgressDialog(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableUser = database.getReference("User");


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here show dialog.

                tableUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(validateEditTexts()){
                            UtilsDialog.createAndShowDialogProgress(progressDialog,  getString(R.string.registering_user));
                            if(!dataSnapshot.child(editTextPhone.getText().toString()).exists()){
                                //Doesnt exist, I can create the user
                                UtilsDialog.dismissDialog(progressDialog);
                                registerUser(tableUser);

                            }else{
                                //Number exist, I cant create.
                                UtilsDialog.dismissDialog(progressDialog);
                                Toast.makeText(SignUpActivity.this, getString(R.string.user_already_register), Toast.LENGTH_LONG).show();
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

    public void registerUser(DatabaseReference table){
        User newUser = new User(editTextName.getText().toString(), editTextPassword.getText().toString());
        table.child(editTextPhone.getText().toString()).setValue(newUser);
        Toast.makeText(SignUpActivity.this, getString(R.string.user_register_succesfully), Toast.LENGTH_LONG).show();
        finish();
    }



    /**
     * Validate editexts to avoid errors in the storage in DB
     * @return
     */
    public boolean validateEditTexts(){
        if(UtilsEditText.isEmpty(editTextPhone) || UtilsEditText.isEmpty(editTextPassword)
                || UtilsEditText.isEmpty(editTextName)){
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
