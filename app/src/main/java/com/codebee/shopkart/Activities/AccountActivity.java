package com.codebee.shopkart.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;

public class AccountActivity extends AppCompatActivity {

    private TextView name_txt,email_txt,credits_txt,orders_txt;
    private UserApi userApi = UserApi.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        name_txt = findViewById(R.id.account_name_text);
        email_txt = findViewById(R.id.account_email_text);
        credits_txt = findViewById(R.id.account_credits_text);
        orders_txt = findViewById(R.id.account_orders_text);

        name_txt.setText(userApi.getName());
        email_txt.setText(userApi.getEmail());
        orders_txt.setText(userApi.getOrders() + " orders.");
        String numberFormat = NumberFormat.getCurrencyInstance().format(userApi.getCredits());
        credits_txt.setText(numberFormat);

        findViewById(R.id.account_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.account_logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Sign-out");
                builder.setMessage("Do you really wish to sign out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logoutUser();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        findViewById(R.id.account_settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AccountActivity.this,AccountSettingsActivity.class),1);
            }
        });
    }

    private void logoutUser() {
        firebaseAuth.signOut();
        setResult(RESULT_OK, new Intent());
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            logoutUser();
        }
    }
}
