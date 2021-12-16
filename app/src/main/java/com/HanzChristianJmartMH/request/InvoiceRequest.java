package com.HanzChristianJmartMH.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk memberikan request terhadap Invoice yang dibentuk
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class InvoiceRequest extends StringRequest {
    private static final String URL_FORMAT = "http://10.0.2.2:6969/payment/%s?%s=%s";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request yang mengambil list Payment dan Invoice
     * dan melakukan GET kepada backendnya dengan parameter
     * @param id merupakan id dari Store maupun Account
     * @param byAccount Pengecekkan boolean apakah invoice dilakukan oleh user Account ataupun user Store
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     */
    public InvoiceRequest(int id, boolean byAccount, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, byAccount ? "getByAccountId" : "getByStoreId", byAccount ? "buyerId" : "storeId", id), listener, errorListener);
        params = new HashMap<>();
        params.put(byAccount ? "buyerId" : "storeId", String.valueOf(id));
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){
        return params;
    }
}
