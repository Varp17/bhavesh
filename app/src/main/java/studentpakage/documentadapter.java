package studentpakage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginform.R;

import java.util.ArrayList;

public class documentadapter extends RecyclerView.Adapter<documentadapter.MyViewHolder> {

    private ArrayList<DataClass> dataList;
    private Context context;

    public documentadapter(ArrayList<DataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_recycler_itemdocument, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass currentItem = dataList.get(position);
        Glide.with(context).load(currentItem.getImageURL()).into(holder.recyclerImage);
        holder.recyclerCaption.setText(currentItem.getCaption());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImage;
        TextView recyclerCaption;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyclerImage.onVisibilityAggregated(true);
            recyclerCaption = itemView.findViewById(R.id.recyclerCaption);
        }
    }
}
