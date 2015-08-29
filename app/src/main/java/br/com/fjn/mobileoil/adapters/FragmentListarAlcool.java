package br.com.fjn.mobileoil.adapters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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


/**
 * Created by unobre on 17/08/2015.
 */
public class FragmentListarAlcool extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private List<PostosCombustivel> list;
    private ListViewAdapterCombustivel adapterCombustivel;
    //private static String url = "http://192.168.0.102/testes/json/modelo_json_here_places.json";
    private static String url = "http://places.cit.api.here.com/places/v1/discover/explore?at=-7.27343152,-39.31797209&app_id=hG4gnJyrmlbNgGscL7Ki&app_code=h3XG36Nr4RgQOjymUTblJQ&tf=plain&size=30&pretty=true$tf=plain&cat=petrol-station";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_listar_alcool, container, false);
        mListView = (ListView) view.findViewById(R.id.listaPostos);

        // Populando meu listView
        list = new ArrayList<>();

        adapterCombustivel = new ListViewAdapterCombustivel();
        adapterCombustivel.setContext(getActivity().getBaseContext());
        mListView.setOnItemClickListener(this);
        new HttpAsyncTask().execute(url);
        return view;
    }

    // Método para tratar os evendos dos clicks nos itens da lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent it = new Intent(getActivity().getBaseContext(), PostoCombustivelDetalhes.class);

        // obtem o objeto do item clicado
        PostosCombustivel posto = list.get(position);
        it.putExtra("postoNome", posto.getNomePosto());
        it.putExtra("postoEndereco", posto.getEndereco());
        it.putExtra("postDataAtualizacao", posto.getDataAtualizacao());
        it.putExtra("postoValorCombustivel", posto.getValorCombustivel());
        it.putExtra("postoDistancia", posto.getDistanciaPosto());
        it.putExtra("postoTipoCombustivel", "Alcool");
        startActivity(it);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;

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

                JSONObject jsonObject = new JSONObject(result);
                JSONObject jsonObjectResults = jsonObject.getJSONObject("results");
                JSONArray jsonMainNodeArray = jsonObjectResults.getJSONArray("items");

                // Percorrendo o array JSON.
                int totalItens = jsonMainNodeArray.length();
                for (int i = 0; i < totalItens; i++) {

                    JSONObject jsonObjectPosto = jsonMainNodeArray.getJSONObject(i);

                    String postoNome = jsonObjectPosto.getString("title");
                    String postoEndereco = jsonObjectPosto.getString("vicinity");
                    String postoDataAtualizacao = "Ontem";
                    String postoValorCombustivel = "3.310";
                    String postoDistancia = FormatarDistancia.getDistanciaFormatada(jsonObjectPosto.getString("distance"));

                    PostosCombustivel p = new PostosCombustivel();
                    p.setNomePosto(postoNome);
                    p.setEndereco(postoEndereco);
                    p.setDataAtualizacao(postoDataAtualizacao);
                    p.setValorCombustivel(postoValorCombustivel);
                    p.setDistanciaPosto(postoDistancia);

                    if (!list.contains(p)) {
                        list.add(p);
                    }
                }

            } catch (JSONException e) {
                Log.e("FRAG_LIST_ALCOOL", "jsonexception");
                Log.e("FRAG_LIST_ALCOOL", e.getMessage());
                e.printStackTrace();
            }

            adapterCombustivel.setPostosCombustivelList(list);
            mListView.setAdapter(adapterCombustivel);

            // Retira a janela de progresso
            dialog.dismiss();
        }
    }
}