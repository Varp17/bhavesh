package teacherpkg;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import administratorpkg.Staff;
import administratorpkg.StaffAdapter;
import administratorpkg.recyclerviewonclick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_mangestudent_classteacher#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_mangestudent_classteacher extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private ArrayList<Student> studentArrayList;
    private ArrayList<String> studentname;
    private ArrayList<String> studentId,password,studentemail,enrollment;
    private RecyclerView recyclerview;
    private ArrayList<String> student;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog studentmanagedialog;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FrameLayout overlay;
    private FirebaseFirestore fstore;
    private Task<QuerySnapshot> task;


    public fragment_mangestudent_classteacher() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_mangestudent_classteacher.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_mangestudent_classteacher newInstance(String param1, String param2) {
        fragment_mangestudent_classteacher fragment = new fragment_mangestudent_classteacher();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mangestudent_classteacher, container, false);
        mAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();


        dataInitialize();
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutstudent);
        swipeRefreshLayout.setOnRefreshListener(this);

        return rootView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.managestudentrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        initializeAdapter();

        StudentAdapter studentAdapter = new StudentAdapter(getContext(), studentArrayList, new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                // Show the dialog box with the selected teacher's details
                Student clickedStudent = studentArrayList.get(position);

                Toast.makeText(getContext(),clickedStudent.toString(),Toast.LENGTH_SHORT).show();
                showStudentDialog(clickedStudent);
            }
        });
        recyclerview.setAdapter(studentAdapter);

    }
    //Data Initialization

    private void dataInitialize() {
        studentname = new ArrayList<>();
        studentArrayList = new ArrayList<>();
        studentId = new ArrayList<>(); // Initialize teacherId ArrayList
        password=new ArrayList<>();
        enrollment = new ArrayList<>();
        studentemail=new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser!=null){
            String classTeacherDocumentId = currentUser.getUid();
            CollectionReference userCollectionRef = fstore.collection("classteachers")
                    .document(classTeacherDocumentId)
                    .collection("class_students");

            Task<QuerySnapshot> task = userCollectionRef.get();
            task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String userName = document.getString("Fullname");
                            String userId = document.getId(); // Fetch the user ID
                            String pass=document.getString("password");
                            String email=document.getString("Email");
                            String enroll = document.getString("Enrollment");
                            studentname.add(userName);
                            studentId.add(userId); // Store the user ID
                            password.add(pass);
                            studentemail.add(email);
                            enrollment.add(enroll);
                        }
                        initializeAdapter();
                    } else {
                        Log.e("Firestore", "Error getting documents: ", task.getException());
                        Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.e("Firestore", "No user Login: ", task.getException());
            Toast.makeText(getContext(), "No user Login", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeAdapter() {
        studentArrayList.clear();

        for (int j = 0; j < studentname.size(); j++) {
            Student student_names = new Student(studentname.get(j), studentId.get(j),password.get(j), studentemail.get(j), enrollment.get(j));
            studentArrayList.add(student_names);
        }

        recyclerview = getView().findViewById(R.id.managestudentrecyclerview); // Initialize RecyclerView here
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        StudentAdapter studentAdapter = new StudentAdapter(getContext(), studentArrayList, new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                // Show the dialog box with the selected teacher's details
                Student clickedStudent = studentArrayList.get(position);
                showStudentDialog(clickedStudent);
            }
        });
        recyclerview.setAdapter(studentAdapter);
    }

    //Refresh
    public void onRefresh() {

        dataInitialize();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }



    private void showStudentDialog(Student student) {
        Toast.makeText(getContext(),student.studentname,Toast.LENGTH_SHORT).show();
        studentmanagedialog = new Dialog(getContext());
        studentmanagedialog.setContentView(R.layout.dialog_delete_staff);
        TextView textViewStudentInfo = studentmanagedialog.findViewById(R.id.textViewStaffInfo);
        Button buttonDelete = studentmanagedialog.findViewById(R.id.buttonDelete);
        Button buttonClose = studentmanagedialog.findViewById(R.id.buttonClose);
        overlay = studentmanagedialog.findViewById(R.id.overlay_delete_dialog);
        progressBar = studentmanagedialog.findViewById(R.id.progressBar_delete);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        textViewStudentInfo.setText("Teacher Name: " + student.getStudentname());

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDelete.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if(currentUser!=null) {
                    String classTeacherDocumentId = currentUser.getUid();

                    DocumentReference student_doc = fstore.collection("classteachers")
                            .document(classTeacherDocumentId)
                            .collection("class_students")
                            .document(student.getStudentId());

                    CollectionReference student_collection_attendance = fstore.collection("classteachers")
                            .document(classTeacherDocumentId)
                            .collection("class_students")
                            .document(student.getStudentId())
                            .collection("attendance");

                    student_collection_attendance.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Delete each document in the attendance collection
                                    document.getReference().delete();
                                }

                                // After deleting all documents, delete the attendance collection itself
                                student_collection_attendance.getParent().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Attendance collection successfully deleted
                                        Toast.makeText(getContext(),"Attendance collection successfully deleted!",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle any errors
                                        Toast.makeText(getContext(),"Error deleting attendance collection",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(),"Error getting documents: "+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    student_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                student_doc.delete();
                                mAuth.signOut();
                                mAuth.signInWithEmailAndPassword(student.getEmail(), student.getpassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        if(authResult!=null){
                                            mAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    studentmanagedialog.dismiss();
                                                    Toast.makeText(getContext(), "acc deleted", Toast.LENGTH_SHORT).show();

                                                    mAuth.signInWithEmailAndPassword(getEmailFromSharedPreferences(),getPasswordFromSharedPreferences()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                        @Override
                                                        public void onSuccess(AuthResult authResult) {
                                                            Toast.makeText(getContext(), "previos acc login", Toast.LENGTH_SHORT).show();
                                                            dataInitialize();
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
                                                    Toast.makeText(getContext(), "sign in failed", Toast.LENGTH_SHORT).show();
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
                                            });
                                            buttonClose.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    Toast.makeText(getContext(), student.getEmail()+" : "+student.getpassword(), Toast.LENGTH_SHORT).show();
                                                    studentmanagedialog.dismiss(); // Dismiss the dialog
                                                }

                                            });
                                            studentmanagedialog.show();
                                        }
                                    }
                                });
                            }
                        }
                    });

                }else{
                    Toast.makeText(getContext(), "Current user not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), student.getEmail()+" : "+student.getpassword(), Toast.LENGTH_SHORT).show();
                studentmanagedialog.dismiss(); // Dismiss the dialog


            }

        });
        studentmanagedialog.show();
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