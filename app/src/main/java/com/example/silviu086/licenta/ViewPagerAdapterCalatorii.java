package com.example.silviu086.licenta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Silviu086 on 01.06.2016.
 */
public class ViewPagerAdapterCalatorii extends FragmentStatePagerAdapter {
    private CharSequence[] titlu;
    private int nrTab;

    public ViewPagerAdapterCalatorii(FragmentManager fm, CharSequence title[], int nrTab) {
        super(fm);
        this.titlu = title;
        this.nrTab = nrTab;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            CalatoriiAdaugateFragment calatoriiAdaugateFragment = new CalatoriiAdaugateFragment();
            return calatoriiAdaugateFragment;
        }
        else if(position == 1){
            CalatoriiInAsteptareFragment calatoriiInAsteptareFragment = new CalatoriiInAsteptareFragment();
            return calatoriiInAsteptareFragment;
        }
        else if(position == 2){
            CalatoriiConfirmateFragment calatoriiConfirmateFragment = new CalatoriiConfirmateFragment();
            return calatoriiConfirmateFragment;
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
