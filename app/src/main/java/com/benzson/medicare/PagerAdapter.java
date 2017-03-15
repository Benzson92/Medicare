package com.benzson.medicare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Benzson on 1/10/17.
 */

// http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Medilog medilog = new Medilog();
                return medilog;
            case 1:
                ListDiary listDiary = new ListDiary();
                return listDiary;
//            case 2:
//                ScanBarcode scanBarcode = new ScanBarcode();
//                return scanBarcode;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
