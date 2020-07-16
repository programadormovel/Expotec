package com.itbbelval.expotec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itbbelval.expotec.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import static com.itbbelval.expotec.R.layout.activity_reader;


public class ReaderActivity extends AppCompatActivity {

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db;
    private Map<String, Object> evento;
    private Intent it;
    private int checkinJaRealizado = 0;
    private static final String TAG = ReaderActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_reader);

        //Objeto Activity para abrir janela com a leitura do QRCode
        final Activity activity = this;

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Camera Scan");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, data);

        if(result != null){
            if(result.getContents() != null){
                String[] res = result.getContents().split(";");
                String dia = res[0];
                String checkin = res[1];
                String email = res[2];

                alert(result.getContents());
                alert("Dia - " + dia);
                alert("E-mail - " + email);
                alert("Dia que compareceu - " + checkin);

                // Gravar dados na coleção eventos Firebase
                db.collection("evento")
                        .whereEqualTo("email", email)
                        .whereEqualTo("dia", dia)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        checkinJaRealizado=1;
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

                if(checkinJaRealizado==0)
                    gravarLeituraFirebase(dia, checkin, email);
                else
                    alert("Check-in já realizado no dia de hoje!");

            }else{
                alert("Scan cancelado");
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alert(String alerta) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ReaderActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        alert.setMessage(alerta);
        alert.show();
    }

    public void gravarLeituraFirebase(String dia, String checkin, String email){

        // Create a new user with a first, middle, and last name
        evento = new HashMap<>();
        evento.put("dia", dia);
        evento.put("checkin", checkin);
        evento.put("email", email);


        // Add a new document with a generated ID
        db.collection("evento")
                .add(evento)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        alert(getResources().getString(R.string.register_success));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                        alert(getResources().getString(R.string.register_insuccess));

                    }
                });

    }

}
