package teacherpkg;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.loginform.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link managestaffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class managestaffFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Staff>staffArrayList;
    private ArrayList<String> teachername;
    private ArrayList<String> teacherId,password,staffemail;
    private RecyclerView recyclerview;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private ArrayList<String> teacher;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog staffmanagedialog;
    private TextView tname;
    private FrameLayout overlay;
    private Task<QuerySnapshot> task;
    ProgressBar progressBar;
    CollectionReference usersCollectionRef;
    DocumentReference userDocRef;


    public managestaffFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassroomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static managestaffFragment newInstance(String param1, String param2) {
        managestaffFragment fragment = new managestaffFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



//        View view = inflater.inflate(R.layout.fragment_classroom, container, false);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_managestudent, container, false);
        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        teacher = new ArrayList<>();



        dataInitialize();

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        return rootView;
    }
    public void onRefresh() {
        // Implement logic to refresh the list of staff members
        // Fetch the latest data from the Firestore database

        dataInitialize();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Stop the refreshing indicator
                swipeRefreshLayout.setRefreshing(false);

                // Hide the ProgressBar when refresh is complete

            }
        }, 2000);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tname = view.findViewById(R.id.studentname);

        recyclerview = view.findViewById(R.id.managestudentrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);



        StaffAdapter staffAdapter = new StaffAdapter(getContext(), staffArrayList, new teacherpkg.recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                // Show the dialog box with the selected teacher's details
                Staff clickedStaff = staffArrayList.get(position);
                showStaffDialog(clickedStaff);
            }
        });
        recyclerview.setAdapter(staffAdapter);

        // Set an OnRefreshListener to handle the refresh action


//        ClassrromViewAdapter classrromViewAdapter = new ClassrromViewAdapter(getContext(),subjectsArrayList, recyclerviewonclick);
//        recyclerview.setAdapter(classrromViewAdapter);

    }


    private void dataInitialize() {
        teachername = new ArrayList<>();
        staffArrayList = new ArrayList<>();
        teacherId = new ArrayList<>(); // Initialize teacherId ArrayList
        password=new ArrayList<>();
        staffemail=new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DocumentReference docRef = fstore.collection("classteachers").document(mAuth.getCurrentUser().getUid().toString());


// Get the UID of the current user as a string
        String uidString = mAuth.getCurrentUser().getUid().toString();
        // Get user names
        CollectionReference userCollectionRef = docRef.collection("class_students");
        task = userCollectionRef.get(); // Initialize the task here
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("Fullname");
                        String userId = document.getId(); // Fetch the user ID
                        String pass=document.getString("password");
                        String email=document.getString("Email");
                        teachername.add(userName);
                        teacherId.add(userId); // Store the user ID
                        password.add(pass);
                        staffemail.add(email);
                    }
                    initializeAdapter();
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                    Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void initializeAdapter() {
        // Clear staffArrayList before initializing it
        staffArrayList.clear();

        // Iterate through subject names and teacher names to create Subjects objects
        for (int j = 0; j < teachername.size(); j++) {
            Staff staff_names = new Staff(teachername.get(j), teacherId.get(j),password.get(j), staffemail.get(j));
            staffArrayList.add(staff_names);
        }

        // Create and set adapter to RecyclerView
        StaffAdapter staffAdapter = new StaffAdapter(getContext(), staffArrayList, new recyclerviewonclick() {

            @Override
            public void onItemClick(int position) {
                // Handle item click here
                // Show the dialog box with the selected teacher's details
                Staff clickedStaff = staffArrayList.get(position);
                showStaffDialog(clickedStaff);
            }
        });
        recyclerview.setAdapter(staffAdapter);
    }

    private void showStaffDialog(Staff staff) {
        staffmanagedialog = new Dialog(getContext());
        staffmanagedialog.setContentView(R.layout.dialog_delete_student);
        TextView textViewStaffInfo = staffmanagedialog.findViewById(R.id.textViewStaffInfo);
        Button buttonDelete = staffmanagedialog.findViewById(R.id.buttonDelete);
        Button buttonClose = staffmanagedialog.findViewById(R.id.buttonClose);
        overlay = staffmanagedialog.findViewById(R.id.overlay_delete_dialog);
        progressBar = staffmanagedialog.findViewById(R.id.progressBar_delete);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        // Set staff information
        textViewStaffInfo.setText("Teacher Name: " + staff.getTeacherName()); // Assuming you have a getter method for teacher name in Staff class

        // Set delete button click listener
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDelete.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                // Delete user data
                String userId = staff.getTeacherID();

                // Get references to Firestore documents
                DocumentReference userDocRef = fstore.collection("classteachers").document(userId);
                CollectionReference userCollectionRef = userDocRef.collection("class_students");

                // Delete user document and class teacher document
                userDocRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    documentSnapshot.getReference().delete();
                                }

                                // Sign out current user
                                mAuth.signOut();

                                // Sign in with the email and password of the user being deleted
                                mAuth.signInWithEmailAndPassword(staff.getEmail(), staff.getpassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // User signed in successfully
                                        // Delete user account
                                        mAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Account deleted successfully
                                                staffmanagedialog.dismiss();
                                                Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();

                                                // Sign in with the previous admin account
                                                signInWithPreviousAdminAccount();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Failed to delete account
                                                Toast.makeText(getContext(), "Failed to delete account", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to sign in with user's credentials
                                        Toast.makeText(getContext(),staff.getEmail()+""+staff.getpassword(),Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getContext(), "Sign in failed", Toast.LENGTH_SHORT).show();

                                        mAuth.signInWithEmailAndPassword(getEmailFromSharedPreferences().toString(),getPasswordFromSharedPreferences().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                Toast.makeText(getContext(), "previos acc login", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "failed to login admin", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to get user collection
                                Toast.makeText(getContext(), "Failed to get user collection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete user document
                        Toast.makeText(getContext(), "Failed to delete user document", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set close button click listener
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffmanagedialog.dismiss();
            }
        });

        staffmanagedialog.show();
    }




    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CRED", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    // Method to retrieve password from SharedPreferences
    private String getPasswordFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CRED", Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }


    private void signInWithPreviousAdminAccount() {
        String previousEmail = getEmailFromSharedPreferences();
        String previousPassword = getPasswordFromSharedPreferences();

        mAuth.signInWithEmailAndPassword(previousEmail, previousPassword)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(getContext(), "Previous account login", Toast.LENGTH_SHORT).show();
                    dataInitialize();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to login with previous account", Toast.LENGTH_SHORT).show());
    }

}