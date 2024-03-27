package administratorpkg;

import static android.content.ContentValues.TAG;

import android.accounts.AccountManagerFuture;
import android.app.Dialog;
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
    private ArrayList<String> teacherId;
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

                        teachername.add(userName);
                        teacherId.add(userId); // Store the user ID
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
            Staff staff_names = new Staff(teachername.get(j), teacherId.get(j));
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
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = staff.getTeacherID();
                if (userId != null) {
                    // Get a reference to the Firestore collection containing user documents
                    usersCollectionRef = fstore.collection("user");

                    // Query for the specific user document using the user ID
                    userDocRef = usersCollectionRef.document(userId);

                    // Start a Firestore transaction
                    fstore.runTransaction(new Transaction.Function<Void>() {
                                @Nullable
                                @Override
                                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                                    // Delete the user document
                                    transaction.delete(userDocRef);

                                    // Also delete the authentication information
                                    // Note: You need to implement the logic to delete authentication info here
                                    // This could be using Firebase Authentication APIs to delete the user account
                                    // or using a separate authentication database where you store additional user information

                                    // For example, if you're using Firebase Authentication
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        user.delete()

                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User authentication information deleted.");
                                                            Toast.makeText(getContext(), "varun", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Log.e(TAG, "Error deleting user authentication information: " + task.getException());
                                                            // Handle error
                                                        }
                                                    }
                                                });
                                    }

                                    return null;
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Both operations succeeded
                                    Toast.makeText(getContext(), "User information deleted successfully", Toast.LENGTH_SHORT).show();
                                    // Remove the staff from the list and refresh the RecyclerView
                                    staffArrayList.remove(staff);
                                    recyclerview.getAdapter().notifyDataSetChanged();
                                    staffmanagedialog.dismiss(); // Dismiss the dialog after delete
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error occurred while deleting user document
                                    Log.e(TAG, "Error deleting user information: " + e.getMessage());
                                    Toast.makeText(getContext(), "Failed to delete user information", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // Handle the case where userId is null
                    Toast.makeText(getContext(), "User ID is null", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Set close button click listener
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffmanagedialog.dismiss(); // Dismiss the dialog
            }
        });

        staffmanagedialog.show();
    }


}