package com.codebee.shopkart.Ui;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.codebee.shopkart.Activities.AccountActivity;
import com.codebee.shopkart.Model.Product;
import com.codebee.shopkart.R;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolderClass>{

    private ArrayList<Product> myArr;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private Context context;
    private UserApi userApi = UserApi.getInstance();

    public CartAdapter(ArrayList<Product> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_cart_product,parent,false);
        context = parent.getContext();
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClass holder, int position) {
        Picasso.get().load(myArr.get(position).getFeaturedImage()).into(holder.img);
        holder.name_txt.setText(myArr.get(position).getName());
        holder.name_txt.setText("Rs. " + myArr.get(position).getPrice());

        holder.remove_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyAlertDialogStyle);
                builder.setTitle("Remove product");
                builder.setMessage("Do you really wish to remove this product from your cart?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        removeProduct(holder);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        holder.buy_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Code to purchase the product
            }
        });
    }

    private void removeProduct(final ViewHolderClass holder) {
        db.getReference("Carts")
                .child(userApi.getId())
                .child(myArr.get(holder.getAdapterPosition()).getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Product removed successfully!",Toast.LENGTH_LONG).show();
                        myArr.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(),myArr.size());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Unable to remove product!",Toast.LENGTH_LONG).show();
            }
        });

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
