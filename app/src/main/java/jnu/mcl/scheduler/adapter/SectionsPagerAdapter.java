package jnu.mcl.scheduler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jnu.mcl.scheduler.fragment.PersonalCalendarFragment;
import jnu.mcl.scheduler.fragment.ShareCalendarFragment;

/**
 * Created by 김 on 2015-11-30.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = PersonalCalendarFragment.newInstance();
        } else {
            fragment = ShareCalendarFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "개인 캘린더";
            case 1:
                return "공유 캘린더";

        }
        return null;
    }
}