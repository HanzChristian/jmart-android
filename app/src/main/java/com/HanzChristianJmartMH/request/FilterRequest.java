package com.HanzChristianJmartMH.request;

import com.HanzChristianJmartMH.model.ProductCategory;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Merupakan Class yang digunakan untuk memberikan request terhadap filter yang dilakukan
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class FilterRequest extends StringRequest {
    private static final String URL_FORMAT = "http://10.0.2.2:6969/product/getFiltered?page=%s&pageSize=%s&accountId=%s&search=%s&minPrice=%s&maxPrice=%s&category=%s";
    private final Map<String, String> params;

    /**
     * Merupakan Constructor request yang merepresentasikan filter pada aplikasi Jmart
     * dan melakukan GET kepada backendnya dengan parameter
     * @param page merupakan index dari pagenya
     * @param pageSize merupakan banyaknya page
     * @param accountId merupakan id dari Account bersangkutan
     * @param search merupakan string untuk pembandingan dengan yang dimasukkan oleh usernya
     * @param minPrice merupakan harga minimal yang dimasukkan user
     * @param maxPrice merupakan harga maksimal yang dimasukkan user
     * @param category merupakan jenis kategori filter
     * @param listener merupakan listener yang menandakan bahwa hubungan ke backend berhasil
     * @param errorListener merupakan errorlistener yang menandakan koneksi ke backend gagal
     */
    public FilterRequest(int page, int pageSize, int accountId, String search, int minPrice, int maxPrice, ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, pageSize, accountId, search, minPrice, maxPrice, category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", String.valueOf(minPrice));
        params.put("maxPrice", String.valueOf(maxPrice));
        params.put("category", category.toString());
    }

    /**
     * Merupakan Constructor yang memiliki kondisi dimana user tidak memilih pageSize untuk filternya
     */
    //Ketika pageSize tidak terisi
    public FilterRequest(int page, int accountId, String search, int minPrice, int maxPrice, ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, "", accountId, search, minPrice, maxPrice, category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", "");
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", String.valueOf(minPrice));
        params.put("maxPrice", String.valueOf(maxPrice));
        params.put("category", category.toString());
    }

    /**
     * Merupakan Constructor yang memiliki kondisi dimana user tidak memilih minPrice dan maxPrice
     */
    //Ketika minPrice dan maxPrice tidak terisi
    public FilterRequest(int page, int pageSize, int accountId, String search, ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, pageSize, accountId, search, "", "", category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", "");
        params.put("maxPrice", "");
        params.put("category", category.toString());
    }

    /**
     * Merupakan Constructor yang memiliki kondisi dimana user tidak memilih maxPrice
     */
    //Ketika maxPrice tidak terisi
    public FilterRequest(int page, int pageSize, int accountId, int minPrice, String search, ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, pageSize, accountId, search, minPrice, "", category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", String.valueOf(minPrice));
        params.put("maxPrice", "");
        params.put("category", category.toString());
    }

    /**
     * Merupakan Constructor yang memiliki kondisi dimana user tidak memilih minPrice
     */
    //Ketika minPrice tidak terisi
    public FilterRequest(ProductCategory category, int page, int pageSize, int accountId, int maxPrice, String search, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, pageSize, accountId, search, "", maxPrice, category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", "");
        params.put("maxPrice", String.valueOf(maxPrice));
        params.put("category", category.toString());
    }

    /**
     * Merupakan Constructor yang memiliki kondisi dimana user tidak memilih minPrice, maxPrice, dan pageSizenya
     */
    //Ketika minPrice,maxPrice dan pageSize tidak terisi
    public FilterRequest(int page, int accountId, String search, ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, "", accountId, search, "", "", category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", "");
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", "");
        params.put("maxPrice", "");
        params.put("category", category.toString());
    }

    /**
     * Merupakan Constructor yang memiliki kondisi dimana user tidak memilih maxPrice dan pageSizenya
     */
    //Ketika maxPrice dan pageSize tidak terisi
    public FilterRequest(int page, int accountId, String search, int minPrice, ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, "", accountId, search, minPrice, "", category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", "");
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", String.valueOf(minPrice));
        params.put("maxPrice", "");
        params.put("category", category.toString());
    }

    /**
     * Merupakan Constructor yang memiliki kondisi dimana user tidak memilih minPrice dan pageSizenya
     */
    //Ketika minPrice dan pageSize tidak terisi
    public FilterRequest(String search, int page, int accountId, int maxPrice, ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, String.format(URL_FORMAT, page, "", accountId, search, "", maxPrice, category.toString()), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", "");
        params.put("accountId", String.valueOf(accountId));
        params.put("search", search);
        params.put("minPrice", "");
        params.put("maxPrice", String.valueOf(maxPrice));
        params.put("category", category.toString());
    }

    /**
     * Merupakan method yang digunakan untuk mengambil parameter constructornya
     * @return Hashmap dengan isi parameter untuk POST
     */
    public Map<String, String> getParams(){
        return params;
    }

}
