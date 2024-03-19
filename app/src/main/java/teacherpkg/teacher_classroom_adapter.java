package teacherpkg;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import administratorpkg.fragment_attendance_in_classroom;
import administratorpkg.fragment_classroom;
import administratorpkg.fragment_documnet_in_classroom;
import administratorpkg.fragment_notification_in_classroom;
import administratorpkg.homeFragment;

public class teacher_classroom_adapter extends FragmentPagerAdapter {
    public teacher_classroom_adapter(FragmentManager fm)
    {
        super(fm);

    }
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return  new teacher_fragment_attendance_classroom();
            case 1:return new teacher_fragment_document_classroom();
            case 2: return new teacher_fragment_document_classroom();
            default: return new teacher_fragment_attendance_classroom();
        }


    }

    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0)
        {
            title="ATTENDENCE";
        }
        if(position==1)
        {
            title="DOCUMENT";
        }
        if(position==2)
        {
            title="NOTIFICATION";
        }

        return title;
    }
}

