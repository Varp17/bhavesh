package com.example.loginform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm)
    {
        super(fm);

    }
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return  new homeFragment();
            case 1:return new fragment_classroom();
            default: return new homeFragment();
        }


    }

    public int getCount() {
        return 2;
    }


    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0)
        {
            title="HOME";
        }
        if(position==1)
        {
            title="CLASSROOM";
        }

        return title;
    }
}
