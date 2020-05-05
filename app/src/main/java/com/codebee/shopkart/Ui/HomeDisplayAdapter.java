package com.codebee.shopkart.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebee.shopkart.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeDisplayAdapter extends RecyclerView.Adapter<HomeDisplayAdapter.ViewHolderClass> {

    private ArrayList<String> myArr;

    public HomeDisplayAdapter(ArrayList<String> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_home_fragment,parent,false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.category_txt.setText(myArr.get(position));

    }

    @Override
    public int getItemCount() {
        return myArr.size();
    }

    public  class  ViewHolderClass extends RecyclerView.ViewHolder{
        public TextView category_txt,no_products_txt;
        public RecyclerView productsRecyclerView;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            category_txt = itemView.findViewById(R.id.item_home_category_text);
            no_products_txt = itemView.findViewById(R.id.item_home_no_products_text);
            productsRecyclerView = itemView.findViewById(R.id.item_home_products_recycler_view);
        }
    }

}
