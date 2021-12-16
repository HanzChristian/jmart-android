package com.HanzChristianJmartMH.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk memberikan request terhadap Register store yang dilakukan
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class RegisterStoreRequest extends StringRequest {
    private static final String URL_FORMAT = "http://10.0.2.2:6969/account/%d/registerStore";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request yang digunakan untuk melakukan register account
     * dan melakukan POST pada bagian backend dengan parameter
     * @param id merupakan id Storenya
     * @param name nama dari store yang dibuat
     * @param address address dari store yang dibuat
     * @param phoneNumber nomor telpon dari store yang dibuat
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     */
    public RegisterStoreRequest(int id, String name, String address, String phoneNumber, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL_FORMAT, id), listener, errorListener);
        params = new HashMap<>();
        params.put("id",String.valueOf(id));
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){
        return params;
    }
}
