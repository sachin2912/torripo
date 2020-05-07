package com.example.torripo;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    Context context;
    ArrayList<Holiday_Package> packages;

    public void setmultiplePackages(ArrayList<Holiday_Package> packages)

    {

        this.packages = packages;
    }
    public RecyclerViewAdapter(Context context,ArrayList<Holiday_Package> packages) {
        this.context = context;
        this.packages = packages;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder , int position) {
        holder.bind(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();

                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,package_detail_view.newInstance(holder.p_id.getText().toString()),"package_detail_view").
                        addToBackStack(null).commit();
                Log.w("item clicked",holder.p_id.getText().toString());

            }
        });

    }




    @Override
    public int getItemCount()
    {
        return packages.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView p_id;
        public TextView location_name;
        public TextView cost;
        public TextView start_place;
        public TextView available;
        public ImageView img_url;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            p_id = itemView.findViewById(R.id.p_id);
            location_name = itemView.findViewById(R.id.location_name);
            cost = itemView.findViewById(R.id.cost);
            start_place = itemView.findViewById(R.id.starting_place);
            available = itemView.findViewById(R.id.available);
            img_url=itemView.findViewById(R.id.img_url);
        }


        public void bind(int pos)
        {
            Holiday_Package pk = packages.get(pos);
            p_id.setText(pk.p_id);
            location_name.setText(pk.location);
            String ct=String.valueOf(pk.cost);
            cost.setText("Rs. "+ct);
            start_place.setText(pk.starting_place);
            int temp=pk.total_count-pk.count;
            String t = String.valueOf(temp);
            String l = "Available : "+t;
            available.setText(l);
            Picasso.with(context).load("http://"+pk.img_url).into(img_url);
        }


    }
}
