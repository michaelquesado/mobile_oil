package br.com.fjn.mobileoil.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by unobre on 26/10/2015.
 */
public class TelaConfigDAO extends DAO {

    public TelaConfigDAO(Context context) {
        super(context);
    }

    /**
     * @param tela o nome da tela que não se quer mais mostrar
     * @param flag boleano verdadeiro ou falso.
     */
    public void ocultarTela(String tela, boolean flag) {

        int exibir = (flag) ? 1 : 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE config_tela SET exibir=" + exibir + " WHERE tela='" + tela + "'";
        db.execSQL(sql);
        db.close();
    }

    public boolean isMostrarTela(String tela) {
        String[] coluna = {"exibir"};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("config_tela", coluna, "tela='login_inicial'", null, null, "", "");

        if (cursor.moveToFirst()) {
            int exibir = (int) (cursor.getInt(cursor.getColumnIndex("exibir")));
            return (exibir == 1) ? true : false;
        }

        return false;
    }
}
