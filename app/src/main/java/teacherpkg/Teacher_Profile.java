package teacherpkg;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Teacher_Profile extends varchi_line {

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    TextView profilename,teacherId,branch,dob,teacheremail,address,phoneno;


    int getProfile_values(){
        return 0;
    }
    @Override
    public int getLayoutresId() {
        return R.layout.activity_teacher_profile;

    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "PROFIlE";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        profilename = findViewById(R.id.teacher_name_profile);
        dob = findViewById(R.id.teacher_profile_dob);
        branch = findViewById(R.id.teacher_profile_address);
        teacherId = findViewById(R.id.profile_teacherid);
        phoneno = findViewById(R.id.teacher_profile_number);
        address = findViewById(R.id.teacher_profile_address);
        teacheremail = findViewById(R.id.teacher_profile_email);


        DocumentReference df = fstore.collection("user").document(mAuth.getCurrentUser().getUid());
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // DocumentSnapshot contains the data of the document
                        String fullName = document.getString("fullname");
                        profilename.setText(fullName);
                        String dobb = document.getString("DOB");
                        dob.setText(dobb);
                        branch.setText("Computer");
                        phoneno.setText(document.getString("mobile no"));
                        address.setText(document.getString("address"));
                        teacheremail.setText(document.getString("email"));

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

