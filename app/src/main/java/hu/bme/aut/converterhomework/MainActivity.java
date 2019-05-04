package hu.bme.aut.converterhomework;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import hu.bme.aut.converterhomework.fragments.AreaFragment;
import hu.bme.aut.converterhomework.fragments.ChangePasswordDialogFragment;
import hu.bme.aut.converterhomework.fragments.CurrencyFragment;
import hu.bme.aut.converterhomework.fragments.FavoritesDialogFragment;
import hu.bme.aut.converterhomework.fragments.LengthFragment;
import hu.bme.aut.converterhomework.fragments.WeightFragment;
import hu.bme.iit.dynamiccodedialog.CryptographyImplementation;

public class MainActivity extends AppCompatActivity implements FavoritesDialogFragment.FavoritePickedListener{

    public static final String PREFS_NAME = "ConverterPrefs";
    private static final String KEY_PREF_PASSWORD_CHECKED = "pref_password_checked";
    private int idNumLength;
    private int idNumWeight;
    private int idNumArea;
    private int idNumCurrency;
    private ViewPager localVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewPager vp = (ViewPager) findViewById(R.id.vpConverter);
        final TabLayout tl = (TabLayout) findViewById(R.id.converterTabs);
        tl.setupWithViewPager(vp);
        final ConverterFragmentPagerAdapter cpa = new ConverterFragmentPagerAdapter(getSupportFragmentManager(),this);
        cpa.addFragment(new LengthFragment());
        cpa.addFragment(new WeightFragment());
        cpa.addFragment(new AreaFragment());
        cpa.addFragment(new CurrencyFragment());
        vp.setAdapter(cpa);
        vp.setOffscreenPageLimit(5);

        localVp = vp;

        final String[] tabTitles = {getString(R.string.length),getString(R.string.weight),getString(R.string.area),getString(R.string.currency)};

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        final SharedPreferences.Editor spe = sp.edit();

        idNumLength = sp.getInt(getString(R.string.idNumLength),0);
        idNumWeight = sp.getInt(getString(R.string.idNumWeight),0);
        idNumArea = sp.getInt(getString(R.string.idNumArea),0);
        idNumCurrency = sp.getInt(getString(R.string.idNumCurrency),0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            String currentTabTitle = getPrefTabName(cpa.getPageTitle(tl.getSelectedTabPosition()).toString(),tabTitles);
            String currentConvertText = "";

            if(currentTabTitle.matches(getString(R.string.lengthPref))) {
                currentConvertText = LengthFragment.getCurrentConvertPref();

                spe.putInt(getString(R.string.idNumLength),idNumLength);
                spe.putString(currentTabTitle + idNumLength,currentConvertText);
                idNumLength++;
                Toast.makeText(getBaseContext(), currentConvertText + " " + getString(R.string.favAdded),Toast.LENGTH_SHORT).show();
                spe.apply();
            }

            else if (currentTabTitle.matches(getString(R.string.weightPref))){
                currentConvertText = WeightFragment.getCurrentConvertPref();

                spe.putInt(getString(R.string.idNumWeight),idNumWeight);
                spe.putString(currentTabTitle + idNumWeight,currentConvertText);
                idNumWeight++;
                Toast.makeText(getBaseContext(),currentConvertText + " " + getString(R.string.favAdded),Toast.LENGTH_SHORT).show();
                spe.apply();
            }

            else if (currentTabTitle.matches(getString(R.string.areaPref))){
                currentConvertText = AreaFragment.getCurrentConvertPref();

                spe.putInt(getString(R.string.idNumArea),idNumArea);
                spe.putString(currentTabTitle + idNumArea,currentConvertText);
                idNumArea++;
                Toast.makeText(getBaseContext(), currentConvertText + " " + getString(R.string.favAdded),Toast.LENGTH_SHORT).show();
                spe.apply();
            }

            else if (currentTabTitle.matches(getString(R.string.currencyPref))){
                currentConvertText = CurrencyFragment.getCurrentConvertPref();

                spe.putInt(getString(R.string.idNumCurrency),idNumCurrency);
                spe.putString(currentTabTitle + idNumCurrency,currentConvertText);
                idNumCurrency++;
                Toast.makeText(getBaseContext(), currentConvertText + " " + getString(R.string.favAdded),Toast.LENGTH_SHORT).show();
                spe.apply();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getBaseContext(), getString(R.string.addingToFavs),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        if (sp.contains(ChangePasswordDialogFragment.KEY_PREF_PASSWORD)){
            PasswordDialogPage passwordDialogPage = new PasswordDialogPage(this, R.style.LayoutTheme, new CryptographyImplementation(), this);
            passwordDialogPage.show();
        }
    }

    private String getPrefTabName(String s, String[] tabTitles) { //INFO: returns string for the SharedPreferences
        if(s .matches( tabTitles[0])) {
            return getString(R.string.lengthPref);
        }

        else if (s .matches( tabTitles[1])){
            return getString(R.string.weightPref);
        }

        else if (s .matches( tabTitles[2])){
            return getString(R.string.areaPref);
        }

        else if (s .matches( tabTitles[3])){
            return getString(R.string.currencyPref);
        }

        else return getString(R.string.nullPref);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorites) {

            new FavoritesDialogFragment().show(getSupportFragmentManager(),FavoritesDialogFragment.TAG);
            return true;
        }
        else if (id == R.id.action_code){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor spEdit = sp.edit();
            item.setChecked(sp.getBoolean(KEY_PREF_PASSWORD_CHECKED,false));
            if (!item.isChecked()){
                new ChangePasswordDialogFragment().show(getSupportFragmentManager(),ChangePasswordDialogFragment.TAG);
                item.setChecked(true);
            }
            else {
                spEdit.remove(ChangePasswordDialogFragment.KEY_PREF_PASSWORD);
                item.setChecked(false);
            }
            spEdit.putBoolean(KEY_PREF_PASSWORD_CHECKED,item.isChecked());
            spEdit.apply();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnFavoritePicked(String tab, String convertText) {
        if(tab.matches(getString(R.string.lengthPref))) {
            localVp.setCurrentItem(0);
            LengthFragment.setFavoriteConvert(convertText);
        }

        else if (tab.matches(getString(R.string.weightPref))){
            localVp.setCurrentItem(1);
            WeightFragment.setFavoriteConvert(convertText);
        }

        else if (tab.matches(getString(R.string.areaPref))){
            localVp.setCurrentItem(2);
            AreaFragment.setFavoriteConvert(convertText);
        }

        else if (tab.matches(getString(R.string.currencyPref))){
            localVp.setCurrentItem(3);
            if(!CurrencyFragment.getNoConnection())
                CurrencyFragment.setFavoriteConvert(convertText);
        }

    }
}
