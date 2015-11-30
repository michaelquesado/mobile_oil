package br.com.fjn.mobileoil;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import java.util.List;

import br.com.fjn.mobileoil.adapters.ViewPagerAdapter;
import br.com.fjn.mobileoil.dao.PreferenciasDAO;
import br.com.fjn.mobileoil.models.Combustivel;
import br.com.fjn.mobileoil.utils.LatitudeLongitude;

public class CombustivelActivity extends SherlockFragmentActivity {

    private ActionBar mActionBar;
    private ViewPager mPager;
    private Tab tab1, tab2, tab3;
    private LocationManager locationManager;
    private final String TAG = "POSICAO_ACT_COMBUSTIVEL";
    private List<Combustivel> listaCombustiveis;
    private PreferenciasDAO prefDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combustivel);

        // Ativando a navegação em modo de abas
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        prefDAO = new PreferenciasDAO(this);
        listaCombustiveis = prefDAO.getPreferencias();

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
        tab1 = mActionBar.newTab().setText("Alcool").setTabListener(tabListener);
        mActionBar.addTab(tab1);

        //tab2 = mActionBar.newTab().setText("Diesel").setTabListener(tabListener);
        //mActionBar.addTab(tab2);

        //tab3 = mActionBar.newTab().setText("Gasolina").setTabListener(tabListener);
        //mActionBar.addTab(tab3);

        // output longitude e latitude
        Log.d(TAG, "Latitude Longitude: " + LatitudeLongitude.getLatitudeLongitude());
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        inflater.inflate(R.menu.menu_perfil_do_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_configuracoes:
                Intent it = new Intent(this, Configuracoes.class);
                startActivity(it);
                break;
            case R.id.action_perfil_user:
                Intent userProfile = new Intent(this, PerfilDoUsuario.class);
                startActivity(userProfile);
        }
        return false;
    }
}