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
    private Tab tab;
    private LocationManager locationManager;
    private final String TAG = "ACT_COMBUSTIVEL";
    private PreferenciasDAO preferenciasDAO;
    private ActionBar.TabListener tabListener;

    static List<Combustivel> preferenciaCombustiveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combustivel);

        // obtendo uma lista de combustiveis selecionados nas preferencias
        preferenciasDAO = new PreferenciasDAO(this);
        preferenciaCombustiveis = preferenciasDAO.getPreferencias();

        // Ativando a navegação em modo de abas
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Localiza o ViewPager no layout activity_combustivel.xml
        mPager = (ViewPager) findViewById(R.id.pager);

        // Activate Fragment Manager
        FragmentManager fm = getSupportFragmentManager();

        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Find the ViewPager Position
                mActionBar.setSelectedNavigationItem(position);
            }
        };

        mPager.setOnPageChangeListener(ViewPagerListener);
        // Locate the adapter class classed ViewPagerAdapter.java
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fm);
        // Set the View Pager Adapter into ViewPager
        mPager.setAdapter(viewPagerAdapter);

        // Capture tab button clicks
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
            }
        };

        // output longitude e latitude
        Log.d(TAG, "Latitude Longitude: " + LatitudeLongitude.getLatitudeLongitude());
    }

    public void exibirTabs() {
        // remove todas as abas
        mActionBar.removeAllTabs();

        // obtem novamente as configurações
        CombustivelActivity.preferenciaCombustiveis = preferenciasDAO.getPreferencias();

        // popula as abas.
        for (Combustivel combustivel : preferenciaCombustiveis) {
            if (combustivel.getMostrar() == 1) {
                tab = mActionBar.newTab().setText(combustivel.getNome()).setTabListener(tabListener);
                mActionBar.addTab(tab);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");
        Log.i("combustivel", CombustivelActivity.preferenciaCombustiveis.toString());

        // Atualiza a visualização das tabs
        exibirTabs();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        exibirTabs();
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