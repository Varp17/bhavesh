package teacherpkg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import administratorpkg.dategiver;

public class Take_Attendance_Activity extends varchi_line {

    TextView classname;
    TextView datetextview,textViewEnrollment,student_name;
    Button next,previous,presentbtn,absentbtn,upload,clear;
    
    String subname;
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    String classteacherid;
    ArrayList<String> studentnamelist,studentENR;

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
        upload=findViewById(R.id.buttonUpload);
        clear=findViewById(R.id.buttonClearAll);






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
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Take_Attendance_Activity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to clear All attendance?" +
                        "\nnote - once deleted can't be changed ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action to perform when "Yes" is clicked
                        // For example, you can proceed with the operation

                        for(int i=0;i< studentdataArrayList.size();i++)
                        {
                           studentdataArrayList.get(i).status="";
                        }
                        statustextview.setText("");

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action to perform when "No" is clicked
                        // For example, you can cancel the operation
                        dialog.dismiss();
                    }
                });
                builder.show();

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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statuscheckall())
                {
                    showConfirmationDialog();
                    writeToExcelSheet();
                }
            }
        });


    }

    private boolean statuscheckall() {
        int c=0;
        for(int i=0;i< studentdataArrayList.size();i++)
        {
            if(studentdataArrayList.get(i).status.equals(""))
            {

                selecteditempos=i;
                student_name.setText(studentdataArrayList.get(i).name);
                textViewEnrollment.setText(studentdataArrayList.get(i).enr);
                statustextview.setText(studentdataArrayList.get(i).status);
                Toast.makeText(this, "attendance pending", Toast.LENGTH_SHORT).show();
                break;

            }else if(studentdataArrayList.get(i).status.equals("P") || studentdataArrayList.get(i).status.equals("Ab"))
            {
                c++;
            }
        }
        if(c==studentdataArrayList.size())
        {

            return true;
        }
        else {
            return false;
        }
    }

    private void setPresent(int position)
    {
        studentdataArrayList.get(position).status="P";

    }
    private void setAbsent(int position)
    {
            studentdataArrayList.get(position).status="Ab";

    }

    private void setinfo(int position) {
        if (studentdataArrayList != null && !studentdataArrayList.isEmpty()) {
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
        } else {
            // Handle the case where studentdataArrayList is null or empty
            // For example, you can display an error message or take appropriate action
        }
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

                                status.add("");
                            }
                            ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, studentnamelist);
                            subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerstudent.setAdapter(subjectAdapter);
                            for (int i = 0; i < studentnamelist.size(); i++) {
                               studentdata = new StudentAttendenceData(studentnamelist.get(i), studentENR.get(i),status.get(i));
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


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to upload attendance?" +
                "\nnote - once uploaded can't be changed ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action to perform when "Yes" is clicked
                // For example, you can proceed with the operation
                Toast.makeText(getApplicationContext(), "uploading to database", Toast.LENGTH_SHORT).show();
                CollectionReference attendsub = fstore.collection("classteachers").document(classteacherid).collection("class_students");
                attendsub.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int i=0;
                        if(task.getResult()!=null)
                        {

                            for(DocumentSnapshot documentSnapshot:task.getResult())
                            {

                                if(documentSnapshot.getString("Fullname").equals(studentdataArrayList.get(i).name))
                                {
                                    DocumentReference documentReference= fstore.collection("classteachers").document(classteacherid)
                                            .collection("class_students").document(documentSnapshot.getId()).collection("attendance")
                                            .document(subname);

                                    Map<String, Object> statusinfo = new HashMap<>();
                                    statusinfo.put(dategiver.getdate(),studentdataArrayList.get(i).status);

                                    documentReference.update(statusinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Take_Attendance_Activity.this, "pushed", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Take_Attendance_Activity.this, "game over", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                i++;
                            }
                            if(studentdataArrayList.size()==i)
                            {
                                DocumentReference classteacher;

                                classteacher= fstore.collection("classroom_subject").document(subname);
                                classteacher.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.getLong("attendance taken counter")!=null && !documentSnapshot.getString("latest attendance taken").equals(dategiver.getdate()))
                                        {
                                            long c=documentSnapshot.getLong("attendance taken counter");
                                            DocumentReference counter= fstore.collection("classroom_subject").document(subname);
                                            Map<String, Object> cun = new HashMap<>();

                                            c++;
                                            cun.put("latest attendance taken",dategiver.getdate());
                                            cun.put("attendance taken counter",c);
                                            counter.update(cun);
                                        }else if(documentSnapshot.getLong("attendance taken counter")==null){
                                            DocumentReference classteacher1= fstore.collection("classroom_subject").document(subname);
                                            Map<String, Object> attendtaken1 = new HashMap<>();
                                            attendtaken1.put("latest attendance taken",dategiver.getdate());
                                            attendtaken1.put("attendance taken counter",1);
                                            classteacher1.update(attendtaken1);
                                        }
                                    }
                                });
                            }
                        }

                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action to perform when "No" is clicked
                // For example, you can cancel the operation
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void writeToExcelSheet() {
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance1");

        String Date1 = dategiver.getdate();
        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Name", "Enrollment", "Date1"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            if (i == 2) {
                cell.setCellValue("(" + Date1 + ")");
            } else {
                cell.setCellValue(headers[i]);
            }
        }

        // Write attendance data
        for (int i = 0; i < studentdataArrayList.size(); i++) {
            StudentAttendenceData studentData = studentdataArrayList.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(studentData.getName());
            row.createCell(1).setCellValue(studentData.getEnrollment());
            row.createCell(2).setCellValue(studentData.getStatus());
            row.createCell(3).setCellValue(dategiver.getdate());
        }

        // Set the path for saving the Excel file
        String fileName = "Attendance1.xlsx";
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "downloads");
        if (!dir.exists()) {
            dir.mkdirs(); // Create directories if they don't exist
        }
        File file = new File(dir, fileName);

        try (OutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
            Toast.makeText(this, "Attendance data saved to Excel sheet", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save attendance data", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
