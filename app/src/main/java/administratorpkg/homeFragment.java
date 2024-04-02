package administratorpkg;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.loginform.R;
import com.example.loginform.maximizeimage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

}

    ImageSlider image_slider;
    ImageView scheduleimg;
    ImageView profile;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String date;
    com.google.android.material.floatingactionbutton.FloatingActionButton floabtnmange,feedbackbtn;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.homefragment, container, false);
        image_slider = rootView.findViewById(R.id.image_slider);
        floabtnmange=rootView.findViewById(R.id.floabtnmange);
        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.cristiano, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.mandeep,ScaleTypes.FIT));
        scheduleimg=rootView.findViewById(R.id.scheduleimg);
        profile=inflater.inflate(R.layout.header, container, false).findViewById(R.id.profileimg);
        TextView teachername = rootView.findViewById(R.id.teachernamemsg);
        TextView dateref=rootView.findViewById(R.id.datemsg);

         fstore=FirebaseFirestore.getInstance();
         fAuth=FirebaseAuth.getInstance();

        DocumentReference documentReference=fstore.collection("user").document(fAuth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                teachername.setText(documentSnapshot.getString("fullname"));
            }
        });
        getdate();
        dateref.setText(date);

        floabtnmange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), Managestaff_activity.class);
                startActivity(intent);

            }
        });

        feedbackbtn=rootView.findViewById(R.id.floatbtnfeedback);

        feedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),feedback_activity_administrator.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hello suyog home fragment", Toast.LENGTH_SHORT).show();
            }
        });
        scheduleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), maximizeimage.class);

                intent.putExtra("img",R.drawable.aaaaa);
                startActivity(intent);
            }
        });

        image_slider.setImageList(imageList); // true for auto sliding
        return rootView;

    }

    private void getdate() {
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        int monthInt = Integer.parseInt(month);

        String monthName; // Variable to hold the month name abbreviation

        switch (monthInt) {
            case 1:
                // January
                monthName = "Jan";
                break;
            case 2:
                // February
                monthName = "Feb";
                break;
            case 3:
                // March
                monthName = "Mar";
                break;
            case 4:
                // April
                monthName = "Apr";
                break;
            case 5:
                // May
                monthName = "May";
                break;
            case 6:
                // June
                monthName = "Jun";
                break;
            case 7:
                // July
                monthName = "Jul";
                break;
            case 8:
                // August
                monthName = "Aug";
                break;
            case 9:
                // September
                monthName = "Sep";
                break;
            case 10:
                // October
                monthName = "Oct";
                break;
            case 11:
                // November
                monthName = "Nov";
                break;
            case 12:
                // December
                monthName = "Dec";
                break;
            default:
                // Default case, if the month is not in the range 1-12
                // You can add code here for handling unexpected cases
                monthName = ""; // or any default value indicating an error
                break;
        }
        date=day+" "+monthName;


    }
}