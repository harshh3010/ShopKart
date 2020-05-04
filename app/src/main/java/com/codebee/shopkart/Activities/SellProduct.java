package com.codebee.shopkart.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class SellProduct extends AppCompatActivity {

    public static final int PICK_FEATURED_IMAGE = 1;
    public static final int PICK_IMG1 = 2;
    public static final int PICK_IMG2 = 3;
    public static final int PICK_IMG3 = 4;
    private Uri featuredImageUri, img1Uri,img2Uri,img3Uri;

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
            addProduct();
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

    private void addProduct(){

    }

}
