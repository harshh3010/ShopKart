package com.codebee.shopkart.Ui;

import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        ImageView imageView = new ImageView(parent.getContext());
        return new ViewHolderClass(imageView);
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
            img = (ImageView) itemView ;
        }
    }
}
