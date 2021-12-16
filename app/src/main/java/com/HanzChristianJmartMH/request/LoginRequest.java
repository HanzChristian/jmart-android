package com.HanzChristianJmartMH.request;

import com.HanzChristianJmartMH.LoginActivity;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk memberikan request terhadap Login yang dilakukan
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/account/login";
    private final Map<String,String> params;

    /**
     * Merupakan Constructor request yang digunakan untuk memberikan verifikasi loginnya
     * dan melakukan POST pada bagian backend untuk verifikasinya dengan parameter
     * @param email merupakan email dari Account
     * @param password merupakan password dari Account
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     */
    public LoginRequest(String email, String password, LoginActivity listener, LoginActivity errorListener){
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
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
