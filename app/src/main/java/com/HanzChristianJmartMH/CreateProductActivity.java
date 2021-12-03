package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateProductActivity extends AppCompatActivity {

    private Spinner categoryspinner;
    private Spinner shipmentspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        categoryspinner = findViewById(R.id.spinnercategory);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryspinner.setAdapter(adapter1);

        shipmentspinner = findViewById(R.id.spinnerShipment);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.shipmentPlans));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shipmentspinner.setAdapter(adapter2);
    }

}