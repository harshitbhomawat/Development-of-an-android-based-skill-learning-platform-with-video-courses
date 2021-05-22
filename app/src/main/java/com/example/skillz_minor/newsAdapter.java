package com.example.skillz_minor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static androidx.core.content.ContextCompat.startActivity;

public class newsAdapter extends FirebaseRecyclerAdapter<newsmodelclass,newsAdapter.newsviewholder> {

    public newsAdapter(@NonNull FirebaseRecyclerOptions<newsmodelclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull newsviewholder holder, int position, @NonNull newsmodelclass model) {
        holder.text.setText(model.getText());
        holder.link.setText(model.getLink());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
              //  Intent intent = new Intent(Intent.ACTION_VIEW);
              //  intent.setData(Uri.parse(holder.link.getText().toString()));
               // Bundle bundle = new Bundle(intent.getExtras());
                //startActivity(MainActivity.getContext,intent,bundle);
                Uri uri=Uri.parse(holder.link.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                try {
                    MainActivity.getContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.getContext,"unable to open",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    @Override
    public newsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newscard,parent,false);
        return new newsAdapter.newsviewholder(view);
    }

    class newsviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text,link;
        ItemClickListener itemClickListener;
        public newsviewholder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.newstext);
            link = itemView.findViewById(R.id.newslink);
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
