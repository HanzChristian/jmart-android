package com.HanzChristianJmartMH.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.HanzChristianJmartMH.model.ProductCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class untuk memberikan request terhadap backend terhadap Product yang akan dibentuk
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class CreateProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/product/create";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request untuk pembentukan Product ke aplikasi Jmart
     * dan melakukan POST kepada backendnya dengan parameter
     * @param accountId id Account yang bersangkutan
     * @param name merupakan nama Product
     * @param weight merupakan berat Product
     * @param conditionUsed merupakan kondisi dari Product
     * @param price merupakan harga dari Product
     * @param discount merupakan diskon yang diimplementasikan pada Product
     * @param category merupakan jenis kategori Product
     * @param shipmentPlans merupakan jenis shipment Product
     * @param listener merupakan listener jika terkoneksi dengan backendnya
     * @param errorListener merupakan error listener jika gagal koneksi ke backend
     */
    public CreateProductRequest(int accountId, String name, int weight, boolean conditionUsed, double price, double discount, ProductCategory category, byte shipmentPlans, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", String.valueOf(accountId));
        params.put("name", name);
        params.put("weight", String.valueOf(weight));
        params.put("conditionUsed", String.valueOf(conditionUsed));
        params.put("price", String.valueOf(price));
        params.put("discount", String.valueOf(discount));
        params.put("category", String.valueOf(category));
        params.put("shipmentPlans", String.valueOf(shipmentPlans));
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){
        return params;
    }
}
