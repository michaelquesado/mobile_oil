package br.com.fjn.mobileoil.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import br.com.fjn.mobileoil.CombustivelActivity;

/**
 * Created by unobre on 17/08/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int PAGE_COUNT = CombustivelActivity.listaCombustiveis.size();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PAGE_COUNT = CombustivelActivity.listaCombustiveis.size();
        Log.i("UELIO_VIEWPA", "Position: " + position);

        if (CombustivelActivity.listaCombustiveis.get(position).getNome().equals("Alcool")) {
            FragmentListarAlcool fragmentListarAlcool = new FragmentListarAlcool();
            return fragmentListarAlcool;
        } else if (CombustivelActivity.listaCombustiveis.get(position).getNome().equals("Diesel")) {
            FragmentListarDiesel fragmentListarDiesel = new FragmentListarDiesel();
            return fragmentListarDiesel;
        } else if (CombustivelActivity.listaCombustiveis.get(position).getNome().equals("Gasolina")) {
            FragmentListarGasolina fragmentListarGasolina = new FragmentListarGasolina();
            return fragmentListarGasolina;
        } else {
            return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
