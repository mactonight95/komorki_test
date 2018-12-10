package com.example.myapplication;
import com.example.myapplication.SessionManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    //Lista elementow UI
    EditText usernameBox, passwordBox;
    Button loginButton;
    TextView registerLink, helloMessage;
    String URL = "https://powerful-tor-45975.herokuapp.com/user/sign-in";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Dolaczanie elementow widoku do zmiennych

        usernameBox = (EditText)findViewById(R.id.usernameBox); //Login
        passwordBox = (EditText)findViewById(R.id.passwordBox); //Haslo
        loginButton = (Button)findViewById(R.id.loginButton); //Przycisk logowania
        registerLink = (TextView)findViewById(R.id.registerLink);   //Link do rejestracji
        helloMessage = (TextView)findViewById(R.id.helloView); //Napis powitalny

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("true")){
                            Toast.makeText(Login.this, "Succesfully logged in!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this,Home.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Bad username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Login.this, "Error occured -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("username", usernameBox.getText().toString());
                        parameters.put("password", passwordBox.getText().toString());
                        return parameters;
                    }
                };
                //Kolejka requestow Volley
                RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                rQueue.add(request);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }


}