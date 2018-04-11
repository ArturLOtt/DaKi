package com.example.hackathon.daki.View.CardList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hackathon.daki.R;

public class StartAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_anuncio);

        Button buttonStartQuiz = findViewById(R.id.btn_start_anuncio);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnuncio();
            }
        });
    }


    private void startAnuncio() {
        Intent intentQuiz = new Intent(this, CriarAnuncioActivity.class);
        startActivity(intentQuiz);
    }
}
