package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.HanzChristianJmartMH.model.Account;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.title);
        Account account = LoginActivity.getLoggedAccount();
        tv.setText(account.name);
    }
}