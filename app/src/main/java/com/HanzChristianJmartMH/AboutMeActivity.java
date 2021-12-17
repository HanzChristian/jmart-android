package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.HanzChristianJmartMH.model.Account;
import com.HanzChristianJmartMH.model.Store;
import com.HanzChristianJmartMH.request.RegisterStoreRequest;
import com.HanzChristianJmartMH.request.RequestFactory;
import com.HanzChristianJmartMH.request.TopUpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Merupakan Class yang merepresentasikan Activity Detail Account dan Storenya, beserta function invoice
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class AboutMeActivity extends AppCompatActivity {

    private Button registerstorebutton;
    private Button registerbutton;
    private Button cancelbutton;
    private Button topupbutton;
    private Button invoicebutton;
    private TextView tvName, tvEmail, tvBalance, tvStoreName, tvStoreAddress, tvStorePhoneNumber;
    private EditText editTopup, editName, editAddress, editPhoneNumber;
    private Account account = LoginActivity.getLoggedAccount();
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private static final Gson gson = new Gson();

    /**
     * Merupakan method yang digunakan untuk melakukan inisialisasi
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //untuk refresh balance (karena habis beli product,gagal payment)
        takeBalance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);


        //Button
        topupbutton = findViewById(R.id.topup);
        registerstorebutton = findViewById(R.id.registerstore1);
        registerbutton = findViewById(R.id.registerstore2);
        cancelbutton = findViewById(R.id.cancelregister);
        invoicebutton = findViewById(R.id.invoicebutton);

        //TextView
        tvName = findViewById(R.id.name);
        tvEmail = findViewById(R.id.emaillogin);
        tvBalance = findViewById(R.id.balance);
        tvStoreName = findViewById(R.id.shopname);
        tvStoreAddress = findViewById(R.id.shopaddress);
        tvStorePhoneNumber = findViewById(R.id.shopphonenumber);

        //EditText
        editTopup = findViewById(R.id.edittopup);
        editName = findViewById(R.id.storename);
        editAddress = findViewById(R.id.storeaddress);
        editPhoneNumber = findViewById(R.id.storephonenumber);

        //Penampilan data Account
        tvName.setText(account.name);
        tvEmail.setText(account.email);
        tvBalance.setText("" + account.balance);

        linearLayout1 = findViewById(R.id.linearlayout1);
        linearLayout2 = findViewById(R.id.linearlayout2);

        //Apabila sudah teregister data storenya, langsung menampilkan layout2
        if (account.store != null) {
            linearLayout2.setVisibility(View.VISIBLE);
            registerstorebutton.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.GONE);
            tvStoreName.setText(account.store.name);
            tvStoreAddress.setText(account.store.address);
            tvStorePhoneNumber.setText(account.store.phoneNumber);
        }
        //Apabila belum teregister, maka muncul tombol register store
        else {
            registerstorebutton.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
        }

        //Apabila topup button di click
        topupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountTopUp = editTopup.getText().toString().trim();
                double amount = Double.valueOf(amountTopUp);

                //Jika server memberikan response (terhubung)
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Boolean object = Boolean.valueOf(response);
                        if (object) {
                            Toast.makeText(AboutMeActivity.this, "Top Up Success!", Toast.LENGTH_SHORT).show();
                            takeBalance();
//                            editTopup.getText().clear();
                        } else {
                            Toast.makeText(AboutMeActivity.this, "Top Up Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                //Jika server tidak memberikan response (gagal)
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AboutMeActivity.this, "Top Up Failed due to Connection!", Toast.LENGTH_SHORT).show();
                        Log.d("ERROR", error.toString());
                    }
                };

                TopUpRequest topUpRequest = new TopUpRequest(account.id, amount, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                queue.add(topUpRequest);
            }
        });

        //Apabila register store di click
        registerstorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                registerstorebutton.setVisibility(v.GONE);
                linearLayout1.setVisibility(v.VISIBLE);
                Toast.makeText(getApplicationContext(),"Register Store di click",Toast.LENGTH_SHORT).show();
            }
        });


        //Apabila register button di click
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeName = editName.getText().toString();
                String storeAddress = editAddress.getText().toString();
                String storePhoneNumber = editPhoneNumber.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            if (object != null) {
                                Toast.makeText(AboutMeActivity.this, "Register Store Success!", Toast.LENGTH_SHORT).show();
                            }
                            account.store = gson.fromJson(object.toString(), Store.class);
                            tvStoreName.setText(account.store.name);
                            tvStoreAddress.setText(account.store.address);
                            tvStorePhoneNumber.setText(account.store.phoneNumber);
                            linearLayout1.setVisibility(v.GONE);
                            linearLayout2.setVisibility(v.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Register Store di click", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(AboutMeActivity.this, "Register Store Failed!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AboutMeActivity.this, "Register Store Failed due to Connection!", Toast.LENGTH_SHORT).show();
                        Log.d("ERROR", error.toString());
                    }
                };
                RegisterStoreRequest registerStoreRequest = new RegisterStoreRequest(account.id, storeName, storeAddress, storePhoneNumber, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                queue.add(registerStoreRequest);
            }
        });

        //Apabila cancel button di click
        cancelbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(v.GONE);
                registerstorebutton.setVisibility(v.VISIBLE);
                Toast.makeText(getApplicationContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //Apabila invoice button di click
        invoicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutMeActivity.this,invoiceActivity.class);
                Toast.makeText(getApplicationContext(), "Invoice Button clicked", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }

    /**
     * Merupakan method yang digunakan untuk melakukan refresh/update balance
     */
    public void takeBalance() {
        //Ketika menerima response
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    account = gson.fromJson(response, Account.class);
                    tvBalance.setText(String.valueOf(account.balance));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //Ketika tidak menerima response
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AboutMeActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
        queue.add(RequestFactory.getById("account", account.id, listener, errorListener));
    }

    /**
     * Merupakan method yang digunakan untuk update balance ketika berbeda layout
     */
    @Override
    protected void onResume() {
        takeBalance();
        super.onResume();
    }
}