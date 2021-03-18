package com.example.mapp.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mapp.fragments.HealthFragment;
import com.example.mapp.fragments.QuotesFragment;
import com.example.mapp.fragments.TodoFragment;
import com.example.mapp.fragments.UrlsFragment;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] titles = {"Menu" , "My Quotes" , "My Works" , "My Wallpapers"};

    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0) return new HealthFragment();
        else if(position == 1) return new QuotesFragment();
        else if(position == 2) return new TodoFragment();
        else return new UrlsFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return 4;
    }

}
