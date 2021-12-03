package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AboutMeActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        button1 = findViewById(R.id.registerstore1);
        button2 = findViewById(R.id.registerstore2);
        button3 = findViewById(R.id.cancelregister);
        linearLayout1 = findViewById(R.id.linearlayout1);
        linearLayout2 = findViewById(R.id.linearlayout2);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            button1.setVisibility(v.GONE);
            linearLayout1.setVisibility(v.VISIBLE);
                Toast.makeText(getApplicationContext(),"Register Store di click",Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(v.GONE);
                linearLayout2.setVisibility(v.VISIBLE);
                Toast.makeText(getApplicationContext(),"Register di click",Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(v.GONE);
                button1.setVisibility(v.VISIBLE);
                Toast.makeText(getApplicationContext(),"Register di click",Toast.LENGTH_SHORT).show();
            }
        });
    }

}