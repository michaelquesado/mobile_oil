package br.com.fjn.mobileoil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import br.com.fjn.mobileoil.dao.PreferenciasDAO;
import br.com.fjn.mobileoil.models.Combustivel;
import br.com.fjn.mobileoil.models.Preferencia;

public class Configuracoes extends Activity implements CompoundButton.OnCheckedChangeListener {

    private CheckBox alcool;
    private CheckBox diesel;
    private CheckBox gasolina;
    private PreferenciasDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        alcool = (CheckBox) findViewById(R.id.preferenciaAlcool);
        diesel = (CheckBox) findViewById(R.id.preferenciaDiesel);
        gasolina = (CheckBox) findViewById(R.id.preferenciaGasolina);

        alcool.setOnCheckedChangeListener(this);
        diesel.setOnCheckedChangeListener(this);
        gasolina.setOnCheckedChangeListener(this);


        configuraPreferencias();

        dao = new PreferenciasDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuracoes, menu);
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

    // obtem as preferencias do banco de dados
    public void configuraPreferencias() {
        PreferenciasDAO preferenciasDAO = new PreferenciasDAO(this);
        List<Combustivel> combustiveis = preferenciasDAO.getPreferencias();
        for (Combustivel c : combustiveis) {
            Log.i("CONFIGURACOES", c.getNome() + " " + c.getMostrar());

            if (c.getNome().toString().equals(alcool.getText().toString()) && c.getMostrar() == 1) {
                alcool.setChecked(true);
                Log.i("MOSTRAR", "Mostrar alcool");
            }

            if (c.getNome().toString().equals(diesel.getText().toString()) && c.getMostrar() == 1) {
                diesel.setChecked(true);
                Log.i("MOSTRAR", "Mostrar diesel");
            }

            if (c.getNome().toString().equals(gasolina.getText().toString()) && c.getMostrar() == 1) {
                gasolina.setChecked(true);
                Log.i("MOSTRAR", "Mostrar gasolina");
            }


        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String nomeBotao = buttonView.getText().toString();
        Log.i("Botao", nomeBotao + " " + isChecked);

        PreferenciasDAO dao = new PreferenciasDAO(this);
        dao.marcarOpcaoCombustivel(nomeBotao, isChecked);
    }

}
