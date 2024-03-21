package teacherpkg;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import administratorpkg.fragment_classroom;
import administratorpkg.homeFragment;

public class teacher_fragmentd_adapter extends FragmentPagerAdapter {
    public teacher_fragmentd_adapter(FragmentManager fm)
    {
        super(fm);

    }
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return  new teacher_home_fragment();
            case 1:return new teacher_classroom_fragment();
            default: return new teacher_home_fragment();
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
