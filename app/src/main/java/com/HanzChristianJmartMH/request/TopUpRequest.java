package com.HanzChristianJmartMH.request;

import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk melakukan request terhadap Topup
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class TopUpRequest extends StringRequest {
    private static final String URL_FORMAT = "http://10.0.2.2:6969/account/%d/topUp";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request yang digunakan untuk melakukan Topup
     * dan melakukan POST pada bagian backend dengan parameter
     * @param id merupakan id account yang akan ditopup
     * @param balance merupakan banyaknya saldo pada id bersangkutan
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     */
    public TopUpRequest(int id,double balance, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL_FORMAT, id), listener, errorListener);
        params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("balance", String.valueOf(balance));
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){
        return params;
    }
}
