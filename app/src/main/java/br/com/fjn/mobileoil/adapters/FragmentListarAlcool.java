package br.com.fjn.mobileoil.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.fjn.mobileoil.PostoCombustivelDetalhes;
import br.com.fjn.mobileoil.R;
import br.com.fjn.mobileoil.models.PostosCombustivel;


/**
 * Created by unobre on 17/08/2015.
 */
public class FragmentListarAlcool extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private List<PostosCombustivel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the view from fragmenttab2.xml
        View view = inflater.inflate(R.layout.activity_listar_alcool, container, false);
        mListView = (ListView) view.findViewById(R.id.listaPostos);

        // Populando meu listView
        list = new ArrayList<>();
        // Criando postos para testes
        Context context = getActivity().getBaseContext();
        for (int i = 0; i < 20; i++) {
            PostosCombustivel p = new PostosCombustivel();
            p.setNomePosto("Auto Posto Batateiras");
            p.setEndereco("Av. José Silva Santo, 15 Parque Recreio");
            p.setDataAtualizacao("Hoje");
            p.setValorCombustivel("3.399");
            p.setDistanciaPosto("1km");
            list.add(p);
        }

        ListViewAdapterCombustivel adapterCombustivel = new ListViewAdapterCombustivel(getActivity().getBaseContext(), list);
        mListView.setAdapter(adapterCombustivel);
        mListView.setOnItemClickListener(this);
        return view;
    }

    // Método para tratar os evendos dos clicks nos itens da lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity().getBaseContext(), "O item clicado foi: " + position, Toast.LENGTH_SHORT).show();
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
}
