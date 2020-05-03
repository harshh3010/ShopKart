package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;

public class AccountActivity extends AppCompatActivity {

    private TextView name_txt,email_txt,credits_txt,orders_txt,current_balance_txt;
    private UserApi userApi = UserApi.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private AlertDialog dialog;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        name_txt = findViewById(R.id.account_name_text);
        email_txt = findViewById(R.id.account_email_text);
        credits_txt = findViewById(R.id.account_credits_text);
        orders_txt = findViewById(R.id.account_orders_text);
        current_balance_txt = findViewById(R.id.account_current_balance_text);

        name_txt.setText(userApi.getName());
        email_txt.setText(userApi.getEmail());
        orders_txt.setText(userApi.getOrders() + " orders.");
        String numberFormat = NumberFormat.getCurrencyInstance().format(userApi.getCredits());
        credits_txt.setText(numberFormat);
        current_balance_txt.setText("Your current account balance is " + numberFormat);

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

        findViewById(R.id.account_wallet_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findViewById(R.id.account_wallet_card_layout).getVisibility() == View.GONE){
                    credits_txt.setVisibility(View.GONE);
                    findViewById(R.id.account_wallet_card_layout).setVisibility(View.VISIBLE);
                }else if(findViewById(R.id.account_wallet_card_layout).getVisibility() == View.VISIBLE){
                    credits_txt.setVisibility(View.VISIBLE);
                    findViewById(R.id.account_wallet_card_layout).setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.account_add_money_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoney();
            }
        });

        findViewById(R.id.account_orders_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,MyOrdersActivity.class));
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

    private void addMoney(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        View view  = getLayoutInflater().inflate(R.layout.dialog_add_money,null);
        final EditText add_money_txt = view.findViewById(R.id.dialog_add_money_text);
        view.findViewById(R.id.dialog_add_money_proceed_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amt = userApi.getCredits();
                pd = new ProgressDialog(AccountActivity.this,R.style.MyAlertDialogStyle);
                pd.setMessage("Please wait...");
                pd.show();
                if(Double.parseDouble(add_money_txt.getText().toString()) <= 10000.00
                        && Double.parseDouble(add_money_txt.getText().toString()) >= 100.00 ){
                    amt  = amt + Double.parseDouble(add_money_txt.getText().toString());
                    final Double finalAmt = amt;
                    db.getReference("Users")
                            .child(userApi.getId())
                            .child("credits")
                            .setValue(amt)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    userApi.setCredits(finalAmt);
                                    String numberFormat = NumberFormat.getCurrencyInstance().format(userApi.getCredits());
                                    credits_txt.setText(numberFormat);
                                    current_balance_txt.setText("Your current account balance is " + numberFormat);
                                    pd.dismiss();
                                    dialog.dismiss();
                                    Toast.makeText(AccountActivity.this,"Rs " + Double.parseDouble(add_money_txt.getText().toString()) + " added to your wallet.",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            dialog.dismiss();
                            Toast.makeText(AccountActivity.this,"Unable to complete transaction.",Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    pd.dismiss();
                    add_money_txt.setError("The amount to be added must be between Rs 100.00 and Rs 10000.00");
                }
            }
        });
        view.findViewById(R.id.dialog_add_money_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

}
