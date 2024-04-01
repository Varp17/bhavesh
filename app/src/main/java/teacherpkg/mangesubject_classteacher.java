package teacherpkg;

import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teacherpkg.Subjects;
import teacherpkg.recyclerviewonclick;
import teacherpkg.teacher_classroomclicked;
import teacherpkg.teacherviewadapter;

public class mangesubject_classteacher extends varchi_line {


    private ArrayList<Subjects>subjectsArrayList;
    private ArrayList<String> subjectname;
    private ArrayList<String> teachername ;
    private RecyclerView recyclerview;
    Dialog myDialog ;

    ArrayList<String> teacherList,subjectList;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    Spinner spinnerYear,spinnerTeacher;

    FloatingActionButton openbtn;
    Button submitbtn;
    String selectedYear;
    String selectedTeacher;
    recyclerviewonclick recyclerviewonclick;
    @Override
    int getLayoutresId() {
        return R.layout.activity_mangesubject_classteacher;
    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "Manage subject Classroom";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataInitialize();



        recyclerview = findViewById(R.id.classroomrecyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerview.setHasFixedSize(true);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        // Set an OnRefreshListener to handle the refresh action
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Perform the actions you want to do when the user triggers a refresh
                // For example, reload data from the server
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
        });

         recyclerviewonclick=new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getApplicationContext(), teacher_classroomclicked.class);


                intent.putExtra("name", subjectname.get(position));




                startActivity(intent);
            }

        };



        openbtn = findViewById(R.id.addsubjectbtn);
        checkclassteacher();
        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView closebtn;
                myDialog = new Dialog(mangesubject_classteacher.this);
                myDialog.setContentView(R.layout.add_subject_floating_panel_teacher);
                closebtn = myDialog.findViewById(R.id.closeaddsubjectpanel);
                submitbtn=myDialog.findViewById(R.id.submit_subject_info_btn);

                myDialog.setCanceledOnTouchOutside(false);

                 spinnerTeacher = myDialog.findViewById(R.id.spinnerTeacher);
                 spinnerYear = myDialog.findViewById(R.id.spinnerYear);

                if(myDialog!=null){

                     subjectList = new ArrayList<>();
                    teacherList = new ArrayList<>();

                    // set subject spiner

                    SetSubjectList();
                    // set teachername spiner
                    setTeachernameList();

                    submitbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           addSubjectClassroom();
                        }
                    });







                    // Listener for Teacher Names spinner
                    spinnerTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                             selectedTeacher = (String) parent.getItemAtPosition(position);
                            // Handle the selected teacher as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Do nothing here if needed
                        }
                    });

// Listener for Subject Names spinner


// Listener for Years spinner
                    spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                             selectedYear = (String) parent.getItemAtPosition(position);
                            // Handle the selected year as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Do nothing here if needed
                        }
                    });
                }

                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });

                myDialog.show();
            }
        });


    }

    private void addSubjectClassroom() {



        DocumentReference dr=fstore.collection("classroom_subject").document(selectedYear);
        Map<String,Object> userInfo=new HashMap<>();
        userInfo.put("fullname",selectedTeacher);
        userInfo.put("class teacher",mAuth.getCurrentUser().getUid());

        dr.set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Subject Classroom Added", Toast.LENGTH_SHORT).show();
                    dataInitialize();
                }
            }
        });



    }

    private void setTeachernameList() {
        CollectionReference teacherref= fstore.collection("user");
        teacherref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult())
                    {
                        teacherList.add(documentSnapshot.getString("fullname"));
                    }
                    ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, teacherList);
                    teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTeacher.setAdapter(teacherAdapter);
                }
            }
        });
    }

    private void SetSubjectList() {

        DocumentReference classteacherref=fstore.collection("classteachers").document(mAuth.getCurrentUser().getUid());
        classteacherref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot!=null) {
                        String subdocid =documentSnapshot.getString("class year");


                                DocumentReference
                        drf = fstore.collection("subjects").document(subdocid);
                                drf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            if (documentSnapshot != null) {
                                                    int i=1;
                                                    while (i<=documentSnapshot.getLong("total"))
                                                    {
                                                        String sub=documentSnapshot.getString("sub"+i);
                                                        subjectList.add(sub);

                                                        i++;
                                                    }
                                                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, subjectList);
                                                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinnerYear.setAdapter(subjectAdapter);
                                                    i=1;

                                            }
                                        }

                                    }
                                });
                    }
                }
            }
        });

    }
    String classteacher;
    private void dataInitialize() {
        subjectsArrayList = new ArrayList<>();
        teachername = new ArrayList<>();
        subjectname = new ArrayList<>();

        CollectionReference classref = fstore.collection("classroom_subject");
        classref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        // Get the document reference
                        String classTeacher = document.getString("class teacher");
                        if (classTeacher != null && classTeacher.equals(mAuth.getCurrentUser().getUid())) {
                            subjectname.add(document.getId());
                            teachername.add(document.getString("fullname"));
                        }
                    }
                    // Initialize subjectsArrayList after data retrieval is complete
                    for (int i = 0; i < subjectname.size(); i++) {
                        Subjects subjects = new Subjects(subjectname.get(i), teachername.get(i));
                        subjectsArrayList.add(subjects);
                    }
                    managesubjectrecycler teacherViewAdapter = new managesubjectrecycler(mangesubject_classteacher.this,subjectsArrayList);
                    recyclerview.setAdapter(teacherViewAdapter);
                    teacherViewAdapter.notifyDataSetChanged();
                } else {
                    // Handle errors
                    Toast.makeText(mangesubject_classteacher.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    private void checkclassteacher() {
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String id = fAuth.getCurrentUser().getUid();
        DocumentReference dr = fstore.collection("classteachers").document(id);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // User is a class teacher
                    openbtn.setVisibility(View.VISIBLE);

                } else {
                    // User is not a class teacher
                    openbtn.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error occurred, hide the button
                openbtn.setVisibility(View.GONE);
            }
        });

    }



}
