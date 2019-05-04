package hu.bme.aut.converterhomework;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stealth on 2017. 11. 21..
 */

public class ConverterFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 4;
    private Context context;
    List<Fragment> fragList = new ArrayList<Fragment>();

    public ConverterFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void addFragment(Fragment frag){
        fragList.add(frag);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.length);
            case 1:
                return context.getString(R.string.weight);
            case 2:
                return context.getString(R.string.area);
            case 3:
                return  context.getString(R.string.currency);
            default:
                return  context.getString(R.string.length);
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
