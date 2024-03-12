package administratorpkg;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.tabs.TabLayout;

public class ManageStudent_classTeacher extends varchi_line {
    ViewPager vpager;
    TabLayout tabLayout;
    manage_activityforclassteacher_adapter myFragmentAdapter;
    @Override
    int getLayoutresId() {
        return R.layout.activity_manage_student_class_teacher;
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