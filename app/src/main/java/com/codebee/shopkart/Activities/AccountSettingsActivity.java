package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
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
    private AlertDialog alertDialog;

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
                        if(password.isEmpty()){
                            Toast.makeText(AccountSettingsActivity.this,"Please enter password to proceed.",Toast.LENGTH_SHORT).show();
                        }else{
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

        findViewById(R.id.account_settings_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

    }

    private void  updatePassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingsActivity.this);
        View view = LayoutInflater.from(AccountSettingsActivity.this).inflate(R.layout.dialog_change_password,null);
        final EditText oldpass,newpass,cnfpass;
        oldpass = view.findViewById(R.id.change_password_old_text);
        newpass = view.findViewById(R.id.change_password_new_text);
        cnfpass = view.findViewById(R.id.change_password_confirm_text);
        view.findViewById(R.id.change_password_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.change_password_proceed_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cnfpass.getText().toString().trim().isEmpty()
                        && !newpass.getText().toString().trim().isEmpty()
                        && !oldpass.getText().toString().trim().isEmpty()){
                    pd = new ProgressDialog(AccountSettingsActivity.this,R.style.AppCompatAlertDialogStyle);
                    pd.setMessage("Please wait...");
                    pd.show();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(userApi.getEmail(), oldpass.getText().toString().trim());
                    firebaseAuth.getCurrentUser().reauthenticate(credential)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if(cnfpass.getText().toString().trim().equals(newpass.getText().toString().trim())){
                                        firebaseAuth.getCurrentUser().updatePassword(newpass.getText().toString().trim())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        db.getReference("Users")
                                                                .child(userApi.getId())
                                                                .child("password")
                                                                .setValue(newpass.getText().toString().trim());
                                                        pd.dismiss();
                                                        alertDialog.dismiss();
                                                        Toast.makeText(AccountSettingsActivity.this,"Saved changes!",Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                alertDialog.dismiss();
                                                Toast.makeText(AccountSettingsActivity.this,"An error occured!",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else{
                                        pd.dismiss();
                                        cnfpass.setError("This doesn't match with the entered password.");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            oldpass.setError("Incorrect password");
                        }
                    });
                }else{
                    if(cnfpass.getText().toString().trim().isEmpty())
                        cnfpass.setError("This field cannot be left empty.");
                    if(newpass.getText().toString().trim().isEmpty())
                        newpass.setError("This field cannot be left empty.");
                    if(oldpass.getText().toString().trim().isEmpty())
                        oldpass.setError("This field cannot be left empty.");
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
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
