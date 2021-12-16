package com.HanzChristianJmartMH.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk memberikan request terhadap Payment yang dilakukan
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class PaymentRequest extends StringRequest {
    private static final String URL_FORMAT = "http://10.0.2.2:6969/payment/create";
    public static final String URL_SUBMIT = "http://10.0.2.2:6969/payment/%d/submit";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request yang digunakan untuk memberikan verifikasi terhadap payment
     * dan melakukan POST pada bagian backend untuk verifikasinya dengan parameter
     * @param buyerId merupakan id yang melakukan Payment
     * @param productId merupakan id dari Product yang dibeli
     * @param productCount merupakan banyaknya (quantity) Product
     * @param shipmentAddress merupakan address pembeli untuk Product bersangkutan
     * @param shipmentPlan merupakan jenis shipment yang dilakukan pada Prodcut
     * @param storeId merupakan Id dari store yang menerima
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     */
    public PaymentRequest(int buyerId, int productId, int productCount, String shipmentAddress, byte shipmentPlan, int storeId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL_FORMAT, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", String.valueOf(buyerId));
        params.put("productId", String.valueOf(productId));
        params.put("productCount", String.valueOf(productCount));
        params.put("shipmentAddress", shipmentAddress);
        params.put("shipmentPlan", String.valueOf(shipmentPlan));
        params.put("storeId", String.valueOf(storeId));
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){
        return params;
    }
}

