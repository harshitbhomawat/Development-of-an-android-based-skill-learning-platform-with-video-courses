package com.example.skillz_minor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class topcourseAdapter extends FirebaseRecyclerAdapter<courseModelClass,topcourseAdapter.myviewholder> {

    public topcourseAdapter(@NonNull FirebaseRecyclerOptions<courseModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull topcourseAdapter.myviewholder holder, int position, @NonNull courseModelClass model) {
        holder.title.setText(model.getTitle());
        Glide.with(holder.topicon).load(model.getLink()).into(holder.topicon);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                String passingtitle = holder.title.getText().toString();
                String link = model.getLink();
                String desc = model.getDesc();
                CourseFragment courseFragment = new CourseFragment(passingtitle,link,desc);
                appCompatActivity.getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,courseFragment).commit();
            }
        });
    }

    @NonNull
    @Override
    public topcourseAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topcoursecard,parent,false);
        return new topcourseAdapter.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView topicon;
        TextView title;
        ItemClickListener itemClickListener;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            topicon = (ImageView)itemView.findViewById(R.id.topicon);
            title = (TextView)itemView.findViewById(R.id.toptitle);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
