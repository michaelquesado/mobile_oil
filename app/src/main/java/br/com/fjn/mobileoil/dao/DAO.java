package br.com.fjn.mobileoil.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by unobre on 07/10/2015.
 */
public class DAO extends SQLiteOpenHelper {

    // Versão do banco de dados.
    private static final int DB_VERSION = 3;

    // Nome do banco de dados.
    private static final String DB_NAME = "mobileoildatabase";

    // Construtor.
    public DAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // PREFERENCIAS - TABELA E VALORES
        db.execSQL("CREATE TABLE preferencias (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, combustivel TEXT NOT NULL, mostrar INTEGER NOT NULL)");
        db.execSQL("INSERT INTO preferencias (combustivel, mostrar) VALUES ('Alcool', 0)");
        db.execSQL("INSERT INTO preferencias (combustivel, mostrar) VALUES ('Diesel', 0)");
        db.execSQL("INSERT INTO preferencias (combustivel, mostrar)VALUES('Gasolina', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PreferenciasDAO.nomeTabela + ";");
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
