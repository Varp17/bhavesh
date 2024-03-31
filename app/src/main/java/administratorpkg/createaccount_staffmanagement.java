package administratorpkg;



import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.loginform.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import administratorpkg.tester;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link createaccount_staffmanagement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createaccount_staffmanagement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ImageButton calander;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText fullname,email,mobileno,address,atuogenrated_password;
    Button createbtn;
    private Button datePickerButton;
    FrameLayout overlay;
    ProgressBar progressBar;

    private DatePickerDialog datePickerDialog;

    public createaccount_staffmanagement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment createaccount_staffmanagement.
     */
    // TODO: Rename and change types and number of parameters
    public static createaccount_staffmanagement newInstance(String param1, String param2) {
        createaccount_staffmanagement fragment = new createaccount_staffmanagement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
    private  createaccount_staffmanagement createaccountStaffmanagement;
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
        View rootView=inflater.inflate(R.layout.fragment_createaccount_staffmanagement, container, false);
        initDatePicker();
        fullname=rootView.findViewById(R.id.fullname);
        email=rootView.findViewById(R.id.email);
        mobileno=rootView.findViewById(R.id.mobileno);
        address=rootView.findViewById(R.id.address);
        atuogenrated_password=rootView.findViewById(R.id.AutoGenerated_Password);
        createbtn=(Button) rootView.findViewById(R.id.Createbtn);

        overlay = rootView.findViewById(R.id.overlay_create_dialog);
        progressBar = rootView.findViewById(R.id.progressBar_create);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        datePickerButton = rootView.findViewById(R.id.datepicker1);
//        datePickerButton.setText(getTodayDate());
//        datePickerButton2.setText(getTodayDate());
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(datePickerButton);
            }
        });

        // Inflate the layout for this fragment



        createbtn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                if (validateFields()) {
                    String message = "Values are: " +
                            fullname.getText().toString() + ", " +
                            email.getText().toString() + ", " +
                            mobileno.getText().toString() + ", " +
                            address.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);
                    createbtn.setVisibility(View.GONE);

                    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
                    FirebaseAuth fAuth=FirebaseAuth.getInstance();
                    String autopassword= tester.generatePassword(5).toString();

                    fAuth.createUserWithEmailAndPassword(email.getText().toString(),autopassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            createbtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            atuogenrated_password.setVisibility(View.VISIBLE);
                            atuogenrated_password.setText(autopassword);
                            FirebaseUser user=fAuth.getCurrentUser();
                            DocumentReference df=fstore.collection("user").document(user.getUid());
                            Map<String,Object> userInfo=new HashMap<>();
                            userInfo.put("fullname",fullname.getText().toString());
                            userInfo.put("email",email.getText().toString());
                            userInfo.put("mobile no",mobileno.getText().toString());
                            userInfo.put("address",address.getText().toString());
                            userInfo.put("DOB", datePickerButton.getText().toString());
                            userInfo.put("password",autopassword);
                            userInfo.put("isTeacher",true);
                            df.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(getContext(), "account  created", Toast.LENGTH_SHORT).show();
                                    fAuth.signInWithEmailAndPassword(getEmailFromSharedPreferences(),getPasswordFromSharedPreferences()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Toast.makeText(getContext(), "delted and relogined", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "varun", Toast.LENGTH_SHORT).show();
                                            Log.d("login", "onFailure: failed ");
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "failed to create account check credential", Toast.LENGTH_SHORT).show();
                        }
                    });
                }




            }

        });

        // Displaying all values in toast



        return rootView;
    }
    private boolean validateFields() {
        EditText[] fields = {fullname, email, mobileno, address};

        for (EditText field : fields) {
            if (TextUtils.isEmpty(field.getText().toString())) {
                field.setError("Fill this field");
                return false;
            }

            if (!isValidEmail(email.getText().toString())) {
                Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return false;
            }
            // Add more specific validation logic if needed (e.g., email validation)
        }

        return true;
    }
    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                datePickerButton.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";
        }
    }
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private void openDatePicker(final Button button) {


        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                button.setText(date);
            }
        });

        datePickerDialog.show();

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















