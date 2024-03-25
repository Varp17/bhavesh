package administratorpkg;

import static com.example.loginform.R.drawable.buttoncorners;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import kotlinx.coroutines.channels.ChannelResult;

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
EditText fullname,email,mobileno,address,atuogenrated_password;
    Button updatebtn,createbtn;
    String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView=inflater.inflate(R.layout.fragment_createaccount_staffmanagement, container, false);
        calander=rootView.findViewById(R.id.calander);
        fullname=rootView.findViewById(R.id.fullname);
        email=rootView.findViewById(R.id.email);
        mobileno=rootView.findViewById(R.id.mobileno);
        address=rootView.findViewById(R.id.address);
        atuogenrated_password=rootView.findViewById(R.id.AutoGenerated_Password);
        updatebtn=rootView.findViewById(R.id.Updatebtn);
        createbtn=(Button) rootView.findViewById(R.id.Createbtn);





        calander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dt = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Create a Calendar instance and set it to the selected date
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);

                        // Format the selected date into a readable format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        selectedDate = dateFormat.format(calendar.getTime());

                        // Show the selected date in a toast

                    }
                }, 2022, 2, 1);

                dt.show();
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

                    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
                    FirebaseAuth fAuth=FirebaseAuth.getInstance();
                    String autopassword=tester.generatePassword(5).toString();

                    fAuth.createUserWithEmailAndPassword(email.getText().toString(),autopassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            atuogenrated_password.setVisibility(View.VISIBLE);
                            atuogenrated_password.setText(autopassword);
                            FirebaseUser user=fAuth.getCurrentUser();
                            DocumentReference df=fstore.collection("user").document(user.getUid());
                            Map<String,Object> userInfo=new HashMap<>();
                            userInfo.put("fullname",fullname.getText().toString());
                            userInfo.put("email",email.getText().toString());
                            userInfo.put("mobile no",mobileno.getText().toString());
                            userInfo.put("address",address.getText().toString());
                            userInfo.put("DOB",selectedDate);
                            userInfo.put("isTeacher",true);
                            df.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "classroom created", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "failed to create account check credential", Toast.LENGTH_SHORT).show();
                        }
                    });


                    updatebtn.setVisibility(View.VISIBLE);
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
            // Add more specific validation logic if needed (e.g., email validation)
        }

        return true;
    }

}

