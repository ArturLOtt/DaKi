package com.example.hackathon.daki.View.CardList;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hackathon.daki.Control.AuxiliumJson;
import com.example.hackathon.daki.Control.RetrofitConfig;
import com.example.hackathon.daki.Control.Utilitarios;
import com.example.hackathon.daki.Main;
import com.example.hackathon.daki.Model.AnuncioB;
import com.example.hackathon.daki.Model.Usuario;
import com.example.hackathon.daki.View.AboutActivity;
import com.example.hackathon.daki.View.LoginActivity;
import com.example.hackathon.daki.R;
import com.example.hackathon.daki.View.RegistroActivity;
import com.example.hackathon.daki.View.WelcomeActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriarAnuncioActivity extends AppCompatActivity {


//>>>  galeria de foto
    ImageView ivImage;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    // Flags
    private static int NEW_ACTION = 1;
    private static final int REQUEST_IMAGE_GALERY = 0;
    private static final int REQUEST_GALERY_PERMISSION = 1;
//>>> galeria de foto


    private TextView txt_tipo;
    private Button btnInserir, btnCancelar, btnUpload, btnData;
    private TextInputLayout autorInput, tituloInput, endereksInput, diasInput, descriksInput;
    private EditText etDiasInput;
    private int mYear, mMonth, mDay;
    private byte[] b;
    private String temp;
    private String selectedSuperStar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anuncio);


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


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Main.context);
        final String token = sharedPreferences.getString("token", "");
        final int usuarioId = sharedPreferences.getInt("usuarioId", 0);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder b = chain.request().newBuilder();
                b.addHeader("Accept", "application/json");
                b.addHeader("Authorization", token);
                return chain.proceed(b.build());
            }
        }).build();


        autorInput = (TextInputLayout) findViewById(R.id.txtCriarAutor);
        tituloInput = (TextInputLayout) findViewById(R.id.txtCriarTitulo);
        endereksInput = (TextInputLayout) findViewById(R.id.txtCriarEndereks);
        diasInput = (TextInputLayout) findViewById(R.id.txtCriarData);
        etDiasInput = (EditText) findViewById(R.id.editCriarData);
        descriksInput = (TextInputLayout) findViewById(R.id.txtCriarDescriks);

        btnUpload = (Button) findViewById(R.id.btnCriarUpload);
        btnData = (Button) findViewById(R.id.btnCriarData);
        btnInserir = (Button) findViewById(R.id.btnCriarInserir);
        btnCancelar = (Button) findViewById(R.id.btnCriarCancelar);
        ivImage = (ImageView) findViewById(R.id.ivFotoPequena);

        final RadioButton evento = (RadioButton) findViewById(R.id.radio_even);
        final RadioButton servico = (RadioButton) findViewById(R.id.radio_serv);
        final RadioButton alerta = (RadioButton) findViewById(R.id.radio_aler);
        TextView txt_tipo = (TextView) findViewById(R.id.txt_tipo);

// TODO       Fora do escopo
        btnUpload.setVisibility(View.GONE);
        ivImage.setVisibility(View.GONE);

                txt_tipo.setVisibility(View.GONE);
                evento.setVisibility(View.GONE);
                servico.setVisibility(View.GONE);
                alerta.setVisibility(View.GONE);

        btnData.setOnClickListener(new abrirDatePicker());
        btnInserir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//// TODO radio button para escolha entre 3 tipos de anuncios: evento, serviço, alerta; não contemplado no Escopo
//                if (evento.isChecked()) {
//                    selectedSuperStar = evento.getText().toString();
//                } else if (servico.isChecked()) {
//                    selectedSuperStar = servico.getText().toString();
//                } else if (alerta.isChecked()) {
//                    selectedSuperStar = alerta.getText().toString();
//                }

                AnuncioB anuncio = new AnuncioB(
                        tituloInput.getEditText().getText().toString()
                        , "evento"
                        , endereksInput.getEditText().getText().toString()
                        , diasInput.getEditText().getText().toString()
                        , "abc"
//                        , b != null ? b.toString() : null
                        , autorInput.getEditText().getText().toString()
                        , descriksInput.getEditText().getText().toString()
                        , null
                        , usuarioId);



                Call<ResponseBody> call = new RetrofitConfig(okHttpClient).getRestInterface().salvarAnuncios(anuncio);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Anuncio criado! \n Aguarde validação do seu anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Falhou", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);

                if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
                    if ((ContextCompat.checkSelfPermission(getBaseContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(CriarAnuncioActivity.this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY_PERMISSION);
                    } else {
                        startActivityForResult(Intent.createChooser(photoPickerIntent, "Selecione a Foto"), REQUEST_IMAGE_GALERY);
                    }
                }
            }
        });
    }


    // Abrir o datepicker para selecionar uma data
    private class abrirDatePicker implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(CriarAnuncioActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etDiasInput.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    // Resultado da galeria
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                b = baos.toByteArray();

                Glide.with(this)
                        .load(stream.toByteArray())
                        .asBitmap()
                        .centerCrop()
                        .into(ivImage);

                Bitmap bitmap = Utilitarios.bitmapFromImageView(ivImage);
                b = Utilitarios.bitmapToBase64(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(CriarAnuncioActivity.this, "Algo deu errado.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(CriarAnuncioActivity.this, "Você não selecionou nenhuma imagem.", Toast.LENGTH_LONG).show();
        }
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


    public void voltarParaLista(View v) {
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createByText = new Intent(CriarAnuncioActivity.this, ListaCardsAprovadosActivity.class);
                startActivity(createByText);
                finish();
            }
        });
    }


}

