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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolderClass>{

    private ArrayList<Product> myArr;

    public CartAdapter(ArrayList<Product> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_cart_product,parent,false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        Picasso.get().load(myArr.get(position).getFeaturedImage()).into(holder.img);
        holder.name_txt.setText(myArr.get(position).getName());
        holder.name_txt.setText("Rs. " + myArr.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return myArr.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView name_txt,price_txt,remove_txt,buy_txt;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_product_image);
            name_txt = itemView.findViewById(R.id.cart_product_name_text);
            price_txt = itemView.findViewById(R.id.cart_product_price_text);
            remove_txt = itemView.findViewById(R.id.cart_remove_text);
            buy_txt = itemView.findViewById(R.id.cart_buy_text);
        }
    }

}
