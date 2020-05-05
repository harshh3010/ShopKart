package com.codebee.shopkart.Ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebee.shopkart.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private ArrayList<String> categories;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        categoryRecyclerView = view.findViewById(R.id.home_fragment_recycler_view);

        loadCategories();

        return view;
    }

    private void loadCategories(){
        categories = new ArrayList<>();
        categories.add("All Products");
        categories.add("Electronics");
        categories.add("Fashion");
        categories.add("Sports and Fitness");
        categories.add("Books and Study");
        categories.add("Toys and Kids");
        categories.add("Home Appliances");

        adapter = new HomeDisplayAdapter(categories);
        categoryRecyclerView.setAdapter(adapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
