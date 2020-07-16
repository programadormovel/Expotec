package com.itbbelval.expotec;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.itbbelval.expotec.R.layout.activity_unidade2;

public class Unidade2Activity extends AppCompatActivity {

    private List<Unidade> listaUnidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_unidade2);

        final RecyclerView rvListaUnidades = findViewById(R.id.lista_unidades2);
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

        Unidade2Adapter unidadeAdapter = new Unidade2Adapter(listaUnidades);
        rvListaUnidades.setAdapter(unidadeAdapter);

    }
}
