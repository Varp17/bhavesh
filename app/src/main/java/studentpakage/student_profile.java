package studentpakage;

import static android.content.ContentValues.TAG;

import android.app.FragmentTransaction;
import android.credentials.CreateCredentialRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_profile extends varchi_line {

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    TextView profilename,studentId,branch,dob,studentemail,address,phoneno;


    int getProfile_values(){



        return 0;
    }
    @Override
    public int getLayoutresId() {
        return R.layout.activity_student_profile;

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
        return "PROFIlE";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        profilename = findViewById(R.id.student_name_profile);
        dob = findViewById(R.id.student_profile_dob);
        studentId = findViewById(R.id.profile_studentid);
        phoneno = findViewById(R.id.student_profile_number);
        address = findViewById(R.id.student_profile_address);
        studentemail = findViewById(R.id.student_profile_email);
        branch = findViewById(R.id.profile_branch_student);

        DocumentReference df=fstore.collection("classteachers").document().collection("class_students").document();

        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // DocumentSnapshot contains the data of the document
                        String fullName = document.getString("Fullname");
                        profilename.setText(fullName);
                        // Use the fullName as needed
                        Log.d(TAG, "Full Name: " + fullName);


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}

