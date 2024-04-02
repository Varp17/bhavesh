package teacherpkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> implements teacherpkg.recyclerviewonclick
{
    teacherpkg.recyclerviewonclick recyclerviewonclick;
    Context context;
    ArrayList<Staff> staffArrayList;

    public StaffAdapter(Context context, ArrayList<Staff> staff , teacherpkg.recyclerviewonclick recyclerviewonclick) {
        this.context = context;
        this.staffArrayList = staff;
        this.recyclerviewonclick=recyclerviewonclick;

    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listview_item_student,parent,false);
        return new StaffViewHolder(v, (recyclerviewonclick));
    }



    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        Staff staff = staffArrayList.get(position);
        holder.teachername.setText(staff.teachername);
        // Store user ID in ViewHolder
        holder.userId = staff.getTeacherID();
    }

    @Override
    public int getItemCount() {
        return staffArrayList.size();
    }


    @Override
    public void onItemClick(int position) {
    }


    public class StaffViewHolder extends RecyclerView.ViewHolder {

        public String userId;
        TextView teachername;

        public StaffViewHolder(@NonNull View itemView, teacherpkg.recyclerviewonclick recyclerviewonclick) {
            super(itemView);
            teachername = itemView.findViewById(R.id.studentname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && StaffAdapter.this.recyclerviewonclick != null) {
                        StaffAdapter.this.recyclerviewonclick.onItemClick(position);
                    }
                }
            });
        }

    }

}
