package com.HanzChristianJmartMH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.HanzChristianJmartMH.model.Account;
import com.HanzChristianJmartMH.request.LoginRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Merupakan Class yang merepresentasikan Activity Login
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>,Response.ErrorListener {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;
    private EditText passwordLogin;
    private EditText emailLogin;
    private Button btnLogin;
    private TextView btnRegister;

    /**
     * Method yang digunakan untuk mendapatkan object Account
     * @return object Account
     */
    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    /**
     * Merupakan method yang digunakan untuk melakukan inisialisasi
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordLogin = findViewById(R.id.passwordlogin);
        emailLogin = findViewById(R.id.emaillogin);
        btnLogin = findViewById(R.id.loginbutton);
        btnRegister = findViewById(R.id.register);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    /**
     * Merupakan method yang digunakan ketika button di click dan memberikan request
     * @param view
     */
    @Override
    public void onClick(View view){
        if(view.getId()==R.id.loginbutton){
            String email = emailLogin.getText().toString().trim();
            String password = passwordLogin.getText().toString().trim();

            LoginRequest req = new LoginRequest(email,password,this,this);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(req);
        }
        else if(view.getId()==R.id.register){
             Intent i = new Intent(this,RegisterActivity.class);
             startActivity(i);
        }
    }

    /**
     * Merupakan method yang digunakan ketika mendapatkan response dari backendnya
     */
    @Override
    public void onResponse(String response) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        try{
            JSONObject jsonObject = new JSONObject(response);
            loggedAccount = gson.fromJson(jsonObject.toString(), Account.class);
        }catch (Exception e){
            Toast.makeText(this, "Login Failed!", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Login Success!", Toast.LENGTH_LONG).show();
        startActivity(i);

    }

    /**
     * Merupakan method yang digunakan ketika gagal mendapatkan response dari backendnya
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Login Failed...", Toast.LENGTH_LONG).show();
    }
}