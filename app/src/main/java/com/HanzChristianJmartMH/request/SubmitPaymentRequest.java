package com.HanzChristianJmartMH.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk memberikan request Submit pada Payment
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class SubmitPaymentRequest extends StringRequest {
    private static final String SUBMIT_URL = "http://10.0.2.2:6969/payment/%d/submit";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request yang melakukan POST pada backend untuk submit berdasarkan parameter
     * @param id merupakan id payment
     * @param receipt merupakan receipt yang akan diberikan
     * @param listener merupakan listener jika terkoneksi dengan backendnya
     * @param errorListener merupakan error listener jika gagal koneksi ke backend
     */
    public SubmitPaymentRequest(int id, String receipt, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format(SUBMIT_URL, id), listener, errorListener);
        params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("receipt", receipt);
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){ return params; }

}
