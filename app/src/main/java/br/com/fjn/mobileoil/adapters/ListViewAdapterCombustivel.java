package br.com.fjn.mobileoil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.fjn.mobileoil.R;
import br.com.fjn.mobileoil.models.PostosCombustivel;


public class ListViewAdapterCombustivel extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<PostosCombustivel> postosCombustivelList;

    public ListViewAdapterCombustivel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPostosCombustivelList(List<PostosCombustivel> list) {
        postosCombustivelList = list;
    }

    @Override
    public int getCount() {
        return postosCombustivelList.size();
    }

    @Override
    public Object getItem(int position) {
        return postosCombustivelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PostosCombustivel postoCombustivel = (PostosCombustivel) getItem(position);

        ViewHolderAdapterCategoria vhac;

        // Reciclando view.
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_view_adapter_combustivel, null, false);

            // Criando HolderView
            vhac = new ViewHolderAdapterCategoria();
            vhac.postoNome = (TextView) convertView.findViewById(R.id.nomePosto);
            vhac.postoEndereco = (TextView) convertView.findViewById(R.id.enderecoPosto);
            vhac.postoValorCombustivel = (TextView) convertView.findViewById(R.id.valorCombustivel);
            vhac.postoDataAtualizacao = (TextView) convertView.findViewById(R.id.dataAtualizacao);
            vhac.postoDistancia = (TextView) convertView.findViewById(R.id.dataAtualizacao);
            vhac.postoDataAtualizacao = (TextView) convertView.findViewById(R.id.distanciaPosto);
            convertView.setTag(vhac);
        } else {
            vhac = (ViewHolderAdapterCategoria) convertView.getTag();
        }

        // Setando os valores para os elementos da view xml.
        vhac.postoNome.setText(postoCombustivel.getNomePosto());
        vhac.postoEndereco.setText(postoCombustivel.getEndereco());
        vhac.postoValorCombustivel.setText(postoCombustivel.getValorCombustivel());
        vhac.postoDataAtualizacao.setText(postoCombustivel.getDataAtualizacao());
        vhac.postoDistancia.setText(postoCombustivel.getDistanciaPosto());

        if(postoCombustivel.getValorCombustivel() == "-1.0000");{
            vhac.postoValorCombustivel.setText("-");
        }
        return convertView;
    }

    static class ViewHolderAdapterCategoria {
        TextView postoNome;
        TextView postoEndereco;
        TextView postoDataAtualizacao;
        TextView postoValorCombustivel;
        TextView postoDistancia;
    }
}
