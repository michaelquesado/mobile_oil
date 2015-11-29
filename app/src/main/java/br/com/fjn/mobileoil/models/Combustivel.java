package br.com.fjn.mobileoil.models;

/**
 * Created by unobre on 19/10/2015.
 */
public class Combustivel {
    private long id;
    private String nome;
    private int mostrar;

    public Combustivel() {
    }

    public Combustivel(long id, String nome, int mostrar) {
        this.id = id;
        this.nome = nome;
        this.mostrar = mostrar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMostrar() {
        return mostrar;
    }

    public void setMostrar(int mostrar) {
        this.mostrar = mostrar;
    }

    @Override
    public String toString() {
        return "Combustivel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", mostrar=" + mostrar +
                '}';
    }
}
