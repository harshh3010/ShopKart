package com.codebee.shopkart.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter2 extends RecyclerView.Adapter<ProductAdapter2.ViewHolderClass> {

    private ArrayList<Product> myArr;

    public ProductAdapter2(ArrayList<Product> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_product_2,parent,false);
        return  new ProductAdapter2.ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.name_txt.setText(myArr.get(position).getName());
        holder.price_txt.setText("Rs. " + myArr.get(position).getPrice());
        Picasso.get().load(myArr.get(position).getFeaturedImage()).into(holder.product_img);
    }

    @Override
    public int getItemCount() {
        return myArr.size();
    }

    public class  ViewHolderClass extends RecyclerView.ViewHolder{
        public TextView name_txt,price_txt;
        public ImageView product_img;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.item_product_name_text_2);
            price_txt = itemView.findViewById(R.id.item_product_price_text_2);
            product_img = itemView.findViewById(R.id.item_product_image_2);
        }
    }

}
