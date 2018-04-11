package com.example.hackathon.daki.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hackathon.daki.Model.AnuncioB;
import com.example.hackathon.daki.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vaharamus on 26/03/2018.
 */

public class AnunciosBAdapter extends RecyclerView.Adapter {

    private List<AnuncioB> anunciosAprovados;
    private Context context;
    private ArrayList<AnuncioB> anunciosLista;
    private Activity activity;

    public AnunciosBAdapter(List<AnuncioB> anunciosAprovados, Context context, Activity activity) {
        this.anunciosAprovados = anunciosAprovados;
        this.context = context;
        this.activity = activity;
        this.anunciosLista = new ArrayList<>();
        this.anunciosLista.addAll(anunciosAprovados);

    }

    // filtrando por nome
    public void filtrarPorNome(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        anunciosAprovados.clear();
        if (charText.length() == 0) {
            anunciosAprovados.addAll(anunciosLista);
        } else {
            for (AnuncioB l : anunciosLista) {
                if (l.getTitulo().toLowerCase(Locale.getDefault()).contains(charText)) {
                    anunciosAprovados.add(l);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        AnunciosBHolder holder = new AnunciosBHolder(view, this);
        holder.setActivity(activity);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AnunciosBHolder viewHolder = (AnunciosBHolder) holder;
        AnuncioB anuncioB = anunciosAprovados.get(position);
        ((AnunciosBHolder) holder).preencher(anuncioB);

    }

    @Override
    public int getItemCount() {
        return anunciosAprovados.size();
    }



}