package teacherpkg;

import static androidx.fragment.app.FragmentManager.TAG;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class View_Attendance_Activity_teacher extends varchi_line implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Student> studentArrayList;
    private ArrayList<String> studentname;
    private ArrayList<String> studentId,password,studentemail,enrollment;
    private RecyclerView recyclerview;
    private ArrayList<String> student;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog viewattendancedialog;
    ProgressBar progressBar;
    String classteacherid;
    private FirebaseAuth mAuth;
    private FrameLayout overlay;
    private FirebaseFirestore fstore;
    private Task<QuerySnapshot> task;
    private String subname;
    private String classTeacherDocumentId;
    private long attendanceCounte=0;


    @Override
    int getLayoutresId() {
        return R.layout.activity_view_attendance_teacher;
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
        return "Attendance Records";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.teacher_color));

        SharedPreferences sharedPreferences = getSharedPreferences("subname", Context.MODE_PRIVATE);
        subname = sharedPreferences.getString("subname", null);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        dataInitialize();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutstudent);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);

        recyclerview = findViewById(R.id.managestudentrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        initializeAdapter();
    }

    private void dataInitialize() {
        studentname = new ArrayList<>();
        studentArrayList = new ArrayList<>();
        studentId = new ArrayList<>();
        password = new ArrayList<>();
        studentemail = new ArrayList<>();
        enrollment = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            classTeacherDocumentId = currentUser.getUid();
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
                            String userId = document.getId();
                            String enroll = document.getString("Enrollment");
                            String pass = document.getString("password");
                            String email = document.getString("Email");
                            enrollment.add(enroll);
                            studentname.add(userName);
                            studentId.add(userId);
                            password.add(pass);
                            studentemail.add(email);
                        }
                        initializeAdapter();
                    } else {
                        Log.e("Firestore", "Error getting documents: ", task.getException());
                        Toast.makeText(View_Attendance_Activity_teacher.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.e("Firestore", "No user Login: ", task.getException());
            Toast.makeText(View_Attendance_Activity_teacher.this, "No user Login", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeAdapter() {
        studentArrayList.clear();

        for (int j = 0; j < studentname.size(); j++) {
            Student student_names = new Student(studentname.get(j), studentId.get(j), password.get(j), studentemail.get(j),enrollment.get(j));
            studentArrayList.add(student_names);
        }

        StudentAdapter studentAdapter = new StudentAdapter(this, studentArrayList, new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                Student clickedStudent = studentArrayList.get(position);
                showStudentDialog(clickedStudent);
            }
        });
        recyclerview.setAdapter(studentAdapter);
    }
    @Override
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
        Toast.makeText(this, subname, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, student.getStudentname(), Toast.LENGTH_SHORT).show();
        viewattendancedialog = new Dialog(this);
        viewattendancedialog.setContentView(R.layout.dialog_view_attendace_record);

        viewattendancedialog.setCancelable(false);
        viewattendancedialog.setCanceledOnTouchOutside(false);

        TextView stdname = viewattendancedialog.findViewById(R.id.stdname_view_attendance);
        TextView enroll_no_view_attendance = viewattendancedialog.findViewById(R.id.enroll_no_view_attendance);
        TextView percentagetext = viewattendancedialog.findViewById(R.id.percentagetext);

        stdname.setText(student.getStudentname());
        enroll_no_view_attendance.setText(student.getEnrollment());

        ProgressBar progressBar1 = viewattendancedialog.findViewById(R.id.progressBarAttendance);




        DocumentReference docRef = fstore.collection("classteachers")
                .document(classTeacherDocumentId)
                .collection("class_students")
                .document(student.getStudentId())
                .collection("attendance")
                .document(subname);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Get attendance data from Firestore
                    long presentCount = 0;
                    long totalCount = documentSnapshot.getData().size(); // Total number of attendance records


                    for (Map.Entry<String, Object> entry : documentSnapshot.getData().entrySet()) {
                        String attendanceStatus = (String) entry.getValue();
                        if (attendanceStatus.equals("P")) {
                            presentCount++;
                        }
                    }

                    // Calculate percentage
                    float percentage = (presentCount / (float) totalCount) * 100;

                    if (percentage < 75) {
                        percentagetext.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        progressBar1.getProgressDrawable().setColorFilter(getResources().getColor(android.R.color.holo_red_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        percentagetext.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                        progressBar1.getProgressDrawable().setColorFilter(getResources().getColor(android.R.color.holo_green_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                    Toast.makeText(getApplicationContext(),totalCount+"",Toast.LENGTH_SHORT).show();

                    ObjectAnimator.ofInt(progressBar1, "progress", 0, (int) percentage)
                            .setDuration(1000)
                            .start();

                    ValueAnimator animator = ValueAnimator.ofFloat(0f, percentage);
                    animator.setDuration(1000);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float progress = (float) animation.getAnimatedValue();
                            percentagetext.setText(String.format("%.2f%%", progress));
                        }
                    });
                    animator.start();

//                    percentagetext.setText(String.format("%.2f%%", percentage));
                } else {
                    Log.d(TAG, "Document does not exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error getting document", e);
            }
        });
        Button cloasebtn = viewattendancedialog.findViewById(R.id.cloasebtn);

        cloasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewattendancedialog.dismiss();
            }
        });


        viewattendancedialog.show();
    }

    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("CRED", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    private String getPasswordFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("CRED", Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }


}
