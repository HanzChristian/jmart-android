package com.HanzChristianJmartMH.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk memberikan request Cancel pada Payment
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class CancelPaymentRequest extends StringRequest {
    public static final String CANCEL_URL = "http://10.0.2.2:6969/payment/%d/cancel";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request yang melakukan POST pada backend untuk cancel berdasarkan parameter
     * @param id merupakan id payment
     * @param listener merupakan listener jika terkoneksi dengan backendnya
     * @param errorListener merupakan error listener jika gagal koneksi ke backend
     */
    public CancelPaymentRequest(Response.Listener<String> listener , int id, Response.ErrorListener errorListener) {
        super(Method.POST, String.format(CANCEL_URL, id), listener, errorListener);
        params = new HashMap<>();
        params.put("id", String.valueOf(id));
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){ return params; }
}
