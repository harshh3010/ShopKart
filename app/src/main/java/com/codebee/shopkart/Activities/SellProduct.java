package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.Model.User;
import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SellProduct extends AppCompatActivity {

    public static final int PICK_FEATURED_IMAGE = 1;
    public static final int PICK_IMG1 = 2;
    public static final int PICK_IMG2 = 3;
    public static final int PICK_IMG3 = 4;
    private Uri featuredImageUri, img1Uri,img2Uri,img3Uri;
    private UserApi userApi = UserApi.getInstance();
    private AlertDialog dialog;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("ProductImages");
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private Double price;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        findViewById(R.id.sell_products_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.sell_products_proceed_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed();
            }
        });

        findViewById(R.id.sell_products_featured_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_FEATURED_IMAGE);
            }
        });

        findViewById(R.id.sell_products_img1_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_IMG1);
            }
        });

        findViewById(R.id.sell_products_img2_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_IMG2);
            }
        });

        findViewById(R.id.sell_products_img3_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_IMG3);
            }
        });
    }

    private void proceed() {
        if (!((EditText) findViewById(R.id.sell_product_name_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_description_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_model_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_price_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_quantity_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_brand_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_size_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_colour_text)).getText().toString().trim().isEmpty()
                && featuredImageUri != null) {
            openPaymentDialog();
        } else {
            if (((EditText) findViewById(R.id.sell_product_name_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_name_text)).setError("This field cannot be left empty.");
            }
            if (((EditText) findViewById(R.id.sell_product_description_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_description_text)).setError("This field cannot be left empty.");
            }
            if (((EditText) findViewById(R.id.sell_product_model_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_model_text)).setError("This field cannot be left empty.");
            }
            if (((EditText) findViewById(R.id.sell_product_price_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_price_text)).setError("This field cannot be left empty.");
            }
            if (((EditText) findViewById(R.id.sell_product_quantity_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_quantity_text)).setError("This field cannot be left empty.");
            }
            if (((EditText) findViewById(R.id.sell_product_brand_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_brand_text)).setError("This field cannot be left empty.");
            }
            if (((EditText) findViewById(R.id.sell_product_size_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_size_text)).setError("This field cannot be left empty.");
            }
            if (((EditText) findViewById(R.id.sell_product_colour_text)).getText().toString().trim().isEmpty()) {
                ((EditText) findViewById(R.id.sell_product_colour_text)).setError("This field cannot be left empty.");
            }
            if (featuredImageUri == null){
                Snackbar.make(getWindow().getDecorView().getRootView(),"Please select a featured image for the product.",Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void pickImage(int reqCode){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), reqCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FEATURED_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            featuredImageUri = data.getData();
            if(featuredImageUri != null){
                ((ImageView)findViewById(R.id.sell_products_featured_image)).setImageURI(featuredImageUri);
            }
        }else if (requestCode == PICK_IMG1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            img1Uri = data.getData();
            if(img1Uri != null){
                ((ImageView)findViewById(R.id.sell_products_img1_image)).setImageURI(img1Uri);
            }
        }else if (requestCode == PICK_IMG2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            img2Uri = data.getData();
            if(img2Uri != null){
                ((ImageView)findViewById(R.id.sell_products_img2_image)).setImageURI(img2Uri);
            }
        }else if (requestCode == PICK_IMG3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            img3Uri = data.getData();
            if(img3Uri != null){
                ((ImageView)findViewById(R.id.sell_products_img3_image)).setImageURI(img3Uri);
            }
        }
    }

    private void openPaymentDialog(){

        price = Double.parseDouble(((EditText)findViewById(R.id.sell_product_price_text)).getText().toString());
        int qty = Integer.parseInt(((EditText)findViewById(R.id.sell_product_quantity_text)).getText().toString());
        price = 0.9*price*qty;

        AlertDialog.Builder builder = new AlertDialog.Builder(SellProduct.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_sell_product,null);
        ((TextView)view.findViewById(R.id.dialog_sell_amt_text)).setText("Rs. " + price);
        view.findViewById(R.id.dialog_sell_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.dialog_sell_proceed_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addProduct();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

    }

    private void addProduct(){

        pd = new ProgressDialog(SellProduct.this,R.style.MyAlertDialogStyle);
        pd.setMessage("Adding product...");
        pd.show();

        final String productId = db.getReference("Products")
                .push().getKey();

        Product product = new Product();
        product.setId(productId);
        product.setName(((EditText)findViewById(R.id.sell_product_name_text)).getText().toString().trim());
        product.setDescription(((EditText)findViewById(R.id.sell_product_description_text)).getText().toString().trim());
        product.setPrice(Double.parseDouble(((EditText)findViewById(R.id.sell_product_price_text)).getText().toString().trim()));
        product.setVendor(userApi.getEmail());
        product.setRemainingCount(Integer.parseInt(((EditText)findViewById(R.id.sell_product_quantity_text)).getText().toString().trim()));
        product.setImages("StoragePath");
        product.setModel(((EditText)findViewById(R.id.sell_product_model_text)).getText().toString().trim());
        product.setSize(((EditText)findViewById(R.id.sell_product_size_text)).getText().toString().trim());
        product.setBrand(((EditText)findViewById(R.id.sell_product_brand_text)).getText().toString().trim());

        int buttonId = ((RadioGroup)findViewById(R.id.sell_products_category_btng)).getCheckedRadioButtonId();
        product.setCategory(((RadioButton)findViewById(buttonId)).getText().toString().trim());

        product.setColour(((EditText)findViewById(R.id.sell_product_colour_text)).getText().toString().trim());
        product.setReleasedTime(System.currentTimeMillis());
        product.setUpdatedTime(System.currentTimeMillis());
        product.setShippable(true);
        product.setDiscount(0);
        product.setFeaturedImage("StoragePath");
        product.setSoldCount(0);

        db.getReference("Products")
                .child(productId)
                .setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        double wallet = userApi.getCredits();
                        wallet = wallet - price;
                        userApi.setCredits(wallet);
                        db.getReference("Users")
                                .child(userApi.getId())
                                .child("credits")
                                .setValue(wallet);
                        storageReference.child("ProductImages")
                                .child(productId)
                                .child("FeaturedImage")
                                .putFile(featuredImageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference.child("ProductImages")
                                                .child(productId)
                                                .child("FeaturedImage")
                                                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                db.getReference("Products")
                                                        .child(productId)
                                                        .child("featuredImage")
                                                        .setValue(String.valueOf(uri));
                                                if(img1Uri != null){
                                                    storageReference.child("ProductImages")
                                                            .child(productId)
                                                            .child("img1")
                                                            .putFile(img1Uri);
                                                }
                                                if(img2Uri != null){
                                                    storageReference.child("ProductImages")
                                                            .child(productId)
                                                            .child("img2")
                                                            .putFile(img2Uri);
                                                }
                                                if(img3Uri != null){
                                                    storageReference.child("ProductImages")
                                                            .child(productId)
                                                            .child("img3")
                                                            .putFile(img3Uri);
                                                }
                                                pd.dismiss();
                                                Toast.makeText(SellProduct.this,"Product added!",Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(SellProduct.this,"Error in saving product image.",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(SellProduct.this,"Unable to upload featured image.",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(SellProduct.this,"Unable to add product.",Toast.LENGTH_LONG).show();
            }
        });

    }

}
