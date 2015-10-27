package br.com.fjn.mobileoil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;
import java.util.List;

import br.com.fjn.mobileoil.dao.PreferenciasDAO;
import br.com.fjn.mobileoil.dao.TelaConfigDAO;
import br.com.fjn.mobileoil.models.Preferencia;

public class PreferenciasActivity extends Activity implements View.OnClickListener {


    private Button mBotaoContinuar;

    private CheckBox mPreferenciaAlcool;
    private CheckBox mPreferenciaDiesel;
    private CheckBox mPreferenciaGasolina;

    private boolean isAlcool;
    private boolean isDiesel;
    private boolean isGasolina;

    private TextView texto;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        initComponents();
        mBotaoContinuar.setOnClickListener(this);

        texto = (TextView) findViewById(R.id.tx);
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("NOME")) {
            String nome = bundle.getString("NOME");
            texto.setText("Bem vindo " + nome);
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        googleApiClient.connect();

        TelaConfigDAO telaConfigDAO = new TelaConfigDAO(this);
        if (!telaConfigDAO.isMostrarTela("preferencias_inicial"))

        {
            abrirTelaCombustiveis();
        }

    }


    private void setPreferencias() {
        isAlcool = mPreferenciaAlcool.isChecked();
        isDiesel = mPreferenciaDiesel.isChecked();
        isGasolina = mPreferenciaGasolina.isChecked();
    }

    // inicializa os componentes da view
    public void initComponents() {
        mBotaoContinuar = (Button) findViewById(R.id.continuar_preferencias);
        mPreferenciaAlcool = (CheckBox) findViewById(R.id.preferenciaAlcool);
        mPreferenciaDiesel = (CheckBox) findViewById(R.id.preferenciaDiesel);
        mPreferenciaGasolina = (CheckBox) findViewById(R.id.preferenciaGasolina);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferencias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent userProfile = new Intent(this, PerfilDoUsuario.class);
            startActivity(userProfile);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        setPreferencias();

        // Criando Preferencias para alcool;
        Preferencia prefAlcool = new Preferencia();
        prefAlcool.setCombustivel("Alcool");
        prefAlcool.setMostrar(isAlcool);

        // Criando Preferencias para diesel;
        Preferencia prefDiesel = new Preferencia();
        prefDiesel.setCombustivel("Diesel");
        prefDiesel.setMostrar(isDiesel);

        // Criando Preferencias para gasolina;
        Preferencia prefGasolina = new Preferencia();
        prefGasolina.setCombustivel("Gasolina");
        prefGasolina.setMostrar(isGasolina);

        // Criando DAO para salvar
        List<Preferencia> preferencias = new ArrayList<>();
        preferencias.add(prefAlcool);
        preferencias.add(prefDiesel);
        preferencias.add(prefGasolina);

        PreferenciasDAO prefDAO = new PreferenciasDAO(this);
        prefDAO.atualizar(preferencias);
        prefDAO.close();

        getPreferencesToString();

        TelaConfigDAO telaConfigDAO = new TelaConfigDAO(this);
        telaConfigDAO.ocultarTela("preferencias_inicial", false);
        telaConfigDAO.close();
    }

    private void abrirTelaCombustiveis() {
        Intent it = new Intent(this, CombustivelActivity.class);
        startActivity(it);
    }


    private void getPreferencesToString() {
        StringBuilder builder = new StringBuilder("Prefencias do usuario:: ");
        builder.append("isAlcool: " + isAlcool + "    ");
        builder.append("isDiesel: " + isDiesel + "    ");
        builder.append("isGasolina: " + isGasolina + "    ");
        Log.i("PREFERENCIAS", builder.toString());
    }
}