package administratorpkg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import teacherpkg.fragment_createaccount_classteacher;
import teacherpkg.fragment_mangestudent_classteacher;

public class manage_activityforclassteacher_adapter extends FragmentPagerAdapter {

    public manage_activityforclassteacher_adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new fragment_createaccount_classteacher();
            case 1:
                return new fragment_mangestudent_classteacher();
            default:
               return new fragment_createaccount_classteacher();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0)
        {
            title="Create Account";
        }
        if(position==1)
        {
            title="Manage Student";
        }


        return title;
    }
}
