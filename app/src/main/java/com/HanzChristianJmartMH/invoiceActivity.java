package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.HanzChristianJmartMH.model.Account;
import com.HanzChristianJmartMH.model.Payment;
import com.HanzChristianJmartMH.request.InvoiceRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Merupakan Class yang merepresentasikan Activity invoice
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class invoiceActivity extends AppCompatActivity {

    private TabLayout tablayoutInvoice;
    private RecyclerView recyclerViewInvoiceAccount,recyclerViewInvoiceStore;
    public static final Gson gson = new Gson();
    private ArrayList<Payment> AccountP = new ArrayList<>();
    private ArrayList<Payment> StoreP = new ArrayList<>();
    private Account account = LoginActivity.getLoggedAccount();

    /**
     * Merupakan method yang digunakan untuk melakukan inisialisasi
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        tablayoutInvoice = findViewById(R.id.tablayoutInvoice);
        getAccountPaymentList();
        getStorePaymentList();
        recyclerViewInvoiceAccount = findViewById(R.id.recyclerviewAccount);
        recyclerViewInvoiceStore = findViewById(R.id.recyclerviewStore);


        tablayoutInvoice.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        recyclerViewInvoiceAccount.setVisibility(View.VISIBLE);
                        recyclerViewInvoiceStore.setVisibility(View.GONE);
                        break;
                    case 1:
                        recyclerViewInvoiceAccount.setVisibility(View.GONE);
                        recyclerViewInvoiceStore.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        showRecycler();

    }

    private void showRecycler(){

    }

    /**
     * Merupakan method yang digunakan menunjukkan list payment pada tab Account
     */
    private void getAccountPaymentList(){
        Response.Listener<String> listener = new Response.Listener<String>() {      //listener
            /**
             * Merupakan method ketika berhasil mendapatkan response dari backend
             * @param response
             */
            @Override
            public void onResponse(String response) {
                try {
                    AccountP.clear();
                    JSONArray object = new JSONArray(response);
                    Type paymentListType = new TypeToken<ArrayList<Payment>>() {
                    }.getType();
                    AccountP = gson.fromJson(response, paymentListType);
                    recyclerViewInvoiceAccount.setLayoutManager(new LinearLayoutManager(invoiceActivity.this));
                    invoiceAccountAdapter invoiceAccountAdapter = new invoiceAccountAdapter(AccountP);
                    recyclerViewInvoiceAccount.setAdapter(invoiceAccountAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            /**
             * Merupakan method ketika gagal mendapatkan response dari backend
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(invoiceActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        };

        InvoiceRequest invoiceRequest = new InvoiceRequest(account.id, true, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(invoiceActivity.this);
        queue.add(invoiceRequest);
    }


    /**
     * Merupakan method yang digunakan menunjukkan list payment pada tab Store
     */
    private void getStorePaymentList(){
        Response.Listener<String> listener = new Response.Listener<String>() {

            /**
             * Merupakan method ketika berhasil mendapatkan response dari backend
             * @param response
             */
            @Override
            public void onResponse(String response) {
                try {
                    StoreP.clear();
                    JSONArray object = new JSONArray(response);
                    Type paymentListType = new TypeToken<ArrayList<Payment>>() {
                    }.getType();
                    StoreP = gson.fromJson(response, paymentListType);
                    recyclerViewInvoiceStore.setLayoutManager(new LinearLayoutManager(invoiceActivity.this));
                    invoiceStoreAdapter invoiceStoreAdapter = new invoiceStoreAdapter(StoreP);
                    recyclerViewInvoiceStore.setAdapter(invoiceStoreAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {

            /**
             * Merupakan method ketika gagal mendapatkan response dari backend
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(invoiceActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        };

        InvoiceRequest invoiceRequest = new InvoiceRequest(account.id, false, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(invoiceActivity.this);
        queue.add(invoiceRequest);
    }
}