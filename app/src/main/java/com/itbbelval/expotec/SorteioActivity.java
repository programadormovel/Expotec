package com.itbbelval.expotec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.itbbelval.expotec.R.layout.activity_sorteio;

public class SorteioActivity extends AppCompatActivity {

    private Button btnSorteio;
    private ProgressBar loadingProgressBar;
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db;
    private static final String TAG = SorteioActivity.class.getName();
    protected List<ListaEmails> emails;
    private int contador;
    private String emailsApresentados;
    private TextView txtGanhador, txtMensagemSorteio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_sorteio);

        inicializarComponentes();

        btnSorteio.setEnabled(false);
        loadingProgressBar.setVisibility(View.GONE);

        contador = 1;

        java.util.Date dataHoraAtual = new java.util.Date();
        final String dia = new SimpleDateFormat("dd").format(dataHoraAtual);

        try{
            loadingProgressBar.setVisibility(View.VISIBLE);
            db.collection("evento")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Toast.makeText(SorteioActivity.this,
                                            document.getId() + " => " + document.getData(),
                                            Toast.LENGTH_LONG);
                                    if(document.getData()
                                            .containsValue(dia.trim())){
                                        String emailUsuario =
                                                document.getData().get("email").toString();
                                        emails.add(new ListaEmails(contador, emailUsuario));
                                        contador++;
                                        //finish();
                                    }
                                }
                                btnSorteio.setEnabled(true);
                                loadingProgressBar.setVisibility(View.GONE);
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(SorteioActivity.this,
                                        "Error getting documents.",
                                        Toast.LENGTH_LONG);
                                btnSorteio.setEnabled(false);
                                loadingProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(SorteioActivity.this,
                    e.getMessage(),
                    Toast.LENGTH_LONG);
        }

        btnSorteio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                txtGanhador.setVisibility(View.INVISIBLE);
                btnSorteio.setEnabled(false);
                txtGanhador.setText("");
                Handler handle = new Handler();
                handle.postDelayed(new Runnable() {
                    @Override public void run() {
                        sortear();
                    }
                }, 9000);
                btnSorteio.setEnabled(true);
                loadingProgressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void inicializarComponentes(){
        btnSorteio = findViewById(R.id.btn_sort);
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        emails = new ArrayList<>();
        txtGanhador = findViewById(R.id.txt_ganhador);
        txtMensagemSorteio = findViewById(R.id.txt_mensagem_sorteio);
        loadingProgressBar = findViewById(R.id.progressBarSorteio);
    }

    private void sortear(){
        String ganhador = null;
        txtMensagemSorteio.setText("Sorteando");
        while(ganhador==null) {
            int numeroSorteado =  ( int ) ( 1 + ( Math.random() * emails.size() ) ) ;
            for (ListaEmails item : emails) {
                emailsApresentados = item.getEmail();
                txtGanhador.setText(emailsApresentados);
                if (numeroSorteado == item.getNumero()) {
                    ganhador = item.getEmail();
                }
            }
            if(ganhador!=null)
                txtGanhador.setVisibility(View.VISIBLE);
                txtMensagemSorteio.setText("O ganhador ou ganhadora é: ");
                txtGanhador.setText(ganhador);
        }
    }

    private class ListaEmails {
        private int numero;
        private String email;

        public ListaEmails(int numero, String email) {
            this.numero = numero;
            this.email = email;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.clearPersistence();
    }
}
