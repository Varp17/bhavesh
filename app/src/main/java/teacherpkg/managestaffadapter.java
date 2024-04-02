package teacherpkg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class managestaffadapter extends FragmentPagerAdapter {
    public managestaffadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return  new fragment_createaccount_classteacher();
            case 1:return new managestaffFragment();
            default: return new fragment_createaccount_classteacher();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0)
        {
            title="Create Account";
        }
        if(position==1)
        {
            title="Manage";
        }
        if(position==2)
        {
            title=" Notification";
        }

        return title;
    }
}
