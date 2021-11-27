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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;
    private EditText passwordLogin;
    private EditText emailLogin;
    private Button btnLogin;
    private TextView btnRegister;


    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordLogin = findViewById(R.id.passwordlogin);
        emailLogin = findViewById(R.id.emaillogin);
        btnLogin = findViewById(R.id.loginbutton);
        btnRegister = findViewById(R.id.registerbutton);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.loginbutton){
            String email = emailLogin.getText().toString().trim();
            String password = passwordLogin.getText().toString().trim();

            LoginRequest req = new LoginRequest(email,password,this,this);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(req);
        }
        else if(view.getId()==R.id.registerbutton){
             Intent i = new Intent(this,RegisterActivity.class);
             startActivity(i);
        }
    }

    public void onResponse(String response) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        try{
            JSONObject jsonObject = new JSONObject(response);
            i.putExtra("id", jsonObject.getInt("id"));
        }catch (Exception e){
            Toast.makeText(this, "Login Failed!", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Login Success!", Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Login Failed!", Toast.LENGTH_LONG).show();
    }
}