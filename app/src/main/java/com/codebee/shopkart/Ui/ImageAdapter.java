package com.codebee.shopkart.Ui;

import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebee.shopkart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolderClass>{

    private ArrayList<String> myArr;

    public ImageAdapter(ArrayList<String> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_product_image,parent,false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        Picasso.get().load(myArr.get(position)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return myArr.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        public ImageView img;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_view_product_image_view) ;
        }
    }
}
