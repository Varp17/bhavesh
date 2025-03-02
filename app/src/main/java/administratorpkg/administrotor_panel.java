package administratorpkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginform.R;
import com.example.loginform.student_login;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;

public class administrotor_panel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
//    ImageButton notification;

      ViewPager vpager;
      TabLayout tabLayout;
      FragmentAdapter myFragmentAdapter;

//      hello
      Menu menu;
      MenuItem menu_home,menu_classroom,menu_feedback,menu_logout,menu_notification;
      ImageView profileimg;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_administrotor_panel);
        connectionDetector connectionDetector = new connectionDetector(getApplicationContext());

        if (!connectionDetector.isConnectedToInternet()) {
            // Internet connection is available
            // Perform your network operations here
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText("No Internet Connection");
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        } else {
            // No internet connection
            // Display a message or take appropriate action
        }
        FirebaseApp.initializeApp(getApplicationContext());
//        notification=findViewById(R.id.notificationbtn);
        drawerLayout=findViewById(R.id.Drawer_lay);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);


//try {
//
//
//    FileInputStream serviceAccount =
//            new FileInputStream("/path/to/presencepro-40e3b-firebase-adminsdk-5dm9w-f182a89d1d.json");
//
//    FirebaseOptions options = new FirebaseOptions.Builder()
//            .setProjectId(String.valueOf(GoogleCredentials.fromStream(serviceAccount)))
//            .setDatabaseUrl("https://presencepro-40e3b-default-rtdb.firebaseio.com")
//            .build();
//
//
//    FirebaseApp.initializeApp(getApplicationContext());
//}catch (Exception e)
//{
//    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//}


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Clg Management Sys");

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

                }
                return false;
            }
        });

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

                Intent intent = new Intent(administrotor_panel.this, administrtor_profile.class);
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
        myFragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
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

            return true;
        }


        return super.onOptionsItemSelected(item);
    }



}
