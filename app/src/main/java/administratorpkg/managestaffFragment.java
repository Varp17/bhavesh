package administratorpkg;

import static android.content.ContentValues.TAG;

import android.accounts.AccountManagerFuture;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_classroom#newInstance} factory method to
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
    private Task<QuerySnapshot> task;
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
    public static fragment_classroom newInstance(String param1, String param2) {
        fragment_classroom fragment = new fragment_classroom();
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
        View rootView = inflater.inflate(R.layout.fragment_managestaff, container, false);
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

        tname = view.findViewById(R.id.teachername);

        recyclerview = view.findViewById(R.id.managestaffrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

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
        // Get user names
        CollectionReference userCollectionRef = fstore.collection("user");
        task = userCollectionRef.get(); // Initialize the task here
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("fullname");
                        String userId = document.getId(); // Fetch the user ID
                        String pass=document.getString("password");
                        String email=document.getString("email");
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
        staffmanagedialog.setContentView(R.layout.dialog_delete_staff);
        TextView textViewStaffInfo = staffmanagedialog.findViewById(R.id.textViewStaffInfo);
        Button buttonDelete = staffmanagedialog.findViewById(R.id.buttonDelete);
        Button buttonClose = staffmanagedialog.findViewById(R.id.buttonClose);

        // Set staff information
        textViewStaffInfo.setText("Teacher Name: " + staff.getTeacherName()); // Assuming you have a getter method for teacher name in Staff class

        // Set delete button click listener
        // Set delete button click listener
        // Set delete button click listener
       buttonDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DocumentReference userdoc= fstore.collection("user").document(staff.getTeacherID());
               userdoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override

                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if(task.isSuccessful())
                       {
                           userdoc.delete();
                           mAuth.signOut();
                           mAuth.signInWithEmailAndPassword(staff.getEmail(), staff.getpassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                               @Override
                               public void onSuccess(AuthResult authResult) {
                                   if(mAuth!=null)
                                   {
                                       mAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void unused) {
                                               Toast.makeText(getContext(), "acc deleted", Toast.LENGTH_SHORT).show();

                                               mAuth.signInWithEmailAndPassword(getEmailFromSharedPreferences(),getPasswordFromSharedPreferences()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
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
                                       }).addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Toast.makeText(getContext(), "failed to delete acc", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   }
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getContext(), "sign in failed", Toast.LENGTH_SHORT).show();
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
                   }
               });

           }
       });



        // Set close button click listener
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), staff.getEmail()+" : "+staff.getpassword(), Toast.LENGTH_SHORT).show();
                staffmanagedialog.dismiss(); // Dismiss the dialog


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

}