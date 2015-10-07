package br.com.fjn.mobileoil.models;

/**
 * Created by unobre on 07/10/2015.
 */
public class Preferencia {

    private long id;
    private String combustivel;
    private int mostrar;

    /**
     * Retorna o id de uma preferencia vinda do banco de dados.
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Seta um id de uma preferencia vinda do banco de dados.
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna o nome do combustivel
     *
     * @return String
     */
    public String getCombustivel() {
        return combustivel;
    }

    /**
     * Define um nome de combustivel
     *
     * @param combustivel
     */
    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    /**
     * Flag para mostrar ou não as abas de combustiveis para o usuario
     *
     * @return int
     */
    public int getMostrar() {
        return mostrar;
    }

    /**
     * <p>Define uma flag. Esta flag so pode ser 0 ou 1.</p>
     * <ul>
     * <li>0 - Nao mostra ao usuario</li>
     * <li>1 - Mostra ao usuario</li>
     * </ul>
     *
     * @param mostrar
     */
    public void setMostrar(boolean mostrar) {
        this.mostrar = (mostrar == true) ? 1 : 0;
    }
}