package teacherpkg;

import android.app.Dialog;
import android.content.Context;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class managesubjectrecycler extends RecyclerView.Adapter<managesubjectrecycler.SubjectViewHolder> {
    private Context context;
    private ArrayList<Subjects> subjectsArrayList;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth fAuth =FirebaseAuth.getInstance();

    public managesubjectrecycler(Context context, ArrayList<Subjects> subjectsArrayList) {
        this.context = context;
        this.subjectsArrayList = subjectsArrayList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.classroomview_cards, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subjects sub = subjectsArrayList.get(position);
        holder.subjectname.setText(sub.subjectname);
        holder.teachername.setText(sub.teachername);

        holder.pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog myDialog = new Dialog(v.getContext());
                myDialog.setContentView(R.layout.dialog_delete_staff);
                Button closebtn = myDialog.findViewById(R.id.buttonClose);
                Button submitbtn = myDialog.findViewById(R.id.buttonDelete);
                TextView info = myDialog.findViewById(R.id.textViewStaffInfo);
                myDialog.setCanceledOnTouchOutside(false);
                info.setText("Do you want to delete " + sub.subjectname + " assigned to " + sub.teachername);

                myDialog.show();

                submitbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DocumentReference documentReference=firestore.collection("classroom_subject").document(sub.subjectname);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(submitbtn.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(submitbtn.getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });


                    }
                });
                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectsArrayList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView subjectname, teachername;
        ImageButton pencil;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectname = itemView.findViewById(R.id.subjectname);
            teachername = itemView.findViewById(R.id.teachername);
            pencil = itemView.findViewById(R.id.editclass);
        }
    }
}
