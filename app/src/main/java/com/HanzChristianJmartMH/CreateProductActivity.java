package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.HanzChristianJmartMH.model.Account;
import com.HanzChristianJmartMH.model.Product;
import com.HanzChristianJmartMH.model.ProductCategory;
import com.HanzChristianJmartMH.request.CreateProductRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Merupakan Class yang merepresentasikan Activity untuk pembentukan product
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class CreateProductActivity extends AppCompatActivity {

    private EditText editNameProduct,editWeightProduct,editPriceProduct,editDiscountProduct;
    private RadioButton radiobuttonNew,radiobuttonUsed;
    private Spinner categorySpinner,shipmentSpinner;
    private Button createProductButton, cancelButton;
    private Product product;
    private static final Gson gson = new Gson();


    /**
     * Merupakan method yang digunakan untuk melakukan inisialisasi
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        //EditText
        editNameProduct = findViewById(R.id.editNameProduct);
        editWeightProduct = findViewById(R.id.editWeightProduct);
        editPriceProduct = findViewById(R.id.editPriceProduct);
        editDiscountProduct = findViewById(R.id.editDiscountProduct);

        //Radio Button
        radiobuttonNew = findViewById(R.id.radiobuttonNew);
        radiobuttonUsed = findViewById(R.id.radiobuttonUsed);

        //Button
        createProductButton = findViewById(R.id.createProductButton);
        cancelButton = findViewById(R.id.cancelButton);

        List<String> productCategoryList = new ArrayList<>();
        for(ProductCategory category : ProductCategory.values()){
            productCategoryList.add(category.toString());
        }

        //Spinner
        categorySpinner = findViewById(R.id.spinnercategory);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productCategoryList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoriesAdapter);

        shipmentSpinner = findViewById(R.id.spinnerShipment);
        ArrayAdapter<String> shipmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.shipmentPlans));
        shipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shipmentSpinner.setAdapter(shipmentAdapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateProductActivity.this,MainActivity.class);
                Toast.makeText(getApplicationContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        //Ketika button CreateProduct di klik
        createProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = LoginActivity.getLoggedAccount();
                String name = editNameProduct.getText().toString();
                String weight = editWeightProduct.getText().toString();
                String price = editPriceProduct.getText().toString();
                String discount = editDiscountProduct.getText().toString();

                int productWeight = Integer.valueOf(weight);
                double productPrice = Double.valueOf(price);
                double productDiscount = Double.valueOf(discount);
                boolean conditionUsed = checkButton(radiobuttonNew, radiobuttonUsed);
                ProductCategory category = getProductCategory(categorySpinner);
                byte shipmentPlan = getShipmentPlan(shipmentSpinner);

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(CreateProductActivity.this,"Create Product Success!",Toast.LENGTH_SHORT).show();
                            }
                            product = gson.fromJson(response,Product.class);
                            Intent intent = new Intent(CreateProductActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateProductActivity.this,"Create Product Failed due to Connection!",Toast.LENGTH_SHORT).show();
                        Log.d("ERROR",error.toString());
                    }
                };
                CreateProductRequest createProductRequest = new CreateProductRequest(account.id, name, productWeight, conditionUsed, productPrice, productDiscount, category, shipmentPlan, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                queue.add(createProductRequest);
            }
        });
    }

    /**
     * Merupakan method yang digunakan untuk melakukan pengecekkan kondisi boolean radiobutton
     * @return berupa boolean true jika new, dan false jika used
     */
    //Pengecekkan Button
    public boolean checkButton(RadioButton radiobuttonNew, RadioButton radiobuttonUsed){
        boolean isUsed = false;
        if(radiobuttonNew.isChecked()){
            isUsed = true;
        }
        else if(radiobuttonUsed.isChecked()){
            isUsed = false;
        }
        return isUsed;
    }

    /**
     * Merupakan method yang digunakan untuk mengambil keseluruhan list dari product category yang ada pada array
     * @param categorySpinner merupakan spinner untuk memilih kategorinya
     * @return list product category
     */
    //Mengambil data product category dengan perbandingan
    public ProductCategory getProductCategory(Spinner categorySpinner){
        //inisiasi awal
        ProductCategory category = ProductCategory.BOOK;
        String productCategory = categorySpinner.getSelectedItem().toString();
        for(ProductCategory prodCategory : ProductCategory.values()){
            if(prodCategory.toString().equals(productCategory)){
                category = prodCategory;
            }
        }
        return category;
    }

    /**
     * Merupakan method yang digunakan untuk mengambil keseluruhan list dari product shipment
     * @param shipmentSpinner merupakan spinner untuk memilih Shipment
     * @return list Shipment dalam bentuk byte
     */
    public byte getShipmentPlan(Spinner shipmentSpinner){
        String shipmentPlan = shipmentSpinner.getSelectedItem().toString();
        if(shipmentPlan.equals("INSTANT")){
            return 1;
        }
        else if(shipmentPlan.equals("SAME DAY")){
            return 2;
        }
        else if(shipmentPlan.equals("NEXT DAY")){
            return 4;
        }
        else if(shipmentPlan.equals("REGULER")){
            return 8;
        }
        else if(shipmentPlan.equals("KARGO")){
            return 16;
        }
        else {
            return 0;
        }
    }
}