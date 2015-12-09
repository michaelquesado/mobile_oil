package br.com.fjn.mobileoil.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by unobre on 07/10/2015.
 */
public class DAO extends SQLiteOpenHelper {

    // Versão do banco de dados.
    private static final int DB_VERSION = 9;

    // Nome do banco de dados.
    private static final String DB_NAME = "mobileoildatabase";

    // Construtor.
    public DAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // PREFERENCIAS DE COMBUSTIVEIS

        // Para armazenar os dados so usuário
        db.execSQL("CREATE TABLE dados_usuario (id INTEGER NOT NULL, nome TEXT NOT NULL, email TEXT NOT NULL)");

        //para checar o login na hora de adicionar um valor a um posto
        db.execSQL("CREATE TABLE checklogin (id TEXT NOT NULL)");

        // para armazenar as preferencias de combustiveis do usuario
        db.execSQL("CREATE TABLE preferencias (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, combustivel TEXT NOT NULL, mostrar INTEGER NOT NULL)");
        db.execSQL("INSERT INTO preferencias (combustivel, mostrar) VALUES ('Alcool', 0)");
        db.execSQL("INSERT INTO preferencias (combustivel, mostrar) VALUES ('Diesel', 0)");
        db.execSQL("INSERT INTO preferencias (combustivel, mostrar)VALUES('Gasolina', 1)");

        // EXIBICAO DAS TELAS
        db.execSQL("CREATE TABLE config_tela (id INTEGER PRIMARY KEY AUTOINCREMENT, tela TEXT, exibir INTEGER NOT NULL)");
        db.execSQL("INSERT INTO config_tela (tela, exibir) VALUES  ('login_inicial', 1)");
        db.execSQL("INSERT INTO config_tela (tela, exibir) VALUES  ('preferencias_inicial', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PreferenciasDAO.tabela + ";");
        db.execSQL("DROP TABLE IF EXISTS config_tela");
        db.execSQL("DROP TABLE IF EXISTS checklogin");
        db.execSQL("DROP TABLE IF EXISTS " + DadosUsuario.tabela);
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
