package com.example.skillz_minor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillz_minor.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class contentAdapter extends RecyclerView.Adapter<contentAdapter.ViewHolder> {
    ArrayList<videos> itemList1;
    LayoutInflater layoutInflater;
    Boolean enabled = CourseFragment.enablechecker();
    public contentAdapter(ArrayList<videos> itemList){
        this.itemList1=itemList;
    }

    @NonNull
    @Override
    public contentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.coursecontent,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.videonumber.setText(itemList1.get(position).getvideonumber());
        holder.videotitle.setText(itemList1.get(position).getvideotitle());
        holder.link.setText(itemList1.get(position).getLink());
        holder.itemView.setEnabled(enabled);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.getContext,VideoActivity.class);
                intent.putExtra("link", itemList1.get(position).getLink());
                MainActivity.getContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return itemList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView videonumber;
        TextView videotitle;
        TextView link;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videonumber = itemView.findViewById(R.id.videonumber);
            videotitle = itemView.findViewById(R.id.videotitle);
            link = itemView.findViewById(R.id.link);
        }

    }
}
