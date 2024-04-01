package administratorpkg;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.List;

public class ClassrromViewAdapter extends RecyclerView.Adapter<ClassrromViewAdapter.ClassroomViewHolder> implements recyclerviewonclick
{
    recyclerviewonclick recyclerviewonclick;
    Context context;
    ArrayList<Subjects> subjectsArrayList;
    Dialog myDialog ;
    Button closebtn,submitbtn;
    TextView info;
    public ClassrromViewAdapter(Context context,ArrayList<Subjects> subjects , recyclerviewonclick recyclerviewonclick) {
        this.context = context;
        this.subjectsArrayList = subjects;
        this.recyclerviewonclick=recyclerviewonclick;

    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.classroomview_cards,parent,false);
        return new ClassroomViewHolder(v,recyclerviewonclick);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Subjects sub = subjectsArrayList.get(position);
        holder.subjectname.setText(sub.subjectname);
        holder.teachername.setText(sub.teachername);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialog = new Dialog(v.getContext());
                myDialog.setContentView(R.layout.dialog_delete_staff);
                closebtn = myDialog.findViewById(R.id.buttonClose);
                submitbtn=myDialog.findViewById(R.id.buttonDelete);
                info=myDialog.findViewById(R.id.textViewStaffInfo);
                myDialog.setCanceledOnTouchOutside(false);
                info.setText("Do you want to delete "+sub.subjectname+" assigned to "+sub.teachername);

                myDialog.show();
                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                submitbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("classteachers")
                                .whereEqualTo("fullname", sub.teachername)
                                // Limiting the query to fetch only one document
                                .limit(1)
                                // Execute the query
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            // Check if any document is found
                                            if (!task.getResult().isEmpty()) {
                                                // Get the document snapshot
                                                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                                // Handle the document
                                                String documentId = document.getId();
                                                // You can access other fields of the document here

                                                DocumentReference docRef = db.collection("classteachers").document(documentId);

                                                // Delete the document
                                                docRef.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    // Document successfully deleted
                                                                    Toast.makeText(v.getContext(), "classroom deleted", Toast.LENGTH_SHORT).show();
                                                                    Log.d("Firestore", "DocumentSnapshot successfully deleted!");

                                                                } else {
                                                                    // Error handling
                                                                    Toast.makeText(v.getContext(), "failed to delete", Toast.LENGTH_SHORT).show();
                                                                    Log.e("Firestore", "Error deleting document", task.getException());
                                                                }
                                                            }
                                                        });
                                                // Do something with the document
                                                // For example, log the details
                                                Log.d("Firestore", "Document found  ");
                                            } else {
                                                // No document found
                                                Log.d("Firestore", "No document found '");
                                                Toast.makeText(v.getContext(), "Please refresh! already deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Error handling
                                            Log.e("Firestore", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                });





            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectsArrayList.size();
    }


    @Override
    public void onItemClick(int position) {

    }


    public static class ClassroomViewHolder extends RecyclerView.ViewHolder {

        TextView subjectname,teachername;
        ImageButton edit;

        public ClassroomViewHolder(@NonNull View itemView,recyclerviewonclick recyclerviewonclick) {
            super(itemView);

            subjectname = itemView.findViewById(R.id.subjectname);
            teachername = itemView.findViewById(R.id.teachername);
            edit=itemView.findViewById(R.id.editclass);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerviewonclick!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){


                            recyclerviewonclick.onItemClick(pos);
                        }
                    }

                }
            });
        }



    }

}
