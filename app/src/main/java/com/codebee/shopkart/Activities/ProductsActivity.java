package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;
import com.codebee.shopkart.Ui.ProductAdapter;
import com.codebee.shopkart.Ui.ProductAdapter2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private String category;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Product> productArrayList;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.products_recycler_view);

        category = getIntent().getStringExtra("category");
        ((TextView)findViewById(R.id.products_category_text)).setText(category);

        findViewById(R.id.products_back_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadProducts();
    }

    private void loadProducts(){
        productArrayList = new ArrayList<>();
        db.getReference("Products")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            if(ds.child("category").getValue().toString().equals(category)){
                                productArrayList.add(ds.getValue(Product.class));
                            }
                            if(category.equals("All Products")){
                                productArrayList.add(ds.getValue(Product.class));
                            }
                        }
                        adapter = new ProductAdapter2(productArrayList);
                        recyclerView.setLayoutManager(new GridLayoutManager(ProductsActivity.this,2));
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
