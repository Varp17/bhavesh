package studentpakage;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class student_classroom_adapter extends FragmentPagerAdapter {
    public student_classroom_adapter (FragmentManager fm)
    {
        super(fm);

    }
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:  return new student_attendance_fragment1();
            case 1:  return new student_notification_fragment1();
            case 2:  return new student_document_fragment1();
            default: return new student_attendance_fragment1();
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

