package com.itbbelval.expotec;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UnidadeAdapter extends RecyclerView.Adapter<UnidadeAdapter.UnidadeViewHolder>{

    List<Unidade> unidades;

    UnidadeAdapter(List<Unidade> unidades){
        this.unidades = unidades;
    }

    @NonNull
    @Override
    public UnidadeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalhe_unidade,
                parent, false);
        UnidadeViewHolder uvh = new UnidadeViewHolder(v);
        return uvh;
    }

    @Override
    public void onBindViewHolder(@NonNull UnidadeViewHolder holder, int position) {
        holder.fotoView.setImageResource(R.drawable.expotec);
        holder.nomeView.setText(unidades.get(position).getNome());
        holder.descricaoView.setText(unidades.get(position).getDescricao());

        final int unidade = position;

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                Intent it = new Intent(ctx, MostraUnidadeActivity.class);
                it.putExtra("unidadeSelecionada", unidade);
                v.getContext().startActivity(it);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return unidades.size();
    }

    public static class UnidadeViewHolder extends RecyclerView.ViewHolder {
        TextView nomeView;
        TextView descricaoView;
        ImageView fotoView;
        View mView;

        UnidadeViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nomeView = (TextView)itemView.findViewById(R.id.unit_name);
            descricaoView = (TextView)itemView.findViewById(R.id.unit_description);
            fotoView = (ImageView)itemView.findViewById(R.id.unit_photo);
        }
    }
}