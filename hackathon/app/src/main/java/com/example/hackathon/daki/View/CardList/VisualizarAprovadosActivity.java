package com.example.hackathon.daki.View.CardList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon.daki.Control.AuxiliumJson;
import com.example.hackathon.daki.Control.RetrofitConfig;
import com.example.hackathon.daki.Control.Utilitarios;
import com.example.hackathon.daki.Main;
import com.example.hackathon.daki.Model.AnuncioB;
import com.example.hackathon.daki.R;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizarAprovadosActivity extends AppCompatActivity {

    private static final String FOTO_USUARIO = "ivFoto";
//    public static final String PERMISSAO = "permissoes";

    private Button btnAprovarVisu, btnDeletarVisu;
    private ImageView ivImageVisu;
    private ImageView ivBarrinha;
    private AnuncioB anuncio;
    TextView tituloVisu, autorVisu, endereksoVisu, dataVisu, descriksaVisu;
    private byte[] b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_anuncio);

        final Bundle extras = getIntent().getExtras();
        final Integer anuncioId = (extras != null) ? extras.getInt("anuncioId") : null;
        Log.d("anuncio", String.valueOf(anuncioId));

        tituloVisu = (TextView) findViewById(R.id.txtTituloVisualizar);
        endereksoVisu = (TextView) findViewById(R.id.txtRuaVisualizar);
        autorVisu = (TextView) findViewById(R.id.txtAutorVisualizar);
        dataVisu = (TextView) findViewById(R.id.txtDataVisualizar);
        descriksaVisu = (TextView) findViewById(R.id.txtDescricaoVisualizar);
        btnDeletarVisu = (Button) findViewById(R.id.btnVisualizarDeletar);
        btnAprovarVisu = (Button) findViewById(R.id.btnVisualizarAprovar);
        ivImageVisu = (ImageView) findViewById(R.id.imgVisualizar);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Main.context);
        final String token = sharedPreferences.getString("token", "");


        final OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder b = chain.request().newBuilder();
                b.addHeader("Accept", "application/json");
                b.addHeader("Authorization", token);
                return chain.proceed(b.build());
            }
        }).build();

        Call<AnuncioB> callAnuncio = new RetrofitConfig(okHttpClient).getRestInterface().buscarAnuncios(anuncioId);
        callAnuncio.enqueue(new Callback<AnuncioB>() {
            @Override
            public void onResponse(Call<AnuncioB> callAnuncio, Response<AnuncioB> response) {
                anuncio = response.body();
                tituloVisu.setText(anuncio.getTitulo());
                endereksoVisu.setText(anuncio.getEndereco());
                autorVisu.setText(anuncio.getContato());
                dataVisu.setText(anuncio.getData());
                descriksaVisu.setText(anuncio.getDescricao());

            }

            @Override
            public void onFailure(Call<AnuncioB> callAnuncio, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falhou", Toast.LENGTH_LONG).show();
            }
        });

        obterAutorizacao();

//TODO////tirar depois
        Toolbar toolbar = (Toolbar) findViewById(R.id.barDaki);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//      add the back button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
//        para funcionar foi add essa linha no manifest:
//        android:parentActivityName=".CardList.ListaCardsAprovadosActivity"

//TODO////tirar depois


//arrumar o Call
        btnDeletarVisu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> callDell = new RetrofitConfig(okHttpClient).getRestInterface().deleteAnuncio(anuncioId);
                callDell.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> callDell, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), "Anuncio deletado", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> callDell, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Falhou", Toast.LENGTH_LONG).show();
                    }
                });


            }
//
        });


//// Reading from SharedPreferences
//        String value = settings.getString("key", "");
//        Log.d(TAG, value);


//          put
        btnAprovarVisu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anuncio.setAutorizacao(true);
//                Log.e("onClick: ", anuncio.toString());
//                Log.e("onClick: ", "anuncioId: " + anuncioId);
                Call<ResponseBody> callPut = new RetrofitConfig(okHttpClient).getRestInterface().putDeAnuncio(anuncioId);
                callPut.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), "Anuncio movido para a lista de aprovados!", Toast.LENGTH_LONG).show();
//                        Log.e("onResponse: ", "response code: " + response.code());

                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Falhou", Toast.LENGTH_LONG).show();
                    }
                });
//                    );
//
//                    Call<ResponseBody> call = new RetrofitConfig(okHttpClient).getRestInterface().deleteAnuncio(anuncioId);
//                    Call<ResponseBody> deleteRequest = putDeAnuncio(anuncioId, anuncioId);
//                    deleteRequest.enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            Toast.makeText(getApplicationContext(), "Anuncio deletado", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(), "Falhou", Toast.LENGTH_LONG).show();
//                        }
//                    });

            }
        });



//        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
//        textViewCreator.setText(creatorName);
//        textViewLikes.setText("Likes: " + likeCount);
//        textViewLocal.setText(local);
//        textViewDate.setText("Data: " + date);
////        TODO lista


        sharedPreferences.getString("permissoes", null);


        SharedPreferences prefs = getSharedPreferences("permissoes", MODE_PRIVATE);
        String permissoes = prefs.getString("permissoes", null);





    }



    public void obterAutorizacao(){



//do usuario

//Admin


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Main.context);
        String s = preferences.getString("permissoes", "");

        if (s.equals("Cliente")){
            btnDeletarVisu.setVisibility(View.GONE);
            btnAprovarVisu.setVisibility(View.GONE);
        }

    }




    //TODO////tirar depois
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
//TODO////tirar depois






}

