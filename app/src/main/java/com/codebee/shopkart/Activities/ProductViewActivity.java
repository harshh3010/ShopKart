package com.codebee.shopkart.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;

import java.util.Calendar;

public class ProductViewActivity extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        product = (Product) getIntent().getSerializableExtra("product");

        loadProductData();

        findViewById(R.id.product_view_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadProductData() {
        ((TextView)findViewById(R.id.product_view_head_text)).setText(product.getName());
        ((TextView)findViewById(R.id.product_view_name_text)).setText(product.getName());
        ((TextView)findViewById(R.id.product_view_model_text)).setText("Model : " + product.getModel());
        ((TextView)findViewById(R.id.product_view_brand_text)).setText("Brand : " + product.getBrand());
        ((TextView)findViewById(R.id.product_view_colour_text)).setText("Colour : " + product.getColour());
        ((TextView)findViewById(R.id.product_view_price_text)).setText("Rs. " + product.getPrice());
        ((TextView)findViewById(R.id.product_view_description_text)).setText("About the product :\n" + product.getDescription());
        ((TextView)findViewById(R.id.product_view_size_text)).setText("Size : " + product.getSize());
        ((TextView)findViewById(R.id.product_view_remaining_text)).setText(product.getRemainingCount() + " products left.");

        if(product.isShippable()){
            ((TextView)findViewById(R.id.product_view_shippable_text)).setText("Shippable");
        }else {
            ((TextView)findViewById(R.id.product_view_shippable_text)).setText("Not Shippable");
        }

        ((TextView)findViewById(R.id.product_view_vendor_text)).setText("A product by " + product.getVendor());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(product.getReleasedTime());
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        ((TextView)findViewById(R.id.product_view_release_text)).setText("Released on " + mDay + "-" + mMonth + "-" + mYear);

        calendar.setTimeInMillis(product.getUpdatedTime());
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        ((TextView)findViewById(R.id.product_view_update_text)).setText("Updated on " + mDay + "-" + mMonth + "-" + mYear);
    }
}
