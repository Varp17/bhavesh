package teacherpkg;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.loginform.R;
import com.example.loginform.maximizeimage;

import java.util.ArrayList;
import java.util.List;

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
    com.google.android.material.floatingactionbutton.FloatingActionButton floabtnmange;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.homefragment, container, false);
        image_slider = rootView.findViewById(R.id.image_slider);
        floabtnmange=rootView.findViewById(R.id.floabtnmange);
        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.ronaldo, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.sunilchettri,ScaleTypes.FIT));
        scheduleimg=rootView.findViewById(R.id.scheduleimg);
        profile=inflater.inflate(R.layout.header, container, false).findViewById(R.id.profileimg);
//        floabtnmange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getContext(), Managestaff_activity.class);
//                startActivity(intent);
//            }
//        });
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
}