package com.example.torripo;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerViewAdapter_1 extends RecyclerView.Adapter<RecyclerViewAdapter_1.ViewHolder1> {


    Context context;
    ArrayList<Booking_details> booking_details;

    public void setmultiplePackages(ArrayList<Booking_details> booking_details)

    {

        this.booking_details = booking_details;
    }
    public RecyclerViewAdapter_1(Context context,ArrayList<Booking_details> booking_details) {
        this.context = context;
        this.booking_details = booking_details;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_1.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_1,parent,false);
        return new ViewHolder1(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter_1.ViewHolder1 holder , int position) {
        holder.bind(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();

                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,booking_detail_view.newInstance(holder.bid.getText().toString(),holder.p_id.getText().toString(),holder.start_date.getText().toString(),holder.booking_date.getText().toString(),holder.status.getText().toString(),holder.nop.getText().toString()),"booking_detail_view").
                        addToBackStack(null).commit();
                Log.w("item clicked",holder.p_id.getText().toString());

            }
        });

    }




    @Override
    public int getItemCount()
    {
        return booking_details.size();
    }



    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public TextView p_id;
        public TextView bid;
        public TextView start_date;
        public TextView status;
        public TextView nop;
        public TextView booking_date;


        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            p_id = itemView.findViewById(R.id.p_id);
            bid = itemView.findViewById(R.id.b_id);
            start_date = itemView.findViewById(R.id.start_date);
            status = itemView.findViewById(R.id.status);
            nop = itemView.findViewById(R.id.no_of_persons_b);
            booking_date=itemView.findViewById(R.id.booking_date);
        }


        public void bind(int pos)
        {
            Booking_details bk = booking_details.get(pos);
            p_id.setText(bk.p_id);
            bid.setText(String.valueOf(bk.b_id));
            String ct=String.valueOf(bk.no_of_persons_booked);
            nop.setText(ct);
            start_date.setText(bk.start_date);

            status.setText(bk.status);
            booking_date.setText(bk.booking_date);

        }


    }
}
