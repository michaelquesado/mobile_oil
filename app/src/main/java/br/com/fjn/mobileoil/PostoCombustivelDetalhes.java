package br.com.fjn.mobileoil;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.ArrayList;
import java.util.List;

import br.com.fjn.mobileoil.adapters.ListViewAdapterCombustivel;
import br.com.fjn.mobileoil.dao.DadosUsuario;
import br.com.fjn.mobileoil.models.PostosCombustivel;
import br.com.fjn.mobileoil.utils.LatitudeLongitude;

public class PostoCombustivelDetalhes extends FragmentActivity implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private List<PostosCombustivel> list;

    private TextView mNomePosto;
    private TextView mTipoCombustivel;
    private TextView mValorCombustivel;
    private ListView mOutrosValoresProximos;
    private TextView mPostoDistancia;
    private TextView mPostoEndereco;
    private Button buttonTracarRota;

    private String postoId;
    private String postoNome;
    private String postoEndereco;
    private String postoDataAtualizacao;
    private String postoTipoCombustivel;
    private String postoValorCombustivel;
    private String postoDistancia;
    private String postoLatLog;
    private String postoCombustivelID;

    private Intent it;

    private GoogleMap googleMap;
    PostosCombustivel p;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posto_combustivel_detalhes);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        Intent it = getIntent();

        if (it != null) {
            initComponents();

            postoId = it.getStringExtra("idposto");
            postoNome = it.getStringExtra("postoNome");
            postoEndereco = it.getStringExtra("postoEndereco");
            postoDataAtualizacao = it.getStringExtra("postDataAtualizacao");
            postoValorCombustivel = it.getStringExtra("postoValorCombustivel");
            postoDistancia = it.getStringExtra("postoDistancia");
            postoLatLog = it.getStringExtra("postoLatLog");
            postoCombustivelID = it.getStringExtra("idcombustivel");



            mValorCombustivel.setText(postoValorCombustivel);
            // alterando a aparencia do texto
            Log.i("VALOR_COMBUSTIVEL", "valor do combustivel: " + postoValorCombustivel);
            // Listener em textview
            mValorCombustivel.setOnClickListener(this);

            mNomePosto.setText(postoNome);
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
            p.setIdPosto(postoId);
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
        String[] latlogPosto = postoLatLog.split(",");

        double latitude = Double.parseDouble(latlogPosto[0]);
        double longitude = Double.parseDouble(latlogPosto[1]);
        Log.d("LATLOG", latitude + "," + longitude);

        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(postoNome));

        String[] latlogUsuario = LatitudeLongitude.getLatitudeLongitude().split(",");
        latitude = Double.parseDouble(latlogUsuario[0]);
        longitude = Double.parseDouble(latlogUsuario[1]);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Você está aqui!"));
    }

    // Inicializa os componentes da View
    private void initComponents() {
        mNomePosto = (TextView) findViewById(R.id.nomePosto);
        mValorCombustivel = (TextView) findViewById(R.id.valorCombustivel);
        // mOutrosValoresProximos = (ListView) findViewById(R.id.postoOutrosPostos);
        mPostoDistancia = (TextView) findViewById(R.id.textView3);
        mPostoEndereco = (TextView) findViewById(R.id.endereco);
        buttonTracarRota = (Button) findViewById(R.id.buttonTracarRota);
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


    //Metodo chamado para traçar a rota da localização do usuário até o posto selecionado.
    public void traceRoute(View view) {
        String[] latlogPosto = postoLatLog.split(",");
        double latitude = Double.parseDouble(latlogPosto[0]);
        double longitude = Double.parseDouble(latlogPosto[1]);

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude + ""));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude + ""));
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, "Por favor, instale o aplicativo Google Maps para continuar!!", Toast.LENGTH_LONG).show();
            }
        }
    }


    // Método para tratar os evendos dos clicks nos itens da lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(getBaseContext(), PostoCombustivelDetalhes.class);

        // obtem o objeto do item clicado
        PostosCombustivel posto = list.get(position);
        it.putExtra("postoID", posto.getIdPosto());
        it.putExtra("postoNome", posto.getNomePosto());
        it.putExtra("postoEndereco", posto.getEndereco());
        it.putExtra("postDataAtualizacao", posto.getDataAtualizacao());
        it.putExtra("postoValorCombustivel", posto.getValorCombustivel());
        it.putExtra("postoDistancia", posto.getDistanciaPosto());
        it.putExtra("postoTipoCombustivel", "Alcool");
        startActivity(it);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.valorCombustivel) {
            if(mGoogleApiClient.isConnected() || mGoogleApiClient == null){
                DadosUsuario usuario = new DadosUsuario(getApplicationContext());
                String idGoogle = getIdUserGoogle();
                String idSqlite = usuario.retornaIdSQlite();
                if(idGoogle.equalsIgnoreCase(idSqlite)){
                    Toast.makeText(this, "Abrir janela de cadastro + " + postoId, Toast.LENGTH_SHORT).show();

                    // Abrir janela de cadastro de combustivel
                    Intent it = new Intent(this, CombustivelAdicionarValor.class);

                    // obtem o objeto do item clicado
                    it.putExtra("postoID", postoId);
                    it.putExtra("postoNome", postoNome);
                    it.putExtra("postoValorCombustivel", postoTipoCombustivel);
                    it.putExtra("postoTipoCombustivel", postoTipoCombustivel);
                    it.putExtra("postoCombustivelId", postoCombustivelID);

                    startActivity(it);

                }else{
                    Toast.makeText(getApplicationContext(),"cai fora", Toast.LENGTH_SHORT).show();
                }
            }else{
                onProviderDisabled();
            }
        }
    }

    public void onProviderDisabled() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Você precisa estar logado no aplicativo para poder vincular um valor a um posto. Deseja fazer isso agora?").setCancelable(false).setPositiveButton("Logar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent loginGoogle = new Intent(getApplicationContext(), LoginGoogle.class);
                startActivity(loginGoogle);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Ok, continue procurando preços.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop(){
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }


    public String getIdUserGoogle(){
        String id = "";
        Person userId = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if(userId != null){
            id = userId.getId();
        }
        return id;
    }



    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

