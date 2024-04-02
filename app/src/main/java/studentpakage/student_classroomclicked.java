package studentpakage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.tabs.TabLayout;
public class student_classroomclicked extends varchi_line{


    private static final int MENU_ITEM_ITEM1 = 1;
    ViewPager vpager;
    TabLayout tabLayout;
    student_classroom_adapter myFragmentAdapter1;
    String title;


    @Override
    int getLayoutresId() {
        return R.layout.activity_student_classroomclicked;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    String getactionbarTiile_in_varchi_line() {

        Intent intent=getIntent();
        this.title = intent.getStringExtra("name");
        return title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.student_color));

        vpager=findViewById(R.id.vpager);
        myFragmentAdapter1=new student_classroom_adapter(getSupportFragmentManager());
        vpager.setAdapter(myFragmentAdapter1);
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vpager);

        Intent intent=getIntent();

        boolean flag=intent.getBooleanExtra("flagfordocuments",false);

        boolean flagattend=intent.getBooleanExtra("flagforattendence",false);
        boolean atsameattend=intent.getBooleanExtra("atsameattend",false);
        boolean atsamedoc=intent.getBooleanExtra("atsamedoc",false);

        if(flag)
        {
            //document
            getSupportActionBar().setTitle("CLASS TEACHER'S");

            tabLayout.setScrollPosition(1, 0f, true);
            vpager.setCurrentItem(1);
        }   if(flagattend) {
            //attendence

            getSupportActionBar().setTitle("CLASS TEACHER'S");


        }if (atsameattend){

            tabLayout.setScrollPosition(0,0f,true);
            vpager.setCurrentItem(0);
        }if (atsamedoc){

            tabLayout.setScrollPosition(1,0f,true);
            vpager.setCurrentItem(1);
        }

    }


}