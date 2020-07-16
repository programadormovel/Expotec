package com.itbbelval.expotec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.itbbelval.expotec.R.layout.activity_unidade;

public class UnidadeActivity extends AppCompatActivity {

    private List<Unidade> listaUnidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_unidade);

        final RecyclerView rvListaUnidades = findViewById(R.id.lista_unidades);
        rvListaUnidades.setLayoutManager(new LinearLayoutManager(this));

        // TODO
        String[] unidades;
        unidades = new String[]{"FIEB - Aldeia da Serra",
                "FIEB - Alphaville", "FIEB - Engenho Novo",
                "FIEB - Jardim Belval", "FIEB - Jardim Mutinga",
                "FIEB - Jardim Paulista", "FIEB - Maria Cristina",
                "FIEB - Parque Imperial", "FIEB - Parque Viana"};

        listaUnidades = new ArrayList<Unidade>();

        for(int x=0; x<unidades.length; x++){
            Unidade uni = new Unidade();
            uni.setNome(unidades[x]);
            listaUnidades.add(uni);
        }

        UnidadeAdapter unidadeAdapter = new UnidadeAdapter(listaUnidades);
        rvListaUnidades.setAdapter(unidadeAdapter);

    }
}
