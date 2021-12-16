package com.HanzChristianJmartMH.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;

/**
 * Merupakan Class yang digunakan untuk memberikan request terhadap Register Account yang dilakukan
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class RegisterRequest extends StringRequest{
    private static final String URL = "http://10.0.2.2:6969/account/register";
    private final Map<String,String> params;

    /**
     * Merupakan Constructor request yang digunakan untuk melakukan register account
     * dan melakukan POST pada bagian backend dengan parameter
     * @param name Nama dari account yang dibuat
     * @param email email dari account yang dibuat
     * @param password password dari account yang dibuat
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     */
    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST,URL,listener,errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String,String> getParams(){
        return params;
    }
}
