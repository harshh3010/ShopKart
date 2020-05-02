package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codebee.shopkart.Model.User;
import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private UserApi userApi = UserApi.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                    finish();
                }else{
                    updateData(firebaseAuth.getCurrentUser());
                }
            }
        },500);
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

                        startActivity(new Intent(SplashScreen.this,ShopActivity.class));
                        finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
