package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;
import com.codebee.shopkart.Ui.ImageAdapter;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProductViewActivity extends AppCompatActivity {

    private Product product;
    private RecyclerView recyclerView;
    private ArrayList<String> myArr;
    private RecyclerView.Adapter adapter;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("ProductImages");
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private UserApi userApi = UserApi.getInstance();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        recyclerView = findViewById(R.id.product_images_recycler_view);

        product = (Product) getIntent().getSerializableExtra("product");

        loadProductData();

        findViewById(R.id.product_view_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.product_view_cart_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void loadProductData() {

        myArr = new ArrayList<String>();
        myArr.add(product.getFeaturedImage());
        storageReference.child("ProductImages")
                .child(product.getId())
                .child("img1")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                myArr.add(String.valueOf(uri));
                adapter.notifyDataSetChanged();
            }
        });
        storageReference.child("ProductImages")
                .child(product.getId())
                .child("img2")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                myArr.add(String.valueOf(uri));
                adapter.notifyDataSetChanged();
            }
        });
        storageReference.child("ProductImages")
                .child(product.getId())
                .child("img3")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                myArr.add(String.valueOf(uri));
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new ImageAdapter(myArr);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                ProductViewActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));

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

    private void addToCart(){

        pd = new ProgressDialog(ProductViewActivity.this,R.style.MyAlertDialogStyle);
        pd.setMessage("Please wait...");
        pd.show();

        Map<String,String> data = new HashMap<>();
        data.put("productId",product.getId());

        String cart_id = db.getReference("Carts")
                .child(userApi.getId())
                .push().getKey();

        db.getReference("Carts")
                .child(userApi.getId())
                .child(cart_id)
                .setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(ProductViewActivity.this,"Product added to your shopping cart.",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(ProductViewActivity.this,"Unable to add product to cart!",Toast.LENGTH_LONG).show();
            }
        });
    }

}
