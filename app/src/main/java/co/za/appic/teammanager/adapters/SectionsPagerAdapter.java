package co.za.appic.teammanager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

import co.za.appic.teammanager.fragments.BasePagerFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<BasePagerFragment> fragments = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();
    private final List<String> fragmentDescriptions = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(BasePagerFragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    public CharSequence getPageDescription(int position) {
        return fragmentDescriptions.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

