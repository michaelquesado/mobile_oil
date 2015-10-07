package br.com.fjn.mobileoil.dao;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import br.com.fjn.mobileoil.models.Preferencia;

/**
 * Created by unobre on 07/10/2015.
 */
public class PreferenciasDAO extends DAO {

    public static String nomeTabela = "preferencias";

    public PreferenciasDAO(Context context) {
        super(context);
    }

    public void atualizar(List<Preferencia> listaPreferencias) {

        SQLiteDatabase db = this.getWritableDatabase();
        resetarPreferencias(db);

        Log.i("PREFERENCIAS", "TOTAL DE PREFERENCIAS " + listaPreferencias.size());

        for (Preferencia p : listaPreferencias) {
            ContentValues cv = new ContentValues();
            cv.put("mostrar", p.getMostrar());

            int atualizar = db.update(nomeTabela, cv, "combustivel = ?",
                    new String[]{p.getCombustivel()});
            Log.i("PREFERENCIAS", p.getCombustivel() + " PARA: " + p.getMostrar() + " VALOR ATUALIZAR: " + atualizar);
        }
        db.close();
    }

    public void resetarPreferencias(SQLiteDatabase db) {
        db = this.getWritableDatabase();
        db.execSQL("UPDATE preferencias SET mostrar = 0");
    }

    public void combustiveis() {

        String sql = "SELECT * FROM " + nomeTabela;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String str = "";
                str += "ID: " + cursor.getInt(0);
                str += " - COMBUSTIVEL: " + cursor.getString(1);
                str += " - MOSTRAR: " + cursor.getInt(2) + "";
                Log.i("P_BANCO", str);
            } while (cursor.moveToNext());
        }
    }

    @Override
    public synchronized void close() {
        super.close();

    }
}
