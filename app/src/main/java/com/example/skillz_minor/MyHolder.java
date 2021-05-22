package com.example.skillz_minor;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillz_minor.ItemClickListener;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView title;
    ImageView iconbtn;
    ItemClickListener itemClickListener;
    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.title=itemView.findViewById(R.id.ctitle);
        this.iconbtn=itemView.findViewById(R.id.iconbtn);
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
