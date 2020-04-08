package com.example.moviecatalogueconsumer.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviecatalogueconsumer.R;
import com.example.moviecatalogueconsumer.ui.fragment.MovieListFragment;
import com.example.moviecatalogueconsumer.ui.fragment.TvShowListFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    MovieListFragment movieListFragment = new MovieListFragment();
    TvShowListFragment tvShowListFragment = new TvShowListFragment();

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_movie, R.string.tab_text_tvshow};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if (position == 1){
            return tvShowListFragment;
        }

        return movieListFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}