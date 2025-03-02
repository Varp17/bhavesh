package teacherpkg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.example.loginform.student_login;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import administratorpkg.feedback_activity_administrator;

public class teacher_panel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewPager vpager;
    TabLayout tabLayout;
    teacher_fragmentd_adapter myFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.teacher_color));
        setContentView(R.layout.activity_teacher_panel);

        drawerLayout = findViewById(R.id.Drawer_lay);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Clg Management Sys");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ImageView profileImg = navigationView.getHeaderView(0).findViewById(R.id.profileimg);
        profileImg.setOnClickListener(v -> {
            Intent intent = new Intent(teacher_panel.this, Teacher_Profile.class);
            startActivity(intent);
            finish();
        });

        vpager = findViewById(R.id.vpager);
        myFragmentAdapter = new teacher_fragmentd_adapter(getSupportFragmentManager());
        vpager.setAdapter(myFragmentAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubarteacher, menu);

        // Get menu items and set click listeners
        MenuItem menu_notification = menu.findItem(R.id.nav_notification);
        MenuItem menu_logout = menu.findItem(R.id.nav_logout);
        MenuItem menu_feedback = menu.findItem(R.id.nav_feedback);
        MenuItem menu_home = menu.findItem(R.id.nav_home);
        MenuItem menu_classroom = menu.findItem(R.id.nav_classroom);

        if (menu_notification != null) {
            menu_notification.setOnMenuItemClickListener(item -> {
                Intent intent1 = new Intent(getApplicationContext(), notification_teacher.class);
                startActivity(intent1);
                finish();
                return true;
            });
        }

        if (menu_logout != null) {
            menu_logout.setOnMenuItemClickListener(item -> {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), student_login.class));
                finish();
                return true;
            });
        }

        if (menu_feedback != null) {
            menu_feedback.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(getApplicationContext(), feedback_teacher.class);
                startActivity(intent);
                return true;
            });
        }

        if (menu_home != null) {
            menu_home.setOnMenuItemClickListener(item -> {
                drawerLayout.closeDrawer(GravityCompat.START);
                tabLayout.setScrollPosition(0, 0f, true);
                vpager.setCurrentItem(0);
                return true;
            });
        }

        if (menu_classroom != null) {
            menu_classroom.setOnMenuItemClickListener(item -> {
                drawerLayout.closeDrawer(GravityCompat.START);
                tabLayout.setScrollPosition(1, 0f, true);
                vpager.setCurrentItem(1);
                return true;
            });
        }

        return true;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (vpager.getCurrentItem() == 1) {
            tabLayout.setScrollPosition(0, 0f, true);
            vpager.setCurrentItem(0);
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }
}
