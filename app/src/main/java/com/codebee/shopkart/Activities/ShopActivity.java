package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codebee.shopkart.R;
import com.codebee.shopkart.Ui.BooksFragment;
import com.codebee.shopkart.Ui.ElectronicsFragment;
import com.codebee.shopkart.Ui.FashionFragment;
import com.codebee.shopkart.Ui.HomeAppliancesFragment;
import com.codebee.shopkart.Ui.HomeFragment;
import com.codebee.shopkart.Ui.SportsFragment;
import com.codebee.shopkart.Ui.ToysFragment;
import com.google.android.material.navigation.NavigationView;

public class ShopActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        drawerLayout = findViewById(R.id.shop_drawer_layout);
        NavigationView navigationView = findViewById(R.id.shop_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.shop_menu_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.navigation_home);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new HomeFragment()).commit();
                break;
            case R.id.navigation_electronics:
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new ElectronicsFragment()).commit();
                break;
            case R.id.navigation_fashion :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new FashionFragment()).commit();
                break;
            case R.id.navigation_sports :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new SportsFragment()).commit();
                break;
            case R.id.navigation_books :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new BooksFragment()).commit();
                break;
            case R.id.navigation_toys :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new ToysFragment()).commit();
                break;
            case R.id.navigation_appliances :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new HomeAppliancesFragment()).commit();
                break;
            case R.id.navigation_share :
                Toast.makeText(ShopActivity.this,"Share",Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }
}
