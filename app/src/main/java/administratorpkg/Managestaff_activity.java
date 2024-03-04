package administratorpkg;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class Managestaff_activity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
//    ImageButton notification;

    ViewPager vpagermanage;
    TabLayout tabLayout;
    managestaffadapter managestaffadapter ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managestaff_activity);
//        notification=findViewById(R.id.notificationbtn);
        drawerLayout=findViewById(R.id.Drawer_lay);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Staff");


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        vpagermanage=findViewById(R.id.vpagermanage);
        managestaffadapter=new managestaffadapter(getSupportFragmentManager());
        vpagermanage.setAdapter(managestaffadapter);
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vpagermanage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubaradmin, menu);

        return true;

    }
}
