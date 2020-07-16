package com.itbbelval.expotec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itbbelval.expotec.ui.login.LoginActivity;
import com.itbbelval.expotec.utils.Alerta;
import com.itbbelval.expotec.utils.MaskEditUtil;

import java.util.HashMap;
import java.util.Map;

import static com.itbbelval.expotec.R.layout.activity_login_registro;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db;

    private FirebaseAuth mAuth;// ...

    private Map<String, Object> user;

    private Intent it;

    private static final String TAG = RegisterActivity.class.getName();

    public RegisterActivity() {
    }

    private int logado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login_registro);

        inicializarComponentes();

        // Realizar registro
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - Realizar registro no Firebase
                if(nameEditText.getText().toString().trim().equals("")
                        || nameEditText.getText().toString().isEmpty()
                        || nameEditText.getText().toString() == null){
                    Toast.makeText(RegisterActivity.this,
                            R.string.nome_obrigatorio,
                            Toast.LENGTH_LONG).show();
                    nameEditText.setFocusable(true);
                    nameEditText.isFocused();
                    nameEditText.setHintTextColor(getResources().getColor(R.color.colorAccent));
                } else if(usernameEditText.getText().toString().trim().equals("")
                        || usernameEditText.getText().toString().isEmpty()
                        || usernameEditText.getText().toString() == null){
                    Toast.makeText(RegisterActivity.this,
                            R.string.email_obrigatorio,
                            Toast.LENGTH_LONG).show();
                    usernameEditText.setFocusable(true);
                    usernameEditText.isFocused();
                    usernameEditText.setHintTextColor(getResources().getColor(R.color.colorAccent));
                } else if(passwordEditText.getText().toString().trim().equals("")
                        || passwordEditText.getText().toString().isEmpty()
                        || passwordEditText.getText().toString() == null){
                    Toast.makeText(RegisterActivity.this,
                            R.string.senha_obrigatoria,
                            Toast.LENGTH_LONG).show();
                    passwordEditText.setFocusable(true);
                    passwordEditText.isFocused();
                    passwordEditText.setHintTextColor(getResources().getColor(R.color.colorAccent));
                } else if(phoneEditText.getText().toString().trim()
                        .replace("(", "")
                        .replace(")", "")
                        .replace("-", "").equals("")
                        || phoneEditText.getText().toString().trim()
                        .replace("(", "")
                        .replace(")", "")
                        .replace("-", "").isEmpty()
                        || phoneEditText.getText().toString() == null){
                    if(isPasswordValid(passwordEditText.getText().toString())
                        && isUserNameValid(usernameEditText.getText().toString())){
                        phoneEditText.removeTextChangedListener(
                                MaskEditUtil.mask(phoneEditText, ""));
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        loginButton.setEnabled(false);
                        v.getRootView().setKeepScreenOn(false);
                        Alerta.mostra(RegisterActivity.this,
                                R.style.dialog,
                                R.string.dialog_wait,
                                R.string.app_name,
                                R.drawable.logo_expotec);
                        writeNewUser(v, nameEditText.getText().toString(),
                                usernameEditText.getText().toString(),
                                passwordEditText.getText().toString(),
                                "NÃO INFORMADO");
                    }else if(!isPasswordValid(passwordEditText.getText().toString())){
                        Alerta.mostra(RegisterActivity.this,
                                R.style.dialog,
                                R.string.invalid_password,
                                R.string.app_name,
                                R.drawable.logo_expotec);

                    }else if(!isUserNameValid(usernameEditText.getText().toString())){
                        Alerta.mostra(RegisterActivity.this,
                                R.style.dialog,
                                R.string.invalid_username,
                                R.string.app_name,
                                R.drawable.logo_expotec);

                    }
                } else {
                    if(isPasswordValid(passwordEditText.getText().toString())
                            && isUserNameValid(usernameEditText.getText().toString())) {
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        v.getRootView().setKeepScreenOn(false);
                        loginButton.setEnabled(false);
                        Alerta.mostra(RegisterActivity.this,
                                R.style.dialog,
                                R.string.dialog_wait,
                                R.string.app_name,
                                R.drawable.logo_expotec);
                        writeNewUser(v, nameEditText.getText().toString(),
                                usernameEditText.getText().toString(),
                                passwordEditText.getText().toString(),
                                phoneEditText.getText().toString());
                    }else if(!isPasswordValid(passwordEditText.getText().toString())){
                        Alerta.mostra(RegisterActivity.this,
                                R.style.dialog,
                                R.string.invalid_password,
                                R.string.app_name,
                                R.drawable.logo_expotec);

                    }else if(!isUserNameValid(usernameEditText.getText().toString())){
                        Alerta.mostra(RegisterActivity.this,
                                R.style.dialog,
                                R.string.invalid_username,
                                R.string.app_name,
                                R.drawable.logo_expotec);
                    }
                }
            }
        });
    }

    private void writeNewUser(final View v, final String name,
                              final String email, final String password,
                              final String phone) {
        // Create a new user with a first, middle, and last name
        user = new HashMap<>();
        user.put("nome", name.trim());
        user.put("email", email.trim());
        //user.put("senha", password);
        user.put("telefone", phone.trim().replace("(", "")
                .replace(")", "").replace("-", ""));
        int nivelAcesso = 1;
        user.put("nivelAcesso", nivelAcesso);

        mAuth.createUserWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser userF = mAuth.getCurrentUser();
                            updateUI(userF);
                            // Add a new document with a generated ID
                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            Snackbar.make(v, R.string.register_success, Snackbar.LENGTH_LONG);
                                            it = new Intent(getBaseContext(), LoginActivity.class);
                                            it.putExtra("email", usernameEditText.getText().toString());
                                            startActivity(it);
                                            logado = 1;
                                            loginButton.setEnabled(true);
                                            loadingProgressBar.setVisibility(View.GONE);
                                            v.getRootView().setKeepScreenOn(true);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Log.w(TAG, "Error adding document", e);
                                            Snackbar.make(v, R.string.register_insuccess, Snackbar.LENGTH_LONG);
                                            nameEditText.setText(name);
                                            usernameEditText.setText(email);
                                            phoneEditText.setText(phone);
                                            nameEditText.setFocusable(true);
                                            loadingProgressBar.setVisibility(View.GONE);
                                            v.getRootView().setKeepScreenOn(true);
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    R.string.register_insuccess,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            loginButton.setEnabled(true);
                            loadingProgressBar.setVisibility(View.GONE);
                            v.getRootView().setKeepScreenOn(true);
                        }
                    }
                });
    }

    private void inicializarComponentes(){
        usernameEditText = findViewById(R.id.username_registro);
        passwordEditText = findViewById(R.id.password_registro);
        nameEditText = findViewById(R.id.nome_registro);
        phoneEditText = findViewById(R.id.fone_registro);
        phoneEditText.addTextChangedListener(
                MaskEditUtil.mask(phoneEditText,
                        MaskEditUtil.FORMAT_FONE));
        loginButton = findViewById(R.id.login_registro);
        loadingProgressBar = findViewById(R.id.loading_registro);

        db = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(logado == 0){
            Intent it = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(it);
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
