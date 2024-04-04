package teacherpkg;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.loginform.R;
import com.example.loginform.maximizeimage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import administratorpkg.dategiver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teacher_home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teacher_home_fragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teacher_home_fragment() {
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
    public static teacher_home_fragment newInstance(String param1, String param2) {
        teacher_home_fragment fragment = new teacher_home_fragment();
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

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FloatingActionButton floatmanagestu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_teacher_home_fragment, container, false);
        image_slider = rootView.findViewById(R.id.image_slider);
        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.ronaldo, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.sunilchettri,ScaleTypes.FIT));
        scheduleimg=rootView.findViewById(R.id.scheduleimg);
        profile=inflater.inflate(R.layout.teacher_header, container, false).findViewById(R.id.profileimg);
        floatmanagestu=rootView.findViewById(R.id.floatmanagestu);
        TextView teachername = rootView.findViewById(R.id.teachernamemsg);
        TextView dateref=rootView.findViewById(R.id.datemsg);

        fstore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();


        dateref.setText(dategiver.getdate());

        DocumentReference documentReference=fstore.collection("user").document(fAuth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                teachername.setText(documentSnapshot.getString("fullname"));
            }
        });
        
        checkclassteacher();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hello chuuyog home fragment", Toast.LENGTH_SHORT).show();
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
    private void checkclassteacher() {
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String id = fAuth.getCurrentUser().getUid().toString();
        DocumentReference dr = fstore.collection("classteachers").document(id);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // User is a class teacher
                    floatmanagestu.setVisibility(View.VISIBLE);
                    floatmanagestu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), ManageStudent_classTeacher.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    // User is not a class teacher
                    floatmanagestu.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error occurred, hide the button
                floatmanagestu.setVisibility(View.GONE);
            }
        });
    }

}