package studentpakage;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

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
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.example.loginform.student_login;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;



public class student_panel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
//    ImageButton notification;

    ViewPager vpager;
    TabLayout tabLayout;
    student_fragmentadpter myFragmentAdapter;
    Menu menu;
    MenuItem menu_home;
    MenuItem menu_classroom,menu_documents,menu_attendence,menu_notification,menu_feedback,menu_logout;

    ImageView profileimg;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);
        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.student_color));
        drawerLayout=findViewById(R.id.Drawer_lay);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PresencePro");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ImageView profileImg = navigationView.getHeaderView(0).findViewById(R.id.profileimg);
        menu=navigationView.getMenu();
        menu_home=menu.findItem(R.id.nav_home);
        menu_classroom=menu.findItem(R.id.nav_classroom);
        menu_logout=menu.findItem(R.id.nav_logout);
        menu_feedback = menu.findItem(R.id.nav_feedback);
        menu_documents = menu.findItem(R.id.nav_document);
        menu_attendence = menu.findItem(R.id.nav_attendance);
        menu_notification = menu.findItem(R.id.nav_notification);


        menu_logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), student_login.class));
                finish();
                return false;
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(student_panel.this, student_profile.class);
                startActivity(intent);
                finishActivity(R.id.student_profilename);

            }
        });



        menu_feedback.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), student_feedback.class);
                startActivity(intent);
                return false;
            }
        });

        menu_home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                tabLayout.setScrollPosition(0,0f,true);
                vpager.setCurrentItem(0);
                return false;
            }
        });
        vpager=findViewById(R.id.vpager);
        myFragmentAdapter=new student_fragmentadpter(getSupportFragmentManager());
        vpager.setAdapter(myFragmentAdapter);
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vpager);
        menu_classroom.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                tabLayout.setScrollPosition(1,0f,true);
                vpager.setCurrentItem(1);
                return false;
            }
        });





//        menu_attendence.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem item) {
//
//
//                Intent intent=new Intent(getApplicationContext(), student_attendance_fragment1.class);
//                startActivity(intent);
//                finish();
//                drawerLayout.closeDrawer(GravityCompat.START);
//
//                return false;
//            }
//        });
//        menu_documents.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//
//            public boolean onMenuItemClick(@NonNull MenuItem item) {
//
//                drawerLayout.closeDrawer(GravityCompat.START);
//
//
//                Intent intent = new Intent(getApplicationContext(), student_document_fragment1.class);
//                intent.putExtra("flagfordocuments", true);
//
//                startActivity(intent);
//                finish();
//                return false;
//            }
//        });menu_notification.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//
//            public boolean onMenuItemClick(@NonNull MenuItem item) {
//
//                drawerLayout.closeDrawer(GravityCompat.START);
//
//
//                Intent intent = new Intent(getApplicationContext(), student_notification_fragment1.class);
//
//                startActivity(intent);
//                finish();
//                return false;
//            }
//        });









    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubarstudent, menu);

        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(vpager.getCurrentItem()==1){
            tabLayout.setScrollPosition(0,0f,true);
            vpager.setCurrentItem(0);

        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }


}