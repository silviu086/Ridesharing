package com.example.silviu086.licenta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Silviu086 on 28.02.2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] titlu;
    private int nrTab;

    public ViewPagerAdapter(FragmentManager fm, CharSequence title[], int nrTab) {
        super(fm);
        this.titlu = title;
        this.nrTab = nrTab;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            LoginTabFragment loginFragment = new LoginTabFragment();
            return loginFragment;
        }
        else if(position == 1){
            RegisterTabFragment registerFragment = new RegisterTabFragment();
            return registerFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlu[position];
    }

    @Override
    public int getCount() {
        return this.nrTab;
    }
}
