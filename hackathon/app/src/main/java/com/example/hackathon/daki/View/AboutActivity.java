package com.example.hackathon.daki.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hackathon.daki.Control.AuxiliumJson;
import com.example.hackathon.daki.Control.Utilitarios;
import com.example.hackathon.daki.Main;
import com.example.hackathon.daki.R;
import com.example.hackathon.daki.View.CardList.ListaCardsAprovadosActivity;
import com.example.hackathon.daki.View.CardList.ListaCardsPendentesActivity;
import com.example.hackathon.daki.View.CardList.StartAnuncioActivity;
import com.example.hackathon.daki.View.CardList.VisualizarAprovadosActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AboutActivity extends AppCompatActivity {

//    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


//        TODO pegar token para acessar as telas
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Main.context);
        final String token = sharedPreferences.getString("token", "");

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder b = chain.request().newBuilder();
                b.addHeader("Accept", "application/json");
                b.addHeader("Authorization", token);
                return chain.proceed(b.build());
            }
        }).build();
//        TODO  pegar token para acessar as telas


        Toolbar toolbar = (Toolbar) findViewById(R.id.barDaki);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//      add the back button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_padrao, menu);
        String permissoes = PreferenceManager.getDefaultSharedPreferences(Main.context)
                .getString("permissoes", "");
        if(permissoes.equals("Cliente")) {
            menu.findItem(R.id.menuListaPendentes).setVisible(false);
        }


        return true;

    }

    //   this will select what item will be selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Utilitarios.getSwitchcase(item, this);


        return super.onOptionsItemSelected(item);
    }


}
