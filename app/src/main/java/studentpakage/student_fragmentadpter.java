package studentpakage;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class student_fragmentadpter extends FragmentPagerAdapter {
    public student_fragmentadpter (FragmentManager fm)
    {
        super(fm);

    }
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return  new student_home_fragment();
            case 1:return new student_classroom_fragment();
            default: return new student_home_fragment();
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
