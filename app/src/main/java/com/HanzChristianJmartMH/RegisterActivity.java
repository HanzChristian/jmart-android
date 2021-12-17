package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.HanzChristianJmartMH.request.RegisterRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Merupakan Class yang merepresentasikan Activity Register
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Merupakan method yang digunakan untuk melakukan inisialisasi
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText nameRegister = findViewById(R.id.name);
        EditText emailRegister = findViewById(R.id.emailregister);
        EditText passwordRegister = findViewById(R.id.passwordregister);
        Button btnRegister = findViewById(R.id.registerbutton);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            /**
             * Merupakan method yang digunakan ketika button register di click
             * @param v berupa view
             */
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    /**
                     * Merupakan method yang terjadi ketika backend berhasil memberikan response
                     * @param response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    /**
                     * Merupakan method yang terjadi ketika backend gagal memberikan response
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Failed...", Toast.LENGTH_SHORT).show();
                    }
                };
                String name = nameRegister.getText().toString();
                String email = emailRegister.getText().toString();
                String password = passwordRegister.getText().toString();
                RegisterRequest registerRequest = new RegisterRequest(name, email, password, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}