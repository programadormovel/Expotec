package com.itbbelval.expotec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.itbbelval.expotec.R.layout.activity_mapa;

public class MapaActivity extends AppCompatActivity {

    TextView txtRedes, txtManutencao, txtInformatica, txtSeguranca,
    txtEdificacoes, txtDesign, txtInternet, txtPublicidade,
    txtFarmacia, txtAnalises, txtEnfermagem, txtQuimica, txtEletronica,
    txtTelecom, txtLogistica, txtAdmin, txtRH, txtHospedagem, txtSecretariado,
    txtJuridico, txtContabilidade, txtFinancas, txtPublico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_mapa);

        txtRedes = findViewById(R.id.txtRedes);
        txtManutencao = findViewById(R.id.txtManutencao);
        txtInformatica = findViewById(R.id.txtInformatica);
        txtSeguranca = findViewById(R.id.txtSeguranca);
        txtEdificacoes = findViewById(R.id.txtEdificacoes);
        txtDesign = findViewById(R.id.txtDesign);
        txtInternet = findViewById(R.id.txtInternet);
        txtPublicidade = findViewById(R.id.txtPublicidade);
        txtFarmacia = findViewById(R.id.txtFarmacia);
        txtAnalises = findViewById(R.id.txtAnalises);
        txtEnfermagem = findViewById(R.id.txtEnfermagem);
        txtQuimica = findViewById(R.id.txtQuimica);
        txtEletronica = findViewById(R.id.txtEletronica);
        txtTelecom = findViewById(R.id.txtTelecom);
        txtLogistica = findViewById(R.id.txtLogistica);
        txtAdmin = findViewById(R.id.txtAdmin);
        txtRH = findViewById(R.id.txtRH);
        txtHospedagem = findViewById(R.id.txtHospedagem);
        txtSecretariado = findViewById(R.id.txtSecretariado);
        txtJuridico = findViewById(R.id.txtJuridico);
        txtContabilidade = findViewById(R.id.txtContabilidade);
        txtFinancas = findViewById(R.id.txtFinancas);
        txtPublico = findViewById(R.id.txtPublico);

/*
        txtRedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 0);
                startActivity(it);
            }
        });
        txtManutencao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 1);
                startActivity(it);
            }
        });
        txtInformatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 2);
                startActivity(it);
            }
        });
        txtSeguranca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 3);
                startActivity(it);
            }
        });
        txtEdificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 4);
                startActivity(it);
            }
        });
        txtDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 5);
                startActivity(it);
            }
        });
        txtInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 6);
                startActivity(it);
            }
        });
        txtPublicidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 7);
                startActivity(it);
            }
        });
        txtFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 8);
                startActivity(it);
            }
        });
        txtAnalises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 9);
                startActivity(it);
            }
        });
        txtEnfermagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 10);
                startActivity(it);
            }
        });
        txtQuimica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 11);
                startActivity(it);
            }
        });
        txtEletronica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 12);
                startActivity(it);
            }
        });
        txtTelecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 13);
                startActivity(it);
            }
        });
        txtLogistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 14);
                startActivity(it);
            }
        });
        txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 15);
                startActivity(it);
            }
        });
        txtRH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 16);
                startActivity(it);
            }
        });
        txtHospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 17);
                startActivity(it);
            }
        });
        txtSecretariado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 18);
                startActivity(it);
            }
        });
        txtJuridico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 19);
                startActivity(it);
            }
        });
        txtContabilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 20);
                startActivity(it);
            }
        });
        txtFinancas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 21);
                startActivity(it);
            }
        });
        txtPublico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapaActivity.this,
                        MostraCursoActivity.class);
                it.putExtra("cursoSelecionado", 22);
                startActivity(it);
            }
        });

*/
    }



}
