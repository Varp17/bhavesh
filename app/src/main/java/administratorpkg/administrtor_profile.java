package administratorpkg;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

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

public class administrtor_profile extends varchi_line {

EditText profile_name,profile_id,profile_branch,profile_dob,profile_address,profile_mobno;
int getProfile_values(){




    return 0;
}
    @Override
    public int getLayoutresId() {
        return R.layout.activity_administrtor_profile;

    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "PROFIlE";
    }


}

