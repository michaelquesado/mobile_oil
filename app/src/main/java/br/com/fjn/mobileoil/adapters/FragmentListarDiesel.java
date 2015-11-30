package br.com.fjn.mobileoil.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.fjn.mobileoil.R;
import br.com.fjn.mobileoil.models.PostosCombustivel;


/**
 * Created by unobre on 17/08/2015.
 */
public class FragmentListarDiesel extends SherlockFragment {
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the view from fragmenttab2.xml
        View view = inflater.inflate(R.layout.activity_listar_diesel, container, false);
        mListView = (ListView) view.findViewById(R.id.listaPostos);

        // Populando meu listView
        List<PostosCombustivel> list = new ArrayList<>();
        // Criando postos para testes
        Context context = getSherlockActivity().getBaseContext();
        for (int i = 0; i < 20; i++) {
            PostosCombustivel p = new PostosCombustivel();
            p.setNomePosto("Auto Posto Batateiras");
            p.setEndereco("Av. José Silva Santo, 15 Parque Recreio");
            p.setDataAtualizacao("Hoje");
            p.setValorCombustivel("3.399");
            p.setDistanciaPosto("1km");
            list.add(p);
        }

        ListViewAdapterCombustivel adapterCombustivel = new ListViewAdapterCombustivel();
        adapterCombustivel.setContext(getActivity().getBaseContext());
        adapterCombustivel.setPostosCombustivelList(list);
        mListView.setAdapter(adapterCombustivel);
        return view;
    }
}
