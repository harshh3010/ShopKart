package com.codebee.shopkart.Ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebee.shopkart.Activities.ProductViewActivity;
import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolderClass> {

    private ArrayList<Product> myArr;
    private Context context;

    public ProductAdapter(ArrayList<Product> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_product,parent,false);
        context = parent.getContext();
        return  new ViewHolderClass(view);
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

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        public TextView name_txt,price_txt;
        public ImageView product_img;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.item_product_name_text);
            price_txt = itemView.findViewById(R.id.item_product_price_text);
            product_img = itemView.findViewById(R.id.item_product_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context,ProductViewActivity.class);
                    i.putExtra("product",(Product) myArr.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }
    }

}
