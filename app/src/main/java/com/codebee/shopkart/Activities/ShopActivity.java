package com.codebee.shopkart.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codebee.shopkart.R;
import com.codebee.shopkart.Ui.BooksFragment;
import com.codebee.shopkart.Ui.ElectronicsFragment;
import com.codebee.shopkart.Ui.FashionFragment;
import com.codebee.shopkart.Ui.HomeAppliancesFragment;
import com.codebee.shopkart.Ui.HomeFragment;
import com.codebee.shopkart.Ui.SportsFragment;
import com.codebee.shopkart.Ui.ToysFragment;
import com.codebee.shopkart.Util.UserApi;
import com.google.android.material.navigation.NavigationView;

public class ShopActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView name_txt,email_txt;
    private UserApi userApi = UserApi.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        drawerLayout = findViewById(R.id.shop_drawer_layout);
        NavigationView navigationView = findViewById(R.id.shop_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        name_txt = view.findViewById(R.id.nav_header_name_text);
        email_txt = view.findViewById(R.id.nav_header_email_text);

        name_txt.setText(userApi.getName());
        email_txt.setText(userApi.getEmail());

        findViewById(R.id.shop_menu_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        findViewById(R.id.shop_my_cart_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopActivity.this,CartActivity.class));
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
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.navigation_electronics:
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new ElectronicsFragment()).commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.navigation_fashion :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new FashionFragment()).commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.navigation_sports :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new SportsFragment()).commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.navigation_books :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new BooksFragment()).commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.navigation_toys :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new ToysFragment()).commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.navigation_appliances :
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_fragment_container, new HomeAppliancesFragment()).commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.navigation_account :
                startActivityForResult(new Intent(ShopActivity.this,AccountActivity.class),1);
                break;
            case R.id.navigation_share :
                Toast.makeText(ShopActivity.this,"Share",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_sell :
                startActivity(new Intent(ShopActivity.this,SellProduct.class));
                break;
            case R.id.navigation_cart :
                startActivity(new Intent(ShopActivity.this,CartActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            startActivity(new Intent(ShopActivity.this, LoginActivity.class));
            finish();
        }
    }
}
