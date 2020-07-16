package com.itbbelval.expotec;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.itbbelval.expotec.ui.login.LoginActivity;
import com.itbbelval.expotec.utils.Alerta;

import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static com.itbbelval.expotec.R.layout.activity_main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView btnSaberMais;
    private int nivelAcesso = -1;
    private Menu menuNav;
    private MenuItem nav_item_events;
    private MenuItem nav_item_units;
    private MenuItem nav_item_map;
    private MenuItem nav_item_unit2;
    private MenuItem nav_item_coupons;
    private MenuItem nav_item_reader;
    private MenuItem nav_item_sort;
    private MenuItem nav_item_tools;
    private MenuItem nav_item_about;
    private MenuItem nav_item_login;
    private MenuItem nav_item_exit;

    private NavigationView navigationView;
    private String nomeUsuario;
    private String emailUsuario;
    private TextView mTextMessage;
//    private TextView txtSaberMais;
    private CheckBox checkBox;
    private String aceitou;

    private WebView wvExpotec, wvPolitica;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        checkBox = findViewById(R.id.chk_politica);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mTextMessage = findViewById(R.id.mTextMessage);
        menuNav = navigationView.getMenu();
        nav_item_events = menuNav.findItem(R.id.nav_events);
        nav_item_units = menuNav.findItem(R.id.nav_units);
        nav_item_map = menuNav.findItem(R.id.nav_map);
        nav_item_coupons = menuNav.findItem(R.id.nav_coupons);
        nav_item_login = menuNav.findItem(R.id.nav_login);
        nav_item_exit = menuNav.findItem(R.id.nav_exit);
        nav_item_tools = menuNav.findItem(R.id.nav_tools);
        nav_item_about = menuNav.findItem(R.id.nav_about);
        nav_item_reader = menuNav.findItem(R.id.nav_reader);
        nav_item_sort = menuNav.findItem(R.id.nav_sort);
        nav_item_unit2 = menuNav.findItem(R.id.nav_units2);

        criarBD();

        criarTabela();

        if("NÃO".equals(consultarAceitouPolitica()))
            inserirAceite();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(aceitou.equals("NÃO")) {
                        //inserirAceite();
                        aceitarPolitica(v);
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(
                                MainActivity.this,
                                R.style.dialog);
                        AlertDialog dialog =
                                builder.setMessage(R.string.dialog_login)
                                .setTitle(R.string.app_name)
                                .setIcon(R.drawable.logo_expotec)
                                .create();
                        dialog.show();*/
                        Alerta.mostra(MainActivity.this,
                                R.style.dialog,
                                R.string.dialog_login,
                                R.string.app_name,
                        R.drawable.logo_expotec);
                    }
                    else
                        rejeitarPolitica(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /*txtSaberMais = findViewById(R.id.txt_see_more);
        txtSaberMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(),
                        SaberMaisActivity.class);
                startActivity(it);
            }
        });
*/
        wvExpotec = findViewById(R.id.wv_expotec);
        String text = "<!DOCTYPE html><html>" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                "</head>" +
                "<body>"
                + "<p align=\"justify\"" +
                " style=\"background-color:#6F1F41;" +
                "color:White;font-size:20px;padding:20px;\";>"
                + getString(R.string.descricao_expotec_card)
                + "</p> "
                + "</body></html>";

        wvExpotec.loadData(text, "text/html", "utf-8");

        wvPolitica = findViewById(R.id.wv_politica);
        text = "<!DOCTYPE html><html>" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                "</head>" +
                "<body>"
                + "<p align=\"justify\"" +
                " style=\"font-size:20px;padding:20px;\";>"
                + getString(R.string.str_politica)
                + "</p> "
                + "</body></html>";

        wvPolitica.loadData(text, "text/html", "utf-8");

    }

    public void criarBD(){
        //Realizando a criação do BD no modo privado
        SQLiteDatabase banco = openOrCreateDatabase("FIEB_EXPOTEC.db",
                Context.MODE_PRIVATE, null);

        banco.close();
    }

    public void criarTabela(){
        SQLiteDatabase banco = openOrCreateDatabase("FIEB_EXPOTEC.db",
                Context.MODE_PRIVATE, null);

        //Criando a tabela
        StringBuilder sqlTabela = new StringBuilder();

        sqlTabela.append("CREATE TABLE IF NOT EXISTS [politica] (");
        sqlTabela.append("[_id] INTEGER PRIMARY KEY, ");
        sqlTabela.append("aceitou VARCHAR(3) ); ");

        banco.execSQL(sqlTabela.toString());

        banco.close();
    }

    public void inserirAceite(){
        SQLiteDatabase banco = openOrCreateDatabase("FIEB_EXPOTEC.db",
                Context.MODE_PRIVATE, null);

        ContentValues cvt = new ContentValues();
        cvt.put("aceitou", "NÃO");
        aceitou = "NÃO";

        //Validando a grava��o e retornando a mensagem de status
        if (banco.insert("politica", "_id", cvt) > 0) {
            //Toast.makeText(getBaseContext(), "Login cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Obrigado por aceitar nossa política de privacidade", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getBaseContext(), "Erro ao realizar Cadastro do Login!!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "FALHA", Toast.LENGTH_SHORT).show();
        }

        if (aceitou.equals("SIM")) {
            nav_item_login.setEnabled(false);
            nav_item_login.setVisible(false);
            nav_item_exit.setEnabled(true);
            nav_item_exit.setVisible(true);
            //checkBox.setEnabled(true);
        }
        else {
            nav_item_login.setEnabled(true);
            nav_item_login.setVisible(true);
            nav_item_exit.setEnabled(false);
            nav_item_exit.setVisible(false);
            //checkBox.setEnabled(false);
        }

        banco.close();

    }

    //Método de cadastramento de aceite da política de privacidade no banco de dados
    public void aceitarPolitica(View v) throws IOException {

        try {
            //Abrindo o banco de dados e coletando as informa��es digitadas
            db = openOrCreateDatabase("FIEB_EXPOTEC.db", Context.MODE_PRIVATE, null);
            ContentValues cvt = new ContentValues();
            int id = 1;
            cvt.put("aceitou", "SIM");
            aceitou = "SIM";

            //Validando a grava��o e retornando a mensagem de status
            if (db.update("politica", cvt,"_id=?",new String[]{String.valueOf(id)}) > 0) {
                //Toast.makeText(getBaseContext(), "Login cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                Snackbar.make(v.getRootView(), "Obrigado por aceitar nossa política de privacidade", Snackbar.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getBaseContext(), "Erro ao realizar Cadastro do Login!!!", Toast.LENGTH_SHORT).show();
                Snackbar.make(v.getRootView(), "FALHA", Snackbar.LENGTH_SHORT).show();
            }

            if (aceitou.equals("SIM")) {
                nav_item_login.setEnabled(true);
                nav_item_login.setVisible(true);
                nav_item_exit.setEnabled(true);
                nav_item_exit.setVisible(true);
                //checkBox.setEnabled(true);
            }
            else {
                nav_item_login.setEnabled(false);
                nav_item_login.setVisible(false);
                nav_item_exit.setEnabled(true);
                nav_item_exit.setVisible(true);
                //checkBox.setEnabled(false);
            }

        } catch (Exception ex) {
            //Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            Snackbar.make(v.getRootView(), ex.getMessage(), Snackbar.LENGTH_SHORT).show();
        }finally{
            db.close();
        }
    }

    //Método de cadastramento de aceite da política de privacidade no banco de dados
    public void rejeitarPolitica(View v) throws IOException {
        try {
            //Abrindo o banco de dados e coletando as informa��es digitadas
            db = openOrCreateDatabase("FIEB_EXPOTEC.db", Context.MODE_PRIVATE, null);
            ContentValues cvt = new ContentValues();
            int id = 1;
            cvt.put("aceitou", "NÃO");
            aceitou = "NÃO";

            //Validando a grava��o e retornando a mensagem de status
            if (db.update("politica", cvt,"_id=?",new String[]{String.valueOf(id)}) > 0) {
                //Toast.makeText(getBaseContext(), "Login cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                Snackbar.make(v.getRootView(), "Política de privacidade não aceita!", Snackbar.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getBaseContext(), "Erro ao realizar Cadastro do Login!!!", Toast.LENGTH_SHORT).show();
                Snackbar.make(v.getRootView(), "FALHA", Snackbar.LENGTH_SHORT).show();
            }

            if (aceitou.equals("SIM")) {
                nav_item_login.setEnabled(true);
                nav_item_login.setVisible(true);
                nav_item_exit.setEnabled(true);
                nav_item_exit.setVisible(true);
                //checkBox.setEnabled(true);
            }
            else {
                nav_item_login.setEnabled(false);
                nav_item_login.setVisible(false);
                nav_item_exit.setEnabled(true);
                nav_item_exit.setVisible(true);
                //checkBox.setEnabled(false);
            }

        } catch (Exception ex) {
            //Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            Snackbar.make(v.getRootView(), ex.getMessage(), Snackbar.LENGTH_SHORT).show();
        }finally{
            db.close();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            // Handle the camera action
            Intent it = new Intent(getBaseContext(), EventosActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_units) {
            Intent it = new Intent(getBaseContext(), UnidadeActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_map) {
            Intent it = new Intent(getBaseContext(), MapaActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_units2) {
            Intent it = new Intent(getBaseContext(), Unidade2Activity.class);
            startActivity(it);
        } else if (id == R.id.nav_coupons) {
            Intent it = new Intent(getBaseContext(),CupomActivity.class);
            it.putExtra("email", emailUsuario);
            it.putExtra("nome", nomeUsuario);
            startActivity(it);
        } else if (id == R.id.nav_reader) {
            Intent it = new Intent(getBaseContext(), ReaderActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_sort) {
            Intent it = new Intent(getBaseContext(), SorteioActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_tools) {
            Intent it = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_about) {
            Intent it = new Intent(getBaseContext(), AboutActivity.class);
            startActivity(it);
        }else if (id == R.id.nav_login) {
            // Chamar a janela de login para permitir entrada no App
            Intent it = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(it);
            finish();
        }else if (id == R.id.nav_exit) {

            // Encerra a atividade do menu lateral
            nav_item_events.setEnabled(true);
            nav_item_events.setVisible(true);
            nav_item_units.setEnabled(true);
            nav_item_units.setVisible(true);
            nav_item_map.setEnabled(true);
            nav_item_map.setVisible(true);
            nav_item_unit2.setVisible(false);
            nav_item_coupons.setEnabled(false);
            nav_item_coupons.setVisible(false);
            nav_item_reader.setEnabled(false);
            nav_item_reader.setVisible(false);
            nav_item_sort.setEnabled(false);
            nav_item_sort.setVisible(false);
            nav_item_tools.setEnabled(false);
            nav_item_tools.setVisible(false);
            nav_item_about.setEnabled(true);
            nav_item_about.setVisible(true);
            if (aceitou.equals("SIM")) {
                nav_item_login.setEnabled(true);
                nav_item_login.setVisible(true);
                nav_item_exit.setEnabled(true);
                nav_item_exit.setVisible(true);
            }
            else {
                nav_item_login.setEnabled(false);
                nav_item_login.setVisible(false);
                nav_item_exit.setEnabled(true);
                nav_item_exit.setVisible(true);
            }



            mTextMessage.setText("Não logado!");
            FirebaseAuth.getInstance().signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Verificar ser o aceitou está gravado como SIM
        aceitou = consultarAceitouPolitica();

        if (aceitou.toString().trim().equals("SIM")) {
            nav_item_login.setEnabled(true);
            nav_item_exit.setEnabled(true);
            nav_item_login.setVisible(true);
            nav_item_exit.setVisible(true);
            checkBox.setChecked(true);
        }
        else {
            nav_item_login.setEnabled(false);
            nav_item_exit.setEnabled(true);
            nav_item_login.setVisible(false);
            nav_item_exit.setVisible(true);
            checkBox.setChecked(false);
        }

        Intent it = getIntent();
        nivelAcesso = it.getIntExtra("nivel", -1);
        nomeUsuario = it.getStringExtra("usuario");
        emailUsuario = it.getStringExtra("email");


        if(nivelAcesso!= -1 && nomeUsuario != null){
            if(nivelAcesso==0){
                nav_item_events.setEnabled(true);
                nav_item_events.setVisible(true);
                nav_item_units.setEnabled(true);
                nav_item_units.setVisible(true);
                nav_item_map.setEnabled(true);
                nav_item_map.setVisible(true);
                nav_item_unit2.setVisible(false);
                nav_item_coupons.setEnabled(false);
                nav_item_coupons.setVisible(false);
                nav_item_reader.setEnabled(false);
                nav_item_reader.setVisible(false);
                nav_item_sort.setEnabled(false);
                nav_item_sort.setVisible(false);
                nav_item_tools.setEnabled(true);
                nav_item_tools.setVisible(true);
                nav_item_about.setEnabled(true);
                nav_item_about.setVisible(true);
                if (aceitou.equals("SIM")) {
                    nav_item_login.setEnabled(true);
                    nav_item_exit.setEnabled(true);
                    nav_item_login.setVisible(true);
                    nav_item_exit.setVisible(true);
                }
                else {
                    nav_item_login.setEnabled(false);
                    nav_item_exit.setEnabled(true);
                    nav_item_login.setVisible(false);
                    nav_item_exit.setVisible(true);
                }
                mTextMessage.setText("Bem vindo, " +
                        nomeUsuario.toString() + "!!!");
            }else if(nivelAcesso==1){
                nav_item_events.setEnabled(true);
                nav_item_events.setVisible(true);
                nav_item_units.setEnabled(true);
                nav_item_units.setVisible(true);
                nav_item_map.setEnabled(true);
                nav_item_map.setVisible(true);
                nav_item_unit2.setVisible(false);
                nav_item_coupons.setEnabled(false);
                nav_item_coupons.setVisible(false);
                nav_item_reader.setEnabled(false);
                nav_item_reader.setVisible(false);
                nav_item_sort.setEnabled(false);
                nav_item_sort.setVisible(false);
                nav_item_tools.setEnabled(false);
                nav_item_tools.setVisible(false);
                nav_item_about.setEnabled(true);
                nav_item_about.setVisible(true);
                if (aceitou.equals("SIM")) {
                    nav_item_login.setEnabled(true);
                    nav_item_exit.setEnabled(true);
                    nav_item_login.setVisible(true);
                    nav_item_exit.setVisible(true);
                }
                else {
                    nav_item_login.setEnabled(false);
                    nav_item_exit.setEnabled(true);
                    nav_item_login.setVisible(false);
                    nav_item_exit.setVisible(true);
                }
                mTextMessage.setText("Bem vindo, " +
                        nomeUsuario.toString() + "!!!");
            }
        }
    }

    //Realiza a consulta do usuário (email) na base de dados local
    public String consultarAceitouPolitica(){
        //Abrindo/Conectando no Banco
        SQLiteDatabase db = openOrCreateDatabase("FIEB_EXPOTEC.db", Context.MODE_PRIVATE, null);

        //Abrindo e apontando o cursor para o registro desejado (vamos selecionar via Select);
        Cursor cursor = db.rawQuery("Select aceitou from politica", null);
                //new String[]{""});

        //Caso o cursor tenha recuperado algum registro
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }else{
            return "NÃO";
        }
    }
}
