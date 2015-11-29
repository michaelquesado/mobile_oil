package br.com.fjn.mobileoil.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import br.com.fjn.mobileoil.CombustivelActivity;
import br.com.fjn.mobileoil.models.Combustivel;

/**
 * Created by unobre on 17/08/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int size = CombustivelActivity.preferenciaCombustiveis.size();
    private FragmentManager fm;

    public ViewPagerAdapter(FragmentManager fm, String coordenadas) {
        super(fm);
    }

    public Fragment getItemByString(int position, String label) {
        return null;
    }

    @Override
    public Fragment getItem(int position) {


        if (CombustivelActivity.preferenciaCombustiveis.get(position).getNome() == "ALCOOL") {
            FragmentListarAlcool fragmentListarAlcool = new FragmentListarAlcool();
            Log.i("VIEW_ADAPTER", "tab alcool");
            return fragmentListarAlcool;
        } else if (CombustivelActivity.preferenciaCombustiveis.get(position).getNome() == "DIESEL") {
            FragmentListarDiesel fragmentListarDiesel = new FragmentListarDiesel();
            Log.i("VIEW_ADAPTER", "tab alcool");
            return fragmentListarDiesel;
        } else {
            FragmentListarGasolina fragmentListarGasolina = new FragmentListarGasolina();
            Log.i("VIEW_ADAPTER", "tab gasolina");
            return fragmentListarGasolina;
        }

/*
        switch (position) {

            case 0:
                FragmentListarAlcool fragmentListarAlcool = new FragmentListarAlcool();
                Log.i("VIEW_ADAPTER", "tab alcool");
                return fragmentListarAlcool;

            case 1:
                FragmentListarDiesel fragmentListarDiesel = new FragmentListarDiesel();
                Log.i("VIEW_ADAPTER", "tab diesel");
                return fragmentListarDiesel;

            case 2:
                FragmentListarGasolina fragmentListarGasolina = new FragmentListarGasolina();
                Log.i("VIEW_ADAPTER", "tab gasolina");
                return fragmentListarGasolina;
        }
        */
    }

    @Override
    public int getCount() {
        return size;
    }
}
