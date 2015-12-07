package br.com.fjn.mobileoil.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.fjn.mobileoil.PostoCombustivelDetalhes;
import br.com.fjn.mobileoil.R;
import br.com.fjn.mobileoil.models.PostosCombustivel;
import br.com.fjn.mobileoil.utils.FormatarDistancia;
import br.com.fjn.mobileoil.utils.JSONLoader;
import br.com.fjn.mobileoil.utils.LatitudeLongitude;


/**
 * Created by unobre on 17/08/2015.
 */
public class FragmentListarAlcool extends Fragment implements AdapterView.OnItemClickListener, LocationListener {

    private ListView mListView;
    private List<PostosCombustivel> list;
    private ListViewAdapterCombustivel adapterCombustivel;
    private final String TAG = "MO_FRAG_ALCOOL";
    //private static String url = "http://192.168.0.102/testes/json/modelo_json_here_places.json";
    private String url = "http://places.cit.api.here.com/places/v1/discover/explore?app_id=hG4gnJyrmlbNgGscL7Ki&app_code=h3XG36Nr4RgQOjymUTblJQ&tf=plain&size=25&pretty=true$tf=plain&cat=petrol-station&at=";
    private LocationManager locationManager;
    private String latlog = null;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_listar_alcool, container, false);
        mListView = (ListView) view.findViewById(R.id.listaPostos);

        /* Populando meu listView
        list = new ArrayList<>();

        adapterCombustivel = new ListViewAdapterCombustivel();
        adapterCombustivel.setContext(getActivity().getBaseContext());
        mListView.setOnItemClickListener(this);


        String latitudes = LatitudeLongitude.getLatitudeLongitude();
        String[] latlog = LatitudeLongitude.getLatitudeLongitude().split(",");
        url = "http://93.188.167.153/ws_mobile_oil/HereMaps/getPostos/lat/" + latlog[0] + "/long/" + latlog[1];

        new HttpAsyncTask().execute(url);
        */
        return view;
    }

    // Método para tratar os evendos dos clicks nos itens da lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent it = new Intent(getActivity().getBaseContext(), PostoCombustivelDetalhes.class);

        // obtem o objeto do item clicado
        PostosCombustivel posto = list.get(position);
        it.putExtra("postoId", posto.getIdPosto());
        it.putExtra("postoNome", posto.getNomePosto());
        //it.putExtra("postoEndereco", posto.getEndereco());
        //it.putExtra("postDataAtualizacao", posto.getDataAtualizacao());
        it.putExtra("postoValorCombustivel", posto.getValorCombustivel());
        //it.putExtra("postoDistancia", posto.getDistanciaPosto());
        it.putExtra("postoTipoCombustivel", "Alcool");
        it.putExtra("postoLatLog", posto.getLatLog());
        startActivity(it);
    }


    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            onProviderDisabled(LocationManager.GPS_PROVIDER);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChange");
        latlog = location.getLatitude() + "," + location.getLongitude();
        LatitudeLongitude.setLatitudeLongitude(latlog);
        Log.i(TAG, "onLocationChange :: " + LatitudeLongitude.getLatitudeLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "onProviderEnable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "onProviderDisable");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("O GPS está desligado, deseja ativa-lo agora?").setCancelable(false).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGpsSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGpsSettingIntent);
            }
        });
        alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Toast.makeText(getActivity().getApplicationContext(), "Para usar o APP é preciso ativar GPS.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * *********************************************************************
     * AsyncTask
     */

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        // Executa antes de iniciar uma Thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostra uma barra de progresso
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Carregando postos de combustiveis...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.show();
        }

        // Executa sobre uma Thread separada
        @Override
        protected String doInBackground(String... urls) {
            Log.e("MEU_JSON_ARRAY", "DO IN BACKGROUND - " + urls[0]);
            return JSONLoader.getJSON(urls[0]);
        }

        // Metodo executado na Thread principal.
        @Override
        protected void onPostExecute(String result) {
            try {

                JSONArray jsonArray = new JSONArray(result);
                Log.i("json", jsonArray.toString());

                //JSONObject jsonObjectResults = jsonObject.getJSONObject("results");
                //JSONArray jsonMainNodeArray = jsonObjectResults.getJSONArray("items");

                // Percorrendo o array JSON.
                int totalItens = jsonArray.length();
                for (int i = 0; i < totalItens; i++) {

                    JSONObject jsonObjectPosto = jsonArray.getJSONObject(i);
                    if (jsonObjectPosto.getString("combustivel").equals("Etanol")) {
                        String postoId = jsonObjectPosto.getString("id");
                        String postoNome = jsonObjectPosto.getString("nome");
                        String postoEndereco = jsonObjectPosto.getString("combustivel");
                        String latitude = jsonObjectPosto.getString("latitude");
                        String longitude = jsonObjectPosto.getString("longitude");
                        String postoDataAtualizacao = "Ontem";
                        String postoValorCombustivel = jsonObjectPosto.getString("valor");
                        Log.i("json", postoValorCombustivel);
                        //String postoDistancia = FormatarDistancia.getDistanciaFormatada(jsonObjectPosto.getString("distance"));

                        PostosCombustivel p = new PostosCombustivel();
                        p.setIdPosto(postoId);
                        p.setNomePosto(postoNome);
                        p.setEndereco(postoEndereco);
                        p.setDataAtualizacao(postoDataAtualizacao);
                        p.setValorCombustivel(postoValorCombustivel);
                        //p.setDistanciaPosto(postoDistancia);
                        p.setLatLog(latitude + "," + longitude);

                        if (!list.contains(p)) {
                            list.add(p);
                        }
                    }
                }

            } catch (JSONException e) {
                Log.e("FRAG_LIST_ALCOOL", "jsonexception :: " + e.getMessage());
                e.printStackTrace();
            }

            adapterCombustivel.setPostosCombustivelList(list);
            mListView.setAdapter(adapterCombustivel);

            // Retira a janela de progresso
            dialog.dismiss();
        }
    }
}