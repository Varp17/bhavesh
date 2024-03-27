package teacherpkg;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import administratorpkg.all_college_notiFication_from_Administrator;
import administratorpkg.feedback_activity_administrator;

public class teacher_panel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
//    ImageButton notification;

    ViewPager vpager;
    TabLayout tabLayout;
    teacher_fragmentd_adapter myFragmentAdapter;
    Menu menu;
    MenuItem menu_home,menu_classroom,menu_feedback,menu_notification,menu_logout;
    ImageView profileimg;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_panel);
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
        menu_notification=menu.findItem(R.id.nav_notification);
        menu_logout=menu.findItem(R.id.nav_logout);


        menu_notification.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if(toolbar.getTitle()!="NOTIFICATIONS") {
                    Intent intent1 = new Intent(getApplicationContext(), all_college_notiFication_from_Administrator.class);
                    startActivity(intent1);
                    finish();
                }
                return false;
            }
        });

        menu_logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();
                return false;
            }
        });
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(teacher_panel.this, Teacher_Profile.class);
                startActivity(intent);
                finishActivity(R.id.managestaff);

            }
        });

        menu_feedback = menu.findItem(R.id.nav_feedback);

        menu_feedback.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), feedback_activity_administrator.class);
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
        myFragmentAdapter=new teacher_fragmentd_adapter(getSupportFragmentManager());
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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubaradmin, menu);


        return true;

    }
    boolean doubleBackToExitPressedOnce=false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(vpager.getCurrentItem()==1){
            tabLayout.setScrollPosition(0,0f,true);
            vpager.setCurrentItem(0);

        }else {
            doubleBackToExitPressedOnce = true;
            finish();
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle your menu items here
        if (id == R.id.nav_notification && toolbar.getTitle()!= "NOTIFICATIONS") {

            Intent intent=new Intent(getApplicationContext(), all_college_notiFication_from_Administrator.class);
            startActivity(intent);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}