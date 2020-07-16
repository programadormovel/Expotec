package com.itbbelval.expotec.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itbbelval.expotec.MainActivity;
import com.itbbelval.expotec.R;
import com.itbbelval.expotec.RegisterActivity;
import com.itbbelval.expotec.utils.Alerta;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    TextView acionaRegistro;
    private FirebaseAuth mAuth;
    private int nivelAcesso;
    private String emailRegistrado;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db;

    private static final String TAG = LoginActivity.class.getName();

    private int logado = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        // Chamada da tela de registro
        acionaRegistro = findViewById(R.id.aciona_login_registro);
        acionaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(it);
                logado = 2;
                finish();
            }
        });

/*
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());

                }
                setResult(Activity.RESULT_OK);


                //Complete and destroy login activity once successful
                //finish();



            }
        });

        /*TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        *//*usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(usernameEditText.getText().toString().trim().equals("")
                        || usernameEditText.getText().toString().isEmpty()
                        || usernameEditText.getText().toString() == null){
                    Toast.makeText(LoginActivity.this,
                            R.string.email_obrigatorio,
                            Toast.LENGTH_SHORT).show();
                    usernameEditText.setFocusable(true);
                }else if(passwordEditText.getText().toString().trim().equals("")
                        || passwordEditText.getText().toString().isEmpty()
                        || passwordEditText.getText().toString() == null){
                    Toast.makeText(LoginActivity.this,
                            R.string.senha_obrigatoria,
                            Toast.LENGTH_SHORT).show();
                    passwordEditText.setFocusable(true);
                    passwordEditText.isFocused();
                    passwordEditText.setHintTextColor(
                            getResources().getColor(R.color.colorAccent));
                }else {
                    loginButton.setEnabled(false);
                    acionaRegistro.setEnabled(false);
                    /*Alerta.mostra(LoginActivity.this,
                            R.style.dialog,
                            R.string.dialog_wait,
                            R.string.app_name,
                            R.drawable.logo_expotec);*/
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    verificaLogin();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        Intent it = getIntent();
        emailRegistrado = it.getStringExtra("email");

        // Trazendo email da tela de registro
        if(emailRegistrado != null) {
            usernameEditText.setText(emailRegistrado);
            passwordEditText.setFocusable(true);
        }
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void verificaLogin(){
        mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(),
                passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser userF = mAuth.getCurrentUser();
                            updateUI(userF);

                            db.collection("users")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                                    Toast.makeText(LoginActivity.this,
                                                            document.getId() + " => " + document.getData(),
                                                            Toast.LENGTH_LONG);
                                                    if(document.getData()
                                                            .containsValue(usernameEditText.getText().toString())){
                                                        Intent it = new Intent(getBaseContext(), MainActivity.class);
                                                        int nivelCapturado =
                                                                Integer.parseInt(document.getData().get("nivelAcesso").toString());
                                                        String nomeUsuario =
                                                                document.getData().get("nome").toString();
                                                        String emailUsuario =
                                                                document.getData().get("email").toString();

                                                        it.putExtra("nivel", nivelCapturado);
                                                        it.putExtra("usuario", nomeUsuario);
                                                        it.putExtra("email", emailUsuario);
                                                        startActivity(it);
                                                        loadingProgressBar.setVisibility(View.GONE);
                                                        logado = 1;
                                                        finish();
                                                    }
                                                }
                                            } else {
                                                Log.w(TAG, "Error getting documents.", task.getException());
                                                Toast.makeText(LoginActivity.this,
                                                        "Error getting documents.",
                                                        Toast.LENGTH_LONG);
                                                loginButton.setEnabled(true);
                                                acionaRegistro.setEnabled(true);
                                                loadingProgressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.autenticacao_falhou,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            loginButton.setEnabled(true);
                            acionaRegistro.setEnabled(true);
                            loadingProgressBar.setVisibility(View.GONE);

                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(logado == 0){
            Intent it = new Intent(getBaseContext(), MainActivity.class);
            startActivity(it);
        }
        db.clearPersistence();
    }
}
