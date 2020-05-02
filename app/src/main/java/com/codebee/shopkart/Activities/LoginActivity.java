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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText email_txt,pwd_txt;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog pd;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private UserApi userApi = UserApi.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_txt = findViewById(R.id.login_email_text);
        pwd_txt = findViewById(R.id.login_password_text);

        findViewById(R.id.login_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void registerNow(View v){
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    private void login(){
        pd = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        pd.setMessage("Please wait...");
        pd.show();

        firebaseAuth.signInWithEmailAndPassword(email_txt.getText().toString().trim(),pwd_txt.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(firebaseAuth.getCurrentUser().isEmailVerified() == true){
                            updateData(firebaseAuth.getCurrentUser());
                        }else{
                            pd.dismiss();
                            email_txt.setError("Please verify your email id first.");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this,"Unable to sign-in!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateData(FirebaseUser firebaseUser){
        db.getReference("Users")
                .child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        userApi.setName(user.getName());
                        userApi.setOrders(user.getOrders());
                        userApi.setWishlist(user.getWishlist());
                        userApi.setFCMToken(user.getFCMToken());
                        userApi.setEmail(user.getEmail());
                        userApi.setCredits(user.getCredits());
                        userApi.setCart(user.getCart());
                        userApi.setId(user.getId());

                        pd.dismiss();
                        startActivity(new Intent(LoginActivity.this,ShopActivity.class));
                        finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
