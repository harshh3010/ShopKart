package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.material.snackbar.Snackbar;

public class SellProduct extends AppCompatActivity {

    UserApi userApi = UserApi.getInstance();
    public static final int PICK_FEATURED_IMAGE = 1, PICK_IMG1 = 2, PICK_IMG2 = 3, PICK_IMG3 = 4;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private Uri featuredImageUri,img1Uri,img2Uri,img3Uri;
    private static int reqCode;

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
                addProduct();
            }
        });

        findViewById(R.id.sell_products_featured_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);
            }
        });

        findViewById(R.id.sell_products_img1_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);
            }
        });

        findViewById(R.id.sell_products_img2_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);
            }
        });

        findViewById(R.id.sell_products_img3_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);
            }
        });
    }

    private void addProduct() {
        if (!((EditText) findViewById(R.id.sell_product_name_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_description_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_model_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_price_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_quantity_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_brand_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_size_text)).getText().toString().trim().isEmpty()
                && !((EditText) findViewById(R.id.sell_product_colour_text)).getText().toString().trim().isEmpty()
                && featuredImageUri != null) {
            // TODO : add code to update product data
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
            if (featuredImageUri == null) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Please select a featured image for the product.", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(SellProduct.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SellProduct.this,
                    new String[]{permission},
                    requestCode);
        } else {
            pickImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(SellProduct.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), reqCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_FEATURED_IMAGE) {
            if (featuredImageUri != null) {
                featuredImageUri = data.getData();
                ((ImageView) findViewById(R.id.sell_products_featured_image)).setImageURI(featuredImageUri);
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMG1) {
            if (img1Uri != null) {
                img1Uri = data.getData();
                ((ImageView) findViewById(R.id.sell_products_img1_image)).setImageURI(img1Uri);
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMG2) {
            if (img2Uri != null) {
                img2Uri = data.getData();
                ((ImageView) findViewById(R.id.sell_products_img2_image)).setImageURI(img2Uri);
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMG3) {
            if (img3Uri != null) {
                img3Uri = data.getData();
                ((ImageView) findViewById(R.id.sell_products_img3_image)).setImageURI(img3Uri);
            }
        }
    }
}
