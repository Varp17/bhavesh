package teacherpkg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import administratorpkg.dategiver;

public class Take_Attendance_Activity extends varchi_line {

    TextView classname;
    TextView datetextview,textViewEnrollment,student_name;
    Button next,previous,presentbtn,absentbtn;
    String subname;
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    String classteacherid;
    ArrayList<String> studentnamelist,studentENR;
    ArrayList<String> prsent,absent;
    Spinner spinnerstudent;

    ArrayList<StudentAttendenceData> studentdataArrayList;
    private String selectedstudent;
    StudentAttendenceData studentdata;
    private int selecteditempos;
    private ArrayList<String> status;
    private TextView statustextview;

    @Override
    int getLayoutresId() {
        return R.layout.activity_take_attendance;
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
        return "Attendance";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.teacher_color));

        datetextview = findViewById(R.id.date);
        spinnerstudent=findViewById(R.id.spinnerStudents);
        textViewEnrollment=findViewById(R.id.textViewEnrollment);
        student_name=findViewById(R.id.student_name);
        next=findViewById(R.id.buttonNext);
        previous=findViewById(R.id.buttonPrevious);
        presentbtn=findViewById(R.id.buttonPresent);
        absentbtn=findViewById(R.id.buttonAbsent);
        statustextview=findViewById(R.id.status);




        SharedPreferences sharedPreferences = getSharedPreferences("subname", Context.MODE_PRIVATE);
        subname = sharedPreferences.getString("subname", null);

        getclassteacherid();
        if (datetextview != null) {
            datetextview.setText("Date: " + dategiver.getdate());
        }
        Toast.makeText(this, subname, Toast.LENGTH_SHORT).show();

        spinnerstudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selecteditempos=position;
                setinfo(position);
                statustextview.setText(studentdataArrayList.get(selecteditempos).status);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here if needed
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setinfo(selecteditempos);
                selecteditempos++;
                if (selecteditempos >= studentdataArrayList.size()) {
                    selecteditempos = 0;

                }


                if (selecteditempos < 0) {
                    selecteditempos = studentdataArrayList.size() - 1;

                }


                spinnerstudent.setSelection(selecteditempos);
                statustextview.setText(studentdataArrayList.get(selecteditempos).status);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setinfo(selecteditempos);
                selecteditempos--;
                if (selecteditempos >= studentdataArrayList.size()) {
                    selecteditempos = 0;

                }


                if (selecteditempos < 0) {
                    selecteditempos = studentdataArrayList.size() - 1;

                }


                spinnerstudent.setSelection(selecteditempos);
                statustextview.setText(studentdataArrayList.get(selecteditempos).status);
            }
        });
        presentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setinfo(selecteditempos);
                setPresent(selecteditempos);
                selecteditempos++;

                if (selecteditempos >= studentdataArrayList.size()) {
                    selecteditempos = 0;

                }


                if (selecteditempos < 0) {
                    selecteditempos = studentdataArrayList.size() - 1;

                }


                spinnerstudent.setSelection(selecteditempos);


                statustextview.setText("");


            }
        });
        absentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setinfo(selecteditempos);
                setAbsent(selecteditempos);
                selecteditempos++;
                if (selecteditempos >= studentdataArrayList.size()) {
                    selecteditempos = 0;

                }


                if (selecteditempos < 0) {
                    selecteditempos = studentdataArrayList.size() - 1;

                }

                spinnerstudent.setSelection(selecteditempos);


                statustextview.setText("");

            }
        });


    }

    private void setPresent(int position)
    {
        studentdataArrayList.get(position).status="p";

    }
    private void setAbsent(int position)
    {
            studentdataArrayList.get(position).status="Ab";

    }

    private void setinfo(int position) {

        if (position >= studentdataArrayList.size()) {
            selecteditempos = 0;
            position = 0;
        }


        if (position < 0) {
            selecteditempos = studentdataArrayList.size() - 1;
            position = studentdataArrayList.size() - 1;
        }


        student_name.setText(studentdataArrayList.get(position).name);
        textViewEnrollment.setText(studentdataArrayList.get(position).enr);
    }


    private void getclassteacherid() {
        DocumentReference documentReference = fstore.collection("classroom_subject").document(subname);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot= task.getResult();
                    if(documentSnapshot.exists())
                    {
                        classteacherid= documentSnapshot.getString("class teacher");
                        dataintialize();
                    }

                }else {
                    Toast.makeText(Take_Attendance_Activity.this, "no calssroom", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void dataintialize() {
        studentnamelist=new ArrayList<>();
        studentENR=new ArrayList<>();
        prsent=new ArrayList<>();
        absent=new ArrayList<>();
        studentdataArrayList = new ArrayList<>();
        status=new ArrayList<>();



        Toast.makeText(this, "helo"+classteacherid, Toast.LENGTH_SHORT).show();

        CollectionReference classstudents=fstore.collection("classteachers").document(classteacherid).
                collection("class_students");
        classstudents.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Take_Attendance_Activity.this, "bye", Toast.LENGTH_SHORT).show();
                            for (DocumentSnapshot documentSnapshot: task.getResult())
                            {
                                studentnamelist.add(documentSnapshot.getString("Fullname"));
                                studentENR.add(documentSnapshot.getString("Enrollment"));
                                prsent.add("Not Define");
                                absent.add("Not Define");
                                status.add("");
                            }
                            ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, studentnamelist);
                            subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerstudent.setAdapter(subjectAdapter);
                            for (int i = 0; i < studentnamelist.size(); i++) {
                               studentdata = new StudentAttendenceData(studentnamelist.get(i), studentENR.get(i),prsent.get(i),absent.get(i),status.get(i));
                                studentdataArrayList.add(studentdata);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });



    }

    public void attendancebackbtn(View view) {
        finish();
    }
}
