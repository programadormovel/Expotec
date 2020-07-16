package com.itbbelval.expotec;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static com.itbbelval.expotec.R.layout.activity_mostra_unidade;

public class MostraUnidadeActivity extends AppCompatActivity {
    private WebView myWebView;
    private int unidadeSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_mostra_unidade);

        // Recebe o índice da unidade selecionada
        Intent it = getIntent();
        unidadeSelecionada = it.getIntExtra("unidadeSelecionada", -1);

        final ProgressBar progressBar = findViewById(R.id.progress_mostra_unidade);
        progressBar.setVisibility(View.INVISIBLE);

        //TODO - Programar redirecionamento
        myWebView = (WebView) findViewById(R.id.wv_mostra_unidade);

        switch(unidadeSelecionada){
            case 0:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/aldeia-da-serra/");
                break;
            case 1:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/alphaville/");
                break;
            case 2:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/engenho-novo/");
                break;
            case 3:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/jardim-belval/");
                break;
            case 4:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/jardim-mutinga/");
                break;
            case 5:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/jardim-paulista/");
                break;
            case 6:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/maria-cristina/");
                break;
            case 7:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/parque-imperial/");
                break;
            case 8:
                myWebView.loadUrl("https://www.fieb.edu.br/unidade/parque-viana/");
                break;
            default:
                myWebView.loadUrl("https://fiebdigital.fieb.edu.br/expotec");
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