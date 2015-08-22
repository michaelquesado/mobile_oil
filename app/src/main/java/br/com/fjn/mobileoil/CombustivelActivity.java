package br.com.fjn.mobileoil;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import br.com.fjn.mobileoil.adapters.ViewPagerAdapter;

public class CombustivelActivity extends SherlockFragmentActivity {

    ActionBar mActionBar;
    ViewPager mPager;
    Tab tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combustivel);

        // Ativando a navegação em modo de abas
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Localiza o ViewPager no layout activity_combustivel.xml
        mPager = (ViewPager) findViewById(R.id.pager);

        // Activate Fragment Manager
        FragmentManager fm = getSupportFragmentManager();

        // Criando recurso para deslizar nas abas.
        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Procura a posição do ViewPager
                mActionBar.setSelectedNavigationItem(position);
            }
        };

        mPager.setOnPageChangeListener(ViewPagerListener);
        // Localiza a classe de adapter (ViewPager.java) e coloca como o adapter do ViewPager
        // TODO fazer com que a localização do usuário vá para as activites de viewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fm);
        // Seta o ViewPagerAdapter para o ViewPager
        mPager.setAdapter(viewPagerAdapter);

        // Captura os clicks nas abas
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
                // Pass the position on tab click to ViewPager
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
                // Utilizado para uma aba que perca o foco.
            }

            @Override
            public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
                // TODO Utilizado para, quando uma aba já esta selecionada
                // e o usuário clica nela novamente.
                // Podia fazer com que, se o usuário clicar nela novamente, o conteúda
                // da aba atualize os dados

            }
        };

        // Criando as abas
        // TODO Essas abas, só serão criadas de acordo com a seleção de preferências do usuário
        tab = mActionBar.newTab().setText("Alcool").setTabListener(tabListener);
        mActionBar.addTab(tab);

        tab = mActionBar.newTab().setText("Diesel").setTabListener(tabListener);
        mActionBar.addTab(tab);

        tab = mActionBar.newTab().setText("Gasolina").setTabListener(tabListener);
        mActionBar.addTab(tab);

    }
}

