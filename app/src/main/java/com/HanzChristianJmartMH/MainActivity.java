package com.HanzChristianJmartMH;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.HanzChristianJmartMH.model.Account;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.title);
        Account account = LoginActivity.getLoggedAccount();
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

//        tv.setText("Hello, " + account.name + " !");
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
            return true;
        }
        else if (id == R.id.person){
            Toast.makeText(MainActivity.this, "Person Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}