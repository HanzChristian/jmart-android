package com.HanzChristianJmartMH;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.HanzChristianJmartMH.model.Account;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager); //refrence viewPager pada Activity
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager()); //set terhadap controller yang dibuat
        viewPager.setAdapter(viewPageAdapter); // memasukkan viewPager sesuai kondisi controller
        viewPager.setCurrentItem(0); //mengatur posisi awal

        //Untuk menampilkan antar 2 tab (products & filter)
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Account account = LoginActivity.getLoggedAccount();

        //Untuk menampilkan toolbar pada menu_main.xml
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Toast.makeText(MainActivity.this, "Search Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        else if ( id == R.id.addbox){
            Toast.makeText(MainActivity.this, "Add Box Clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this,CreateProductActivity.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.person){
            Toast.makeText(MainActivity.this, "Person Clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this,AboutMeActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}