package com.example.hackathon.daki.View;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hackathon.daki.Control.RetrofitConfig;
import com.example.hackathon.daki.Control.Utilitarios;
import com.example.hackathon.daki.Model.Usuario;
import com.example.hackathon.daki.View.CardList.ListaCardsAprovadosActivity;
import com.example.hackathon.daki.View.CardList.ListaCardsPendentesActivity;
import com.example.hackathon.daki.View.CardList.StartAnuncioActivity;
import com.example.hackathon.daki.View.CardList.VisualizarAprovadosActivity;
import com.example.hackathon.daki.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtNome, txtMail, txtPass, txtPassSegundo;
    private TextInputLayout nameInput, mailInput, passInput, confPassInput;
    private Button btnConfirmar;
    public Button btnVoltar;
    private Vibrator vib;
    Animation animShake;
    private String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtMail  = (EditText) findViewById(R.id.txtRegisEmailks);
        txtPass = (EditText) findViewById(R.id.txtRegisSenhaks);
        txtPassSegundo = (EditText) findViewById(R.id.txtRegisConfirSenhaks);

        nameInput = (TextInputLayout) findViewById(R.id.txtRegisNameksfix);
        mailInput = (TextInputLayout) findViewById(R.id.txtRegisEmailksfix);
        passInput = (TextInputLayout) findViewById(R.id.txtRegisSenhaksfix);
        confPassInput = (TextInputLayout) findViewById(R.id.txtRegisConfirSenhaksfix);
        btnConfirmar = (Button) findViewById(R.id.btnRegisAvancarks);
        btnVoltar = (Button) findViewById(R.id.btnRegisVoltarks);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        final String email = mailInput.getEditText().getText().toString().trim();

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mview) {

                if(passInput.getEditText().getText().toString().equals(confPassInput.getEditText().getText().toString())) {

//                    if (email.matches(emailPattern)){

                        // RestInterface restInterface = retrofit.create(RestInterface.class);
                        Call<ResponseBody> call = new RetrofitConfig().getRestInterface().cadastrar(new Usuario(
                                nameInput.getEditText().getText().toString(),
                                mailInput.getEditText().getText().toString(),
                                passInput.getEditText().getText().toString()
                        ));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {

                                    Intent registroIntent = new Intent(RegistroActivity.this, LoginActivity.class);
                                    startActivity(registroIntent);

                                    Toast.makeText(getApplicationContext(), "Conta criada! \n Você pode fazer Login agora", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Falhou", Toast.LENGTH_LONG).show();
                            }
                        });


//                }else{
//                    submitFormMail();
//                    Toast.makeText(RegistroActivity.this, "Formato de e-mail inválida", Toast.LENGTH_LONG).show();
//
//                }

                } else {
                    submitFormPass();
                    Toast.makeText(RegistroActivity.this, "Tente com outra senha", Toast.LENGTH_LONG).show();
                }




            }
        });
    }

    private void submitFormPass() {

            confPassInput.setAnimation(animShake);
            confPassInput.startAnimation(animShake);
            vib.vibrate(120);
//            return;
        }

//    private void submitFormMail() {
//
//        mailInput.setAnimation(animShake);
//        mailInput.startAnimation(animShake);
//        vib.vibrate(120);
////            return;
//    }

    public void VoltarParaLogin(View v) {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VoltarIntent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(VoltarIntent);
            }
        });
    }

}
