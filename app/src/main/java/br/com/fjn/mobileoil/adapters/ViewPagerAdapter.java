package br.com.fjn.mobileoil.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import br.com.fjn.mobileoil.CombustivelActivity;

/**
 * Created by unobre on 17/08/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int PAGE_COUNT = CombustivelActivity.listaCombustiveis.size();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PAGE_COUNT = CombustivelActivity.listaCombustiveis.size();
        Log.i("UELIO_VIEWPA", "Position: " + position);

        Log.e("VPA", "activity a chamar" + CombustivelActivity.listaCombustiveis.get(position).getNome());

        if (CombustivelActivity.listaCombustiveis.get(position).getNome().equals("Alcool")) {
            FragmentListarAlcool fragmentListarAlcool = new FragmentListarAlcool();
            Log.e("VPA", "ALCOOL" + PAGE_COUNT);
            Log.e("VPA", "ALCOOL" + PAGE_COUNT);
            return fragmentListarAlcool;
        } else if (CombustivelActivity.listaCombustiveis.get(position).getNome().equals("Diesel")) {
            FragmentListarDiesel fragmentListarDiesel = new FragmentListarDiesel();
            Log.e("VPA", "DIESEL" + PAGE_COUNT);
            return fragmentListarDiesel;
        } else if (CombustivelActivity.listaCombustiveis.get(position).getNome().equals("Gasolina")) {
            FragmentListarGasolina fragmentListarGasolina = new FragmentListarGasolina();
            Log.e("VPA", "GASOLINA" + PAGE_COUNT);
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
