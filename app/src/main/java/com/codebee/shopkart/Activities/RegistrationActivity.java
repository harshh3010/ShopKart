package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codebee.shopkart.Model.User;
import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name_txt,email_txt,pwd_txt,confirm_pwd_txt;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog pd;
    private UserApi userApi = UserApi.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name_txt = findViewById(R.id.registration_name_text);
        email_txt = findViewById(R.id.registration_email_text);
        pwd_txt = findViewById(R.id.registration_password_text);
        confirm_pwd_txt = findViewById(R.id.registration_confirm_password_text);

        findViewById(R.id.registration_signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name_txt.getText().toString().isEmpty()
                        && !email_txt.getText().toString().isEmpty()
                        && !pwd_txt.getText().toString().isEmpty()
                        && !confirm_pwd_txt.getText().toString().isEmpty()){
                    if(confirm_pwd_txt.getText().toString().equals(pwd_txt.getText().toString())){
                       registerUser();
                    }else{
                        confirm_pwd_txt.setError("Confirmation password doesn't  match with the one you created.");
                    }
                }
                if(name_txt.getText().toString().isEmpty())
                    name_txt.setError("This field cannot be left empty.");
                if(email_txt.getText().toString().isEmpty())
                    email_txt.setError("This field cannot be left empty.");
                if(pwd_txt.getText().toString().isEmpty())
                    pwd_txt.setError("This field cannot be left empty.");
                if(confirm_pwd_txt.getText().toString().isEmpty())
                    confirm_pwd_txt.setError("This field cannot be left empty.");
            }
        });

    }

    private void registerUser(){
        pd = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        pd.setMessage("Please wait...");
        pd.show();
        firebaseAuth
                .createUserWithEmailAndPassword(email_txt.getText().toString().trim(),pwd_txt.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        final FirebaseUser firebaseUser = authResult.getUser();
                        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                updateUserData(firebaseUser);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(RegistrationActivity.this,"An error occured !",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this,"Unable to register!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUserData(FirebaseUser firebaseUser){

        userApi.setCart(0);
        userApi.setEmail(firebaseUser.getEmail());
        userApi.setId(firebaseUser.getUid());
        userApi.setName(name_txt.getText().toString().trim());
        userApi.setOrders(0);
        userApi.setWishlist(0);
        userApi.setCredits(500.00);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                userApi.setFCMToken(newToken);
            }
        });

        User user = new User();
        user.setCart(userApi.getCart());
        user.setCredits(userApi.getCredits());
        user.setEmail(userApi.getEmail());
        user.setFCMToken(userApi.getFCMToken());
        user.setId(userApi.getId());
        user.setName(userApi.getName());
        user.setOrders(userApi.getOrders());
        user.setWishlist(userApi.getWishlist());

        db.getReference("Users")
                .child(userApi.getId())
                .setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(RegistrationActivity.this,
                                "Registered Successfully! Please verify your email address to continue.",
                                Toast.LENGTH_LONG)
                                .show();
                        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(RegistrationActivity.this,"Failed to update user data!",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
