package com.codebee.shopkart.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class HomeDisplayAdapter extends RecyclerView.Adapter<HomeDisplayAdapter.ViewHolderClass> {

    private ArrayList<String> myArr;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private RecyclerView.Adapter adapter;
    private Context context;
    private ArrayList<Product> products;
    private LinearLayoutManager HorizontalLayout;

    public HomeDisplayAdapter(ArrayList<String> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_home_fragment, parent, false);
        context = parent.getContext();
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.category_txt.setText(myArr.get(position));
        displayProduct(holder);
    }

    @Override
    public int getItemCount() {
        return myArr.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        public TextView category_txt, no_products_txt;
        public RecyclerView productsRecyclerView;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            category_txt = itemView.findViewById(R.id.item_home_category_text);
            no_products_txt = itemView.findViewById(R.id.item_home_no_products_text);
            productsRecyclerView = itemView.findViewById(R.id.item_home_products_recycler_view);
        }
    }

    private void displayProduct(final ViewHolderClass holder) {

        db.getReference("Products")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        products = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            products.add(ds.getValue(Product.class));
                        }
                        final ArrayList<Product> filteredProducts = new ArrayList<>();
                        for (int i = 0; i < products.size(); i++) {
                            if (products.get(i).getCategory().equals(myArr.get(holder.getAdapterPosition()))) {
                                filteredProducts.add(products.get(i));
                            }
                        }
                        if(filteredProducts.isEmpty()){
                            holder.no_products_txt.setVisibility(View.VISIBLE);
                        }else{
                            holder.no_products_txt.setVisibility(View.GONE);
                        }
                        adapter = new ProductAdapter(filteredProducts);
                        holder.productsRecyclerView.setLayoutManager(new LinearLayoutManager(
                                context,
                                LinearLayoutManager.HORIZONTAL,
                                false));
                        holder.productsRecyclerView.setAdapter(adapter);

                        if(myArr.get(holder.getAdapterPosition()).equals("All Products")){
                            if(products.isEmpty()){
                                holder.no_products_txt.setVisibility(View.VISIBLE);
                            }else{
                                holder.no_products_txt.setVisibility(View.GONE);
                            }
                            adapter = new ProductAdapter(products);
                            holder.productsRecyclerView.setAdapter(adapter);
                            holder.productsRecyclerView.setLayoutManager(new LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
