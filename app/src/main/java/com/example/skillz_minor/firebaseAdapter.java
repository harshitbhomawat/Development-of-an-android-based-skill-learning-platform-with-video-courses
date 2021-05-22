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

public class firebaseAdapter extends FirebaseRecyclerAdapter<courseModelClass,firebaseAdapter.myviewholder> {
    Boolean aligner = true;

    public firebaseAdapter(@NonNull FirebaseRecyclerOptions<courseModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull courseModelClass model) {
        holder.title.setText(model.getTitle());
//        holder.desc.setText(model.getDesc());
        if(aligner){
        Glide.with(holder.profileIcon).load(model.getLink()).into(holder.profileIcon);
        aligner = !aligner;
        }
        else {
            Glide.with(holder.righticon).load(model.getLink()).into(holder.righticon);
            aligner = !aligner;
        }

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
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coursecard,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView profileIcon,righticon;
        TextView title,desc;
        ItemClickListener itemClickListener;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            profileIcon = (ImageView)itemView.findViewById(R.id.iconbtn);
            righticon = (ImageView)itemView.findViewById(R.id.righticonbtn);
            title = (TextView)itemView.findViewById(R.id.ctitle);
//            desc = (TextView)itemView.findViewById(R.id.cdesc);
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
