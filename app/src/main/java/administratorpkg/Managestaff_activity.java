package administratorpkg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.tabs.TabLayout;

public class Managestaff_activity extends varchi_line {


//    ImageButton notification;

    ViewPager vpagermanage;
    TabLayout tabLayout;
    managestaffadapter managestaffadapter;


    @Override
    public int getLayoutresId() {
        return R.layout.managestaff_activity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "MANAGE STAFF";
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.managestaff_activity);
////        notification=findViewById(R.id.notificationbtn);
//        drawerLayout = findViewById(R.id.Drawer_lay);
//        navigationView = findViewById(R.id.nav_view);
//        toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Manage Staff");
//
//        navigationView.bringToFront();
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


        vpagermanage = findViewById(R.id.vpager);
        managestaffadapter = new managestaffadapter(getSupportFragmentManager());
        vpagermanage.setAdapter(managestaffadapter);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vpagermanage);
//        menu = navigationView.getMenu();
//        menu_home = menu.findItem(R.id.nav_home);
//        menu_classroom = menu.findItem(R.id.nav_classroom);
//        menu_home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem item) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(getApplicationContext(), administrotor_panel.class);
//                startActivity(intent);
//
//                return false;
//            }
//        });
//        menu_classroom.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem item) {
//                Intent intent = new Intent(getApplicationContext(), administrotor_panel.class);
//                startActivity(intent);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                tabLayout.setScrollPosition(1, 0f, true);
//                vpagermanage.setCurrentItem(1);
//                return false;
//            }
//        });

    }
}

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menubaradmin, menu);
//
//        return true;
//
//    }



