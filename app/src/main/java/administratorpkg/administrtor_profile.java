package administratorpkg;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class administrtor_profile extends varchi_line {

EditText profile_name,profile_id,profile_branch,profile_dob,profile_address,profile_mobno;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    TextView profilename;
int getProfile_values(){



    return 0;
}
    @Override
    public int getLayoutresId() {
        return R.layout.activity_administrtor_profile;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
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
        profilename=findViewById(R.id.student_profilename);

        DocumentReference df=fstore.collection("user").document(mAuth.getCurrentUser().getUid());
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // DocumentSnapshot contains the data of the document
                        String fullName = document.getString("fullname");
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

