package com.example.hackathon.daki.View.CardList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.hackathon.daki.Control.AuxiliumJson;
import com.example.hackathon.daki.Control.RetrofitConfig;
import com.example.hackathon.daki.Control.Utilitarios;
import com.example.hackathon.daki.Main;
import com.example.hackathon.daki.Model.AnuncioB;
import com.example.hackathon.daki.R;
import com.example.hackathon.daki.View.Adapter.AnunciosBAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaCardsAprovadosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<AnuncioB> anunciosAprovados;
    private AnunciosBAdapter adapter;
    private SearchView svAnunciosAprovados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cards_aprovados);

        mRecyclerView = findViewById(R.id.rvListaCardsAprovadosx);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        svAnunciosAprovados = findViewById(R.id.svPesquisaAprovados);

        buscarAnuncio();

        buscarSearchView();


//TODO////tirar depois
        Toolbar toolbar = (Toolbar) findViewById(R.id.barDaki);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//      add the back button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
//TODO////tirar depois

    }

    private void buscarAnuncio()  {
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


        Call<List<AnuncioB>> call = new RetrofitConfig(okHttpClient).getRestInterface().listarAnunciosAprovados();
        call.enqueue(new Callback<List<AnuncioB>>() {
            @Override
            public void onResponse(Call<List<AnuncioB>> call, Response<List<AnuncioB>> response) {
                if (response.isSuccessful()) {
                    anunciosAprovados = response.body();
                    if (anunciosAprovados != null) {
                        adapter = new AnunciosBAdapter(anunciosAprovados, getApplicationContext(), ListaCardsAprovadosActivity.this);
                        mRecyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AnuncioB>> call, Throwable t) {

            }
        });
    }


    private void buscarSearchView() {

        svAnunciosAprovados.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // recebo a String que quero buscar
                String anuncioBuscado = s;
                // coloco um filtro no próprio adapter
                adapter.filtrarPorNome(anuncioBuscado);
                return false;
            }
        });

    }

    //    this will create the options menu for us => 3 dots
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        buscarAnuncio();
    }

}

