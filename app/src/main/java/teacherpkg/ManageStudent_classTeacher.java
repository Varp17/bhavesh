package teacherpkg;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.tabs.TabLayout;

import administratorpkg.manage_activityforclassteacher_adapter;


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
        return null;
    }

    @Override
    public int getCount() {
        return 0;
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