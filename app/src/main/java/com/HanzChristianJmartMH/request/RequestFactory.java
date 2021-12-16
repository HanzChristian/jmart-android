package com.HanzChristianJmartMH.request;

import android.location.GnssAntennaInfo;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk mendapatkan list object berdasarkan Id ataupun Page
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class RequestFactory {
    public static final String URL_FORMAT_ID = "http://10.0.2.2:6969/%s/%d";
    public static final String URL_FORMAT_PAGE = "http://10.0.2.2:6969/%s/page?page=%s&pageSize=%s";

    /**
     * Merupakan Method yang dibentuk untuk mendapatkan list object berdasarkan id dengan parameter
     * @param parentURI request parentURI
     * @param id merupakan id dari object
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     * @return berupa list object berdasarkan id dan parentURI pada parameter
     */

    public static StringRequest getById(String parentURI, int id, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String url = String.format(URL_FORMAT_ID, parentURI, id);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     * Merupakan Method yang dibentuk untuk mendapatkan list object berdasarkan page dengan parameter
     * @param parentURI request parentURI
     * @param page merupakan index page dari object
     * @param pageSize merupakan banyaknya page
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     * @return berupa list object berdasarkan page dan parentURI pada parameter
     */
    public static StringRequest getPage(String parentURI, int page, int pageSize, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String url = String.format(URL_FORMAT_PAGE, parentURI, page, pageSize);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
}
