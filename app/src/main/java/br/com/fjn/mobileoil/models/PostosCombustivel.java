package br.com.fjn.mobileoil.models;

/**
 * Created by unobre on 22/08/2015.
 */
public class PostosCombustivel {
    private String idPosto;
    private int logo;
    private String nomePosto;
    private String endereco;
    private String dataAtualizacao;
    private String valorCombustivel;
    private String distanciaPosto;
    private String latLog;
    private String tipoCombustivel;

    public PostosCombustivel() {
    }

    public String getIdPosto() {
        return idPosto;
    }

    public void setIdPosto(String idPosto) {
        this.idPosto = idPosto;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getNomePosto() {
        return nomePosto;
    }

    public void setNomePosto(String nomePosto) {
        this.nomePosto = nomePosto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getValorCombustivel() {
        return valorCombustivel;
    }

    public void setValorCombustivel(String valorCombustivel) {
        this.valorCombustivel = valorCombustivel;
    }

    public String getDistanciaPosto() {
        return distanciaPosto;
    }

    public void setDistanciaPosto(String distanciaPosto) {
        this.distanciaPosto = distanciaPosto;
    }

    public String getLatLog() {
        return latLog;
    }

    public void setLatLog(String latLog) {
        this.latLog = latLog;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    @Override
    public String toString() {
        return "PostosCombustivel{" +
                "idPosto='" + idPosto + '\'' +
                ", logo=" + logo +
                ", nomePosto='" + nomePosto + '\'' +
                ", endereco='" + endereco + '\'' +
                ", dataAtualizacao='" + dataAtualizacao + '\'' +
                ", valorCombustivel='" + valorCombustivel + '\'' +
                ", distanciaPosto='" + distanciaPosto + '\'' +
                ", latLog='" + latLog + '\'' +
                ", tipoCombustivel='" + tipoCombustivel + '\'' +
                '}';
    }
}
