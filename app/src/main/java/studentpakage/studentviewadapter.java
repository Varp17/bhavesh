package studentpakage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import administratorpkg.ClassrromViewAdapter;

public class studentviewadapter extends RecyclerView.Adapter<studentviewadapter.studentViewHolder> implements recyclerviewonclick
{
    recyclerviewonclick recyclerviewonclick;
    Context context;
    ArrayList<Subjects> subjectsArrayList;

    Dialog myDialog ;
    Button closebtn,submitbtn;
    TextView info;
    public studentviewadapter(Context context,ArrayList<Subjects> subjects , recyclerviewonclick recyclerviewonclick) {
        this.context = context;
        this.subjectsArrayList = subjects;
        this.recyclerviewonclick=recyclerviewonclick;

    }

    @NonNull
    @Override
    public studentviewadapter.studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.classroomview_cards,parent,false);
        return new studentViewHolder(view,recyclerviewonclick);
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewHolder holder, int position) {
        Subjects sub = subjectsArrayList.get(position);
        holder.subjectname.setText(sub.subjectname);
        holder.teachername.setText(sub.teachername);

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                myDialog = new Dialog(v.getContext());
//                myDialog.setContentView(R.layout.dialog_delete_staff);
//                closebtn = myDialog.findViewById(R.id.buttonClose);
//                submitbtn=myDialog.findViewById(R.id.buttonDelete);
//                info=myDialog.findViewById(R.id.textViewStaffInfo);
//                myDialog.setCanceledOnTouchOutside(false);
//                info.setText("Do you want to delete "+sub.subjectname+" assigned to "+sub.teachername);
//
//                myDialog.show();
//                closebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        myDialog.dismiss();
//                    }
//                });
//                submitbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                        db.collection("classteachers")
//                                .whereEqualTo("fullname", sub.teachername)
//                                // Limiting the query to fetch only one document
//                                .limit(1)
//                                // Execute the query
//                                .get()
//                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            // Check if any document is found
//                                            if (!task.getResult().isEmpty()) {
//                                                // Get the document snapshot
//                                                DocumentSnapshot document = task.getResult().getDocuments().get(0);
//                                                // Handle the document
//                                                String documentId = document.getId();
//                                                // You can access other fields of the document here
//
//                                                DocumentReference docRef = db.collection("classteachers").document(documentId);
//
//                                                // Delete the document
//                                                docRef.delete()
//                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                if (task.isSuccessful()) {
//                                                                    // Document successfully deleted
//                                                                    Toast.makeText(v.getContext(), "classroom deleted", Toast.LENGTH_SHORT).show();
//                                                                    Log.d("Firestore", "DocumentSnapshot successfully deleted!");
//
//                                                                } else {
//                                                                    // Error handling
//                                                                    Toast.makeText(v.getContext(), "failed to delete", Toast.LENGTH_SHORT).show();
//                                                                    Log.e("Firestore", "Error deleting document", task.getException());
//                                                                }
//                                                            }
//                                                        });
//                                                // Do something with the document
//                                                // For example, log the details
//                                                Log.d("Firestore", "Document found  ");
//                                            } else {
//                                                // No document found
//                                                Log.d("Firestore", "No document found '");
//                                                Toast.makeText(v.getContext(), "Please refresh! already deleted", Toast.LENGTH_SHORT).show();
//                                            }
//                                        } else {
//                                            // Error handling
//                                            Log.e("Firestore", "Error getting documents: ", task.getException());
//                                        }
//                                    }
//                                });
//                    }
//                });
//
//
//
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (subjectsArrayList != null) {
            return subjectsArrayList.size();
        } else {
            return 0; // or return a default size depending on your logic
        }
    }


    @Override
    public void onItemClick(int position) {

    }


    public static class studentViewHolder extends RecyclerView.ViewHolder {

        public View edit;
        TextView subjectname,teachername;

        public studentViewHolder(@NonNull View itemView, recyclerviewonclick recyclerviewonclick) {
            super(itemView);

            subjectname = itemView.findViewById(R.id.subjectname);
            teachername = itemView.findViewById(R.id.teachername);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerviewonclick!=null){
                        int pos=getAdapterPosition();


                        if(pos!=RecyclerView.NO_POSITION){

//                            Context context = itemView.getContext();
//                            Intent intent = new Intent(context, student_classroom_fragment.class);
//                            // You may need to pass some data to the fragment here
//                            // For example, if you need to pass subject details, you can do:
//                            // intent.putExtra("subject_id", subjectsArrayList.get(pos).getId());
//                            context.startActivity(intent);


                            recyclerviewonclick.onItemClick(pos);
                        }
                    }

                }
            });
        }


        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}