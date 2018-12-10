package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import android.util.Log;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Home extends AppCompatActivity {

    //Lista elementow UI
    TextView articlesList;
    Button articlesGetButton, articleAddButton;
    String URL = " https://powerful-tor-45975.herokuapp.com/article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Dolaczanie elementow widoku do zmiennych
        articleAddButton = (Button) findViewById(R.id.articleAddButton); //Dodaj artykul
        articlesGetButton = (Button) findViewById(R.id.articlesGetButton); //Pobierz liste artykulow
        articlesList = (TextView) findViewById(R.id.registerLink);   //Lista arykulow
        articlesList.setMovementMethod(new ScrollingMovementMethod()); //Mozliwosc scrollowania listy
    }
    private void clearArticlesList() {
        //Wyczysc liste artykulow

        articlesList.setText("");
    }
    private void addToArticlesList(String articleTitle) {
        //Dodaj artykul do listy artykulow
        String currentText = articlesList.getText().toString();
        articlesList.setText(currentText + "\n \n" + articleTitle);
    }

    private void setArticlesListText(String str) {
        //Ustaw tekst na liscie np. "No articles found"

        articlesList.setText(str);
    }

    private void getArticlesList() {
        //Pobranie listy tytulow artykulow

        //JSON request ktory nie dziala???

        JsonArrayRequest arrayReq = new JsonArrayRequest(Request.Method.GET, URL + "/all", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Sprawdzam dlugosc odpowiedzi od serwera
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String articleTitle = jsonObj.getString("title:");
                                    addToArticlesList(articleTitle);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            //Jezeli brak artykulow to wyswietlam to
                            setArticlesListText("No articles found.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //W przypadku bledu api
                        setArticlesListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        //Dodanie do kolejki requestow
        RequestQueue rQueue = Volley.newRequestQueue(Home.this);
        rQueue.add(arrayReq);
    }
    public void getArticlesClicked(View v) {
        //Po kliknieciu GET ARTICLES wywoluje sie ta funkcja
        //dzięki linii w widoku activity_home: android:onClick="getArticlesClicked"

        //Wyczysc liste artyulow
        clearArticlesList();

       //Wyswietl swiezo pobrane listy tematow artykulow
        getArticlesList();
    }

    //TODO: klikam na tytul z listy artykulow i wyswietla sie nowe activity z tekstem artyulu i mozliwosc komentowania

}

