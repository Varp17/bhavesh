package administratorpkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import administratorpkg.FragmentAdapter;

public class classroomClicked_activity extends varchi_line{


    private static final int MENU_ITEM_ITEM1 = 1;
    ViewPager vpager;
    TabLayout tabLayout;
    classroom_activity_adapter myFragmentAdapter;
    String title;

    @Override
    int getLayoutresId() {
        return R.layout.classroom_clicked_activity;
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

        vpager=findViewById(R.id.vpager);
        myFragmentAdapter=new classroom_activity_adapter(getSupportFragmentManager());
        vpager.setAdapter(myFragmentAdapter);
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