package teacherpkg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.tabs.TabLayout;



public  class ManageStudent_classTeacher extends varchi_line {
    ViewPager vpager;
    TabLayout tabLayout;
    manage_activityforclassteacher_adapter myFragmentAdapter;


    @Override
    public int getLayoutresId() {
        return R.layout.activity_manage_student_class_teacher;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new fragment_createaccount_classteacher();
            case 1:
                return new managestaffFragment();
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

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "Manage Students";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vpager=findViewById(R.id.vpager);
        myFragmentAdapter=new manage_activityforclassteacher_adapter(getSupportFragmentManager());
        vpager.setAdapter(myFragmentAdapter);
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vpager);

    }
}