package com.example.skillz_minor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<MyHolder> implements Filterable {
    ArrayList<Courses> itemList1;
    ArrayList<Courses> extralist;
    Context context;
    public Adapter(ArrayList<Courses> itemList1, Context context) {
        this.itemList1 = itemList1;
        extralist = new ArrayList<>(itemList1);
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // convert xml to object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coursecard,null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.title.setText(itemList1.get(position).getTitle());
        holder.iconbtn.setImageResource(itemList1.get(position).getImage());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                //CourseFragment courseFragment = new CourseFragment(pos+1);
               // appCompatActivity.getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,courseFragment).commit();
            }
        });
    }
    
    @Override
    public int getItemCount()
    {
        return itemList1.size();
    }

    @Override
    public Filter getFilter() {
        return examplefilter;
    }
    private Filter examplefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Courses> filteredList = new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filteredList.addAll(extralist);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Courses item:extralist){
                    if(item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList1.clear();
            itemList1.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}
