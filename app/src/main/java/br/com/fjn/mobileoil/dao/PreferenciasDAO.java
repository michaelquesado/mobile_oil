package br.com.fjn.mobileoil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.fjn.mobileoil.models.Combustivel;
import br.com.fjn.mobileoil.models.Preferencia;

/**
 * Created by unobre on 07/10/2015.
 */
public class PreferenciasDAO extends DAO {

    public static String tabela = "preferencias";

    public PreferenciasDAO(Context context) {
        super(context);
    }

    public List<Combustivel> getPreferencias() {

        List<Combustivel> combustivels = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] colunas = {"id", "combustivel", "mostrar"};
        Cursor cursor = db.query(tabela, colunas, "mostrar=1", null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Combustivel combustivel = new Combustivel();
                combustivel.setId(cursor.getInt(0));
                combustivel.setNome(cursor.getString(1));
                combustivel.setMostrar(cursor.getInt(2));
                combustivels.add(combustivel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return combustivels;
    }

    public void selecionado(String combustivel, int valor) {
        String sql = "UPDATE " + tabela + " SET mostrar = " + valor + " WHERE combustivel = '" + combustivel + "' LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void marcarOpcaoCombustivel(String combustivel, boolean status) {

        int mostrar = (status) ? 1 : 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE preferencias SET mostrar=" + mostrar + " WHERE combustivel='" + combustivel + "'";
        db.execSQL(sql);
        db.close();
    }

    public void atualizar(List<Preferencia> listaPreferencias) {

        SQLiteDatabase db = this.getWritableDatabase();
        resetarPreferencias(db);

        Log.i("PREFERENCIAS", "TOTAL DE PREFERENCIAS " + listaPreferencias.size());

        for (Preferencia p : listaPreferencias) {
            ContentValues cv = new ContentValues();
            cv.put("mostrar", p.getMostrar());

            int atualizar = db.update(tabela, cv, "combustivel = ?",
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

        String sql = "SELECT * FROM " + tabela;
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
