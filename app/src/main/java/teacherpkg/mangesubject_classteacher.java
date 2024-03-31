package teacherpkg;

import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import teacherpkg.Subjects;
import teacherpkg.recyclerviewonclick;
import teacherpkg.teacher_classroomclicked;
import teacherpkg.teacherviewadapter;

public class mangesubject_classteacher extends varchi_line {


    private ArrayList<Subjects>subjectsArrayList;
    private String[] subjectname;
    private String[] teachername;
    private RecyclerView recyclerview;
    Dialog myDialog ;




    FloatingActionButton openbtn;

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

        recyclerviewonclick recyclerviewonclick=new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getApplicationContext(), teacher_classroomclicked.class);


                intent.putExtra("name",subjectname[position]);




                startActivity(intent);
            }

        };

        teacherviewadapter teacherViewAdapter = new teacherviewadapter(getApplicationContext(),subjectsArrayList, recyclerviewonclick);
        recyclerview.setAdapter(teacherViewAdapter);
        teacherViewAdapter.notifyDataSetChanged();

        openbtn = findViewById(R.id.addsubjectbtn);
        checkclassteacher();
        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView closebtn;
                myDialog = new Dialog(mangesubject_classteacher.this);
                myDialog.setContentView(R.layout.add_subject_floating_panel_teacher);
                closebtn = myDialog.findViewById(R.id.closeaddsubjectpanel);

                myDialog.setCanceledOnTouchOutside(false);

                Spinner spinnerTeacher = myDialog.findViewById(R.id.spinnerTeacher);
                Spinner spinnerYear = myDialog.findViewById(R.id.spinnerYear);

                if(myDialog!=null){

                    List<String> subjectList = Arrays.asList("MAD", "PHP", "Python", "ETI");
                    List<String> teacherList = Arrays.asList("Prerana Mam", "Vaishnavi Mam", "Trupti Mam", "Shirin Mam");
                    List<String> yearList = Arrays.asList("FY","SY","TY");

                    ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, teacherList);
                    teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTeacher.setAdapter(teacherAdapter);

                    ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, subjectList);
                    subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, yearList);
                    yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerYear.setAdapter(yearAdapter);

                    // Listener for Teacher Names spinner
                    spinnerTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedTeacher = (String) parent.getItemAtPosition(position);
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
                            String selectedYear = (String) parent.getItemAtPosition(position);
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
    private void dataInitialize(){
        subjectsArrayList = new ArrayList<>();
        subjectname = new String[]{
                "CLASS TEACHER'S",
                getString(R.string.sub1),
                getString(R.string.sub2),
                getString(R.string.sub3),
                getString(R.string.sub4),

        };
        teachername = new String[]{
                "Swapnil Sir",
                getString(R.string.tech1),
                getString(R.string.tech2),
                getString(R.string.tech3),
                getString(R.string.tech4)
        };
        for(int i=0;i<subjectname.length;i++){
            Subjects subjects = new Subjects((subjectname[i]),teachername[i]);
            subjectsArrayList.add(subjects);
        }

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
