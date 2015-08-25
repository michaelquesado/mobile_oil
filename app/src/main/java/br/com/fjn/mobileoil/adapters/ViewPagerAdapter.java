package br.com.fjn.mobileoil.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by unobre on 17/08/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentListarAlcool fragmentListarAlcool = new FragmentListarAlcool();
                return fragmentListarAlcool;
            case 1:
                FragmentListarDiesel fragmentListarDiesel = new FragmentListarDiesel();
                return fragmentListarDiesel;
            case 2:
                FragmentListarGasolina fragmentListarGasolina = new FragmentListarGasolina();
                return fragmentListarGasolina;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
