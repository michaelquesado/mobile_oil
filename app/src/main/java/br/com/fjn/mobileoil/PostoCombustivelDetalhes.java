package br.com.fjn.mobileoil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.fjn.mobileoil.adapters.ListViewAdapterCombustivel;
import br.com.fjn.mobileoil.models.PostosCombustivel;

public class PostoCombustivelDetalhes extends FragmentActivity implements AdapterView.OnItemClickListener {

    private List<PostosCombustivel> list;

    private TextView mNomePosto;
    private TextView mTipoCombustivel;
    private TextView mValorCombustivel;
    private ListView mOutrosValoresProximos;
    private TextView mPostoDistancia;
    private TextView mPostoEndereco;

    private String postoNome;
    private String postoEndereco;
    private String postoDataAtualizacao;
    private String postoTipoCombustivel;
    private String postoValorCombustivel;
    private String postoDistancia;
    private String postoLatLog;

    private Intent it;

    private GoogleMap googleMap;
    PostosCombustivel p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posto_combustivel_detalhes);

        Intent it = getIntent();

        if (it != null) {
            initComponents();

            postoNome = it.getStringExtra("postoNome");
            postoEndereco = it.getStringExtra("postoEndereco");
            postoDataAtualizacao = it.getStringExtra("postDataAtualizacao");
            postoValorCombustivel = it.getStringExtra("postoValorCombustivel");
            postoDistancia = it.getStringExtra("postoDistancia");
            postoLatLog = it.getStringExtra("postoLatLog");

            mNomePosto.setText(postoNome);
            mValorCombustivel.setText(postoValorCombustivel);
            mPostoDistancia.setText(postoDistancia);
            mPostoEndereco.setText(postoEndereco);
        }

        // Adicionando outros postos de combustiveis;
        // Populando meu listView
        list = new ArrayList<>();
        // Criando postos para testes
        Context context = getBaseContext();
        for (int i = 0; i < 20; i++) {
            p = new PostosCombustivel();
            p.setNomePosto("Auto Posto Batateiras");
            p.setEndereco("Av. José Silva Santo, 15 Parque Recreio");
            p.setDataAtualizacao("Hoje");
            p.setValorCombustivel("3.399");
            p.setDistanciaPosto("1km");
            list.add(p);
        }

        // ListViewAdapterCombustivel adapterCombustivel = new ListViewAdapterCombustivel();
        // dapterCombustivel.setContext(this);
        // adapterCombustivel.setPostosCombustivelList(list);
        // mOutrosValoresProximos.setAdapter(adapterCombustivel);
        // mOutrosValoresProximos.setOnItemClickListener(this);

        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Faça uma verificação nula para verificar se já  temos  o mapa instanciado
        if (googleMap == null) {
            //Tentar obter o mapa do SupportMapFragment
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.postoLocalizacao)).getMap();
        }

        if (googleMap != null) {
            // Verifica se fomos bem sucedidos na obtenção do mapa
            setUpMap();
        }
    }

    private void setUpMap() {
        googleMap.setMyLocationEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        String[] latlog = postoLatLog.split(",");

        double latitude = Double.parseDouble(latlog[0]);
        double longitude = Double.parseDouble(latlog[1]);
        Log.d("LATLOG", latitude + "," + longitude);

        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Você está aqui!"));
    }

    // Inicializa os componentes da View
    private void initComponents() {
        mNomePosto = (TextView) findViewById(R.id.nomePosto);
        mValorCombustivel = (TextView) findViewById(R.id.valorCombustivel);
        // mOutrosValoresProximos = (ListView) findViewById(R.id.postoOutrosPostos);
        mPostoDistancia = (TextView) findViewById(R.id.textView3);
        mPostoEndereco = (TextView) findViewById(R.id.endereco);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posto_combustivel_detalhes, menu);
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

    // Método para tratar os evendos dos clicks nos itens da lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(getBaseContext(), PostoCombustivelDetalhes.class);

        // obtem o objeto do item clicado
        PostosCombustivel posto = list.get(position);
        it.putExtra("postoNome", posto.getNomePosto());
        it.putExtra("postoEndereco", posto.getEndereco());
        it.putExtra("postDataAtualizacao", posto.getDataAtualizacao());
        it.putExtra("postoValorCombustivel", posto.getValorCombustivel());
        it.putExtra("postoDistancia", posto.getDistanciaPosto());
        it.putExtra("postoTipoCombustivel", "Alcool");
        startActivity(it);
        finish();
    }
}
