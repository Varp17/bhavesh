package administratorpkg;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

public class ClassrromViewAdapter extends RecyclerView.Adapter<ClassrromViewAdapter.ClassroomViewHolder> implements recyclerviewonclick
{
    recyclerviewonclick recyclerviewonclick;
    Context context;
    ArrayList<Subjects> subjectsArrayList;
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
        ImageButton edit_class;
        private void showPopup() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            View dialogView = inflater.inflate(R.layout.popupbutton, null);
            builder.setView(dialogView);

            Button buttonInPopup = dialogView.findViewById(R.id.buttompopupyes);
            buttonInPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click inside the popup
                    // You can dismiss the popup here or perform any other action
                }
            });

            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        public ClassroomViewHolder(@NonNull View itemView,recyclerviewonclick recyclerviewonclick) {
            super(itemView);

            subjectname = itemView.findViewById(R.id.subjectname);
            teachername = itemView.findViewById(R.id.teachername);
            edit_class=itemView.findViewById(R.id.editclass);
            edit_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            showPopup();

                }
            });
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
