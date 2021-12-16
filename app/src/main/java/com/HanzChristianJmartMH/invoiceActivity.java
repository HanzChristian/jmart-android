package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class invoiceActivity extends AppCompatActivity {

    private TabLayout tablayoutInvoice;
    private RecyclerView recyclerViewInvoiceAccount;
    public static final Gson gson = new Gson();
    private ArrayList<Payment> ListP = new ArrayList<>();
    private Account account = LoginActivity.getLoggedAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        tablayoutInvoice = findViewById(R.id.tablayoutInvoice);
        getPaymentList();
        recyclerViewInvoiceAccount = findViewById(R.id.recyclerviewAccount);
//        recyclerViewInvoiceStore = findViewById(R.id.recyclerviewStore);


        tablayoutInvoice.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        recyclerViewInvoiceAccount.setVisibility(View.VISIBLE);
//                        recyclerViewInvoiceStore.setVisibility(View.GONE);
                        break;
                    case 1:
                        recyclerViewInvoiceAccount.setVisibility(View.GONE);
//                        recyclerViewInvoiceStore.setVisibility(View.VISIBLE);
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

    private void getPaymentList(){
        Response.Listener<String> listener = new Response.Listener<String>() {      //listener
            @Override
            public void onResponse(String response) {
                try {
                    ListP.clear();
                    JSONArray object = new JSONArray(response);
                    Type paymentListType = new TypeToken<ArrayList<Payment>>() {
                    }.getType();     //mengambil tipe list Payment
                    ListP = gson.fromJson(response, paymentListType);
                    recyclerViewInvoiceAccount.setLayoutManager(new LinearLayoutManager(invoiceActivity.this));
                    invoiceAdapter invoiceAccountAdapter = new invoiceAdapter(ListP);
                    recyclerViewInvoiceAccount.setAdapter(invoiceAccountAdapter);
                } catch (JSONException e) {     //jika response null
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {       //errorListener jika tidak terkoneksi ke backend
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(invoiceActivity.this, "Get List Failed due to Connection", Toast.LENGTH_SHORT).show();
            }
        };

        InvoiceRequest invoiceRequest = new InvoiceRequest(account.id, true, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(invoiceActivity.this);
        queue.add(invoiceRequest);
    }
}