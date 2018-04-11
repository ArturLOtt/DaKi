package com.example.hackathon.daki.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.hackathon.daki.Control.AuxiliumJson;
import com.example.hackathon.daki.Control.RetrofitConfig;
import com.example.hackathon.daki.Control.Utilitarios;
import com.example.hackathon.daki.Main;
import com.example.hackathon.daki.Model.Usuario;
import com.example.hackathon.daki.View.CardList.ListaCardsPendentesActivity;
import com.example.hackathon.daki.View.CardList.VisualizarAprovadosActivity;
import com.example.hackathon.daki.View.CardList.ListaCardsAprovadosActivity;
import com.example.hackathon.daki.View.CardList.StartAnuncioActivity;
import com.example.hackathon.daki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public EditText txtName;
    public EditText txtPass;
    private TextInputLayout nameInputLayout, passInputLayout;

    public Button btnLoginx;
    public Button btnCreatex;
    private Vibrator vib;
    Animation animShake;
    private String token;
    private String permiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtName = (EditText) findViewById(R.id.txtNicknameLoginks);
        txtPass = (EditText) findViewById(R.id.txtPassLoginks);
        btnLoginx = (Button) findViewById(R.id.btnLoginEntrarks);
        btnCreatex = (Button) findViewById(R.id.btnLoginCreateks);

        nameInputLayout = (TextInputLayout) findViewById(R.id.txtNicknameLoginksfix);
        passInputLayout = (TextInputLayout) findViewById(R.id.txtPassLoginksfix);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Main.context);
        token = sharedPreferences.getString("token", null);

        if (token != null) {

            Intent intent = new Intent(LoginActivity.this, ListaCardsAprovadosActivity.class);
            startActivity(intent);
        }


//
//        nameInputLayout.getEditText().setText("admin@admin.com");
//        passInputLayout.getEditText().setText("senhaadmin");


        btnLoginx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mview) {

                // RestInterface restInterface = retrofit.create(RestInterface.class);
                Call<ResponseBody> call = new RetrofitConfig().getRestInterface().buscarLogin(new Usuario(nameInputLayout.getEditText().getText().toString(),
                        passInputLayout.getEditText().getText().toString()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            JSONObject objeto = null;
                            try {
                                objeto = new JSONObject(response.body().string());
                                token = objeto.getString("acessToken");
                                String usuario = objeto.getString("usuario");

                                JSONObject usuarioJSON = new JSONObject(usuario);
                                int usuarioId = usuarioJSON.getInt("id");
                                String permissoes = usuarioJSON.getString("permissoes");


//                                Codigo donegá
                                String permissoesDois = usuarioJSON.getString("permissoes");
//                                Log.e("doneTESTEBATATATA", new JSONArray(permissoesDois).toString());

                                JSONArray permissaoJSONDois = new JSONArray(permissoesDois);

//                                Log.e("doneTESTEBATATATA", permissaoJSONDois.get(0).toString());

                                JSONObject obj = (JSONObject) permissaoJSONDois.get(0);

//                                Log.e("doneTESTEBATATATA", "obj   "+obj.toString());

                                String s = (String) obj.get("nome");
//                                Log.e("doneTESTEBATATATA", "  SS    "+s);

                                JSONArray permissaoJSON = new JSONArray(permissoes);
                               JSONObject tipoPermissao = permissaoJSON.getJSONObject(0);
                               String retornoPermissao = tipoPermissao.getString("nome");

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", "Bearer " + token);
//                                editor.putString("permissao", permiss);
                                editor.putInt("usuarioId", usuarioId);
                                editor.putString("permissoes", s);
                                editor.apply();

                                if (retornoPermissao.equals("User")) {
                                    Intent Permissaointent = new Intent(LoginActivity.this, ListaCardsPendentesActivity.class);
                                    startActivity(Permissaointent);
                                } else {
                                    Intent AprovadoIntent = new Intent(LoginActivity.this, ListaCardsAprovadosActivity.class);
                                    startActivity(AprovadoIntent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Usuário não registrado", Toast.LENGTH_LONG).show();
                        submitForm();
                    }
                });
        }
    });
}

    private void submitForm() {

        nameInputLayout.setAnimation(animShake);
        passInputLayout.startAnimation(animShake);
            vib.vibrate(120);
        }

    public void criarAcc(View v) {
        btnCreatex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createByText = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(createByText);
            }
        });
    }


}

