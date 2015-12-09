package br.com.fjn.mobileoil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import br.com.fjn.mobileoil.models.Usuario;

/**
 * Created by unobre on 26/10/2015.
 */
public class DadosUsuario extends DAO {

    public static String tabela = "dados_usuario";

    public DadosUsuario(Context context) {
        super(context);
    }

    // Apagar todos os dados da tabela.
    // Salvar os novos dados o usuario na tabela.

    /**
     * Limpa os dados da tabela DADOS_USUARIO
     */
    public void limparTabela() {
        String sql = "DELETE * FROM " + DadosUsuario.tabela;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    //limpa a tabela que contém o id do usuário na hora que ele fizer o logout
    public void limparLogin(){
        String sql = "DELETE FROM checklogin";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    //faz a checagem do login na hora de add um valor
    public void checarLogin(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        db.insert("checklogin", "", cv);
        db.close();

        Log.i("DADOSSSSSS", id);
    }

    //retorna o id do usuario salvo no sqlite para comparação com o do mGoogleClient
    public String retornaIdSQlite(){
        String query = "SELECT id FROM checklogin";
        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery(query, null);
        cursor.moveToFirst();

        String nomeString = cursor.getString(cursor.getColumnIndex("id"));
        StringBuilder conversor = new StringBuilder();
        conversor.append(nomeString);

        return conversor.toString();
    }

    public boolean salvarUsuario(Usuario usuario) {

        // So permite um usuario na tabela do banco de dados.
        limparTabela();

        // Vai retornar o status da operacao (verdadeiro ou falso) em relacao
        // a insercao de um usuario na tabela dados usuario
        boolean status = false;

        // Obtem um statment de escrita com o banco
        SQLiteDatabase db = this.getWritableDatabase();

        // Esta estrutura fornece um mapeamento (chave - valor) para
        // utilizacao em consultas com o banco de dados
        ContentValues cv = new ContentValues();
        cv.put("id", usuario.getId());
        cv.put("nome", usuario.getNome());
        cv.put("email", usuario.getEmail());

        // Realiza a operacao de salvar
        long resultado = db.insert(DadosUsuario.tabela, "", cv);

        // Fecha a conexao com o banco de dados
        db.close();

        // Verifica se foi possivel salvar. Caso sim, muda o valor da variavel status para TRUE;
        // Nao eh necessario seta-lo como FALSE, pois esta variavel ja foi declarada assim.
        if (resultado > 0) {
            status = true;
        }

        // OUTPUT
        Log.i("DADOS_USUARIO", "Dados do usuario salvo: " + usuario.toString());

        return status;
    }
}
