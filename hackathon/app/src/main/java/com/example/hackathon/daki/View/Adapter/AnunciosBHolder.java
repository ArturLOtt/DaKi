package com.example.hackathon.daki.View.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon.daki.Main;
import com.example.hackathon.daki.Model.AnuncioB;
import com.example.hackathon.daki.R;
import com.example.hackathon.daki.View.CardList.ListaCardsAprovadosActivity;
import com.example.hackathon.daki.View.CardList.VisualizarAprovadosActivity;

/**
 * Created by Vaharamus on 26/03/2018.
 */

public class AnunciosBHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private int anuncioId;
    private TextView aTitulo;
    private TextView aLocal;
    private ImageView aImageView;
    private ImageView barrinha;
    private final AnunciosBAdapter adapter;
    private Context context;
    private Activity activity;


    public AnunciosBHolder(final View view, final AnunciosBAdapter adapter) {
        super(view);
        this.adapter = adapter;
        view.setOnClickListener(this);
        context = view.getContext();

        aTitulo = itemView.findViewById(R.id.txtCardTitulo);
        aLocal = view.findViewById(R.id.txtCardLocal);
        aImageView = itemView.findViewById(R.id.ivFoto);
        barrinha = itemView.findViewById(R.id.ivCardColorAnuncio);
    }

    public void preencher(AnuncioB anuncioB) {
        anuncioId = anuncioB.getId();
        aTitulo.setText(anuncioB.getTitulo());
        aLocal.setText(anuncioB.getEndereco());






//            for (int a = 0; a < AnuncioB.size(); i++) {
//                                      if (tipo == 2)
//                                      {
//                                          barrinha.setBackgroundColor(ContextCompat.getColor(ListaCardsAprovadosActivity.this, R.color.colorServico));
////                                          texto.setText("texto a");
//                                      } else if (tipo == 3) {
//                                          anuncioImage.setBackgroundColor(ContextCompat.getColor(ListaCardsAprovadosActivity.this, R.color.colorAlerta));
//                                      }
//                                      else if (tipo == 1){
//                                          anuncioImage.setBackgroundColor(ContextCompat.getColor(ListaCardsAprovadosActivity.this, R.color.colorEvento));
//
//                                      }
//                                  }

    }

    @Override
    public void onClick(View view) {
        Log.d("anuncio", String.valueOf(anuncioId));

        Intent intentVizu = new Intent(activity, VisualizarAprovadosActivity.class);
        intentVizu.putExtra("anuncioId", anuncioId);

        Log.e("batata", "  SS    ");

        activity.startActivityForResult(intentVizu,0);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}



