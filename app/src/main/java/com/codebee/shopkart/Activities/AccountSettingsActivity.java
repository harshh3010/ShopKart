package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class AccountSettingsActivity extends AppCompatActivity {

    private EditText name_txt;
    private UserApi userApi = UserApi.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private ProgressDialog pd;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        name_txt = findViewById(R.id.account_settings_name_text);

        name_txt.setText(userApi.getName());

        findViewById(R.id.account_settings_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_txt.getText().toString().trim().equals(userApi.getName())){
                    Snackbar.make(v.getRootView(),"No changes made.",Snackbar.LENGTH_LONG).show();
                }else{
                    updateData();
                }
            }
        });

        findViewById(R.id.account_settings_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingsActivity.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Delete Account");
                builder.setMessage("Please enter your password to continue.");
                final EditText input = new EditText(AccountSettingsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String password = input.getText().toString().trim();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(userApi.getEmail(), password);
                        firebaseAuth.getCurrentUser().reauthenticate(credential)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                        deleteAccount();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(AccountSettingsActivity.this,"Incorrect password!",Toast.LENGTH_SHORT).show();
                            }
                        });
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

        findViewById(R.id.account_settings_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //TODO : change password code

    }


    private void updateData() {
        pd = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        pd.setMessage("Please wait...");
        pd.show();
        db.getReference("Users")
                .child(userApi.getId())
                .child("name")
                .setValue(name_txt.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userApi.setName(name_txt.getText().toString().trim());
                        pd.dismiss();
                        Toast.makeText(AccountSettingsActivity.this,"Saved changes!",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AccountSettingsActivity.this,"An error occured!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAccount() {
        pd = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        pd.setMessage("Please wait...");
        pd.show();
        firebaseAuth.getCurrentUser()
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.getReference("Users")
                                .child(userApi.getId())
                                .removeValue();
                        pd.dismiss();
                        setResult(RESULT_OK, new Intent());
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AccountSettingsActivity.this,"Unable to remove account!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }
}
