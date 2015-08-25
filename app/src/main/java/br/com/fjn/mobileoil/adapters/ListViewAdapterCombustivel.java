package br.com.fjn.mobileoil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.fjn.mobileoil.R;
import br.com.fjn.mobileoil.models.PostosCombustivel;


public class ListViewAdapterCombustivel extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<PostosCombustivel> postosCombustivelList;

    public ListViewAdapterCombustivel(Context context, List<PostosCombustivel> postosCombustivelList) {
        this.context = context;
        this.postosCombustivelList = postosCombustivelList;
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

        // Obtem o objetivo de acordo com sua posição.
        // Observe que tenho que fazer um cast porque o método desta classe me retorna commo objeto.
        PostosCombustivel postoCombustivel = (PostosCombustivel) getItem(position);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolderAdapterCategoria vhac;

        // Agora vou reaproveitar algumas view para que o android não fique só criando.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_list_view_adapter_combustivel, null, false);

            // Instanciando e inicializando o Holder desta classe.
            vhac = new ViewHolderAdapterCategoria();
            vhac.postoIcone = (ImageView) convertView.findViewById(R.id.iconeLocalizacao);
            vhac.postoNome = (TextView) convertView.findViewById(R.id.nomePosto);
            vhac.postoEndereco = (TextView) convertView.findViewById(R.id.enderecoPosto);
            vhac.postoValorCombustivel = (TextView) convertView.findViewById(R.id.dataAtualizacao);
            vhac.postoDataAtualizacao = (TextView) convertView.findViewById(R.id.valorCombustivel);
            vhac.postoDistancia = (TextView) convertView.findViewById(R.id.dataAtualizacao);
            vhac.postoDataAtualizacao = (TextView) convertView.findViewById(R.id.distanciaPosto);

            // Setando os valores para os elementos da view xml.
            vhac.postoIcone.setImageResource(R.mipmap.ic_place_black_48dp);
            vhac.postoNome.setText(postoCombustivel.getNomePosto());
            vhac.postoEndereco.setText(postoCombustivel.getEndereco());
            vhac.postoValorCombustivel.setText(postoCombustivel.getValorCombustivel());
            vhac.postoDataAtualizacao.setText(postoCombustivel.getDataAtualizacao());
            vhac.postoDistancia.setText(postoCombustivel.getDistanciaPosto());
            convertView.setTag(vhac);
        } else {
            vhac = (ViewHolderAdapterCategoria) convertView.getTag();
        }
        return convertView;
    }

    // Esta técnica obtem um maior desempenho sobre os elementos
    // que o método getView retorna.
    // Se eu não utilizasse esta técnica, a cada item da lista o
    // método cria novas instancias dos itens contidas nela.
    // Com esta técnica, os componentes de view São criados e
    // depois reutilizados.
    // Ou seja, alem de reutilizar as vies da propria lista,
    // estou reaproveitando os elementos de view internos.

    static class ViewHolderAdapterCategoria {
        ImageView postoIcone;
        TextView postoNome;
        TextView postoEndereco;
        TextView postoDataAtualizacao;
        TextView postoValorCombustivel;
        TextView postoDistancia;
    }
}
