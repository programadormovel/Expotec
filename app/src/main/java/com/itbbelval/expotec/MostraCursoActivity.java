package com.itbbelval.expotec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import static com.itbbelval.expotec.R.layout.activity_mostra_curso;

public class MostraCursoActivity extends AppCompatActivity {
    private WebView myWebView;
    private int cursoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_mostra_curso);

        // Recebe o índice da unidade selecionada
        Intent it = getIntent();
        cursoSelecionado = it.getIntExtra("cursoSelecionado", -1);

        final ProgressBar progressBar = findViewById(R.id.progress_mostra_curso);
        progressBar.setVisibility(View.INVISIBLE);

        //TODO - Programar redirecionamento
        myWebView = (WebView) findViewById(R.id.wv_mostra_curso);

        switch(cursoSelecionado){
            case 0:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/redes-de-computadores/");
                break;
            case 1:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/manutencao-e-suporte-em-informatica/");
                break;
            case 2:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/informatica/");
                break;
            case 3:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/seguranca-do-trabalho/");
                break;
            case 4:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/edificacoes/");
                break;
            case 5:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/design-de-interiores/");
                break;
            case 6:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/informatica-para-internet/");
                break;
            case 7:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/publicidade/");
                break;
            case 8:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/farmacia/");
                break;
            case 9:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/analises-clinicas/");
                break;
            case 10:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/enfermagem/");
                break;
            case 11:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/quimica/");
                break;
            case 12:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/eletroeletronica/");
                break;
            case 13:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/telecomunicacoes/");
                break;
            case 14:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/logistica/");
                break;
            case 15:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/administracao/");
                break;
            case 16:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/recursos-humanos/");
                break;
            case 17:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/hospedagem/");
                break;
            case 18:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/secretariado/");
                break;
            case 19:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/servicos-juridicos/");
                break;
            case 20:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/contabilidade/");
                break;
            case 21:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/financas/");
                break;
            case 22:
                myWebView.loadUrl("https://www.fieb.edu.br/curso/servicos-publicos");
                break;
            default:
                myWebView.loadUrl("https://www.fiebdigital.fieb.edu.br/expotec");
                break;
        }

        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE); // mostra a progress
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE); // esconde a progress
            }
        });
    }
}