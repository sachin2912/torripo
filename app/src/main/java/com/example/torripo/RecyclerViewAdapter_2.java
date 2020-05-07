package com.example.torripo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter_2 extends RecyclerView.Adapter<RecyclerViewAdapter_2.ViewHolder2> {


    Context context;
    ArrayList<Passenger_details> passenger_details;


    public void setmultiplePackages(ArrayList<Passenger_details> passenger_details)

    {

        this.passenger_details = passenger_details;
    }
    public RecyclerViewAdapter_2(Context context,ArrayList<Passenger_details> passenger_details) {
        this.context = context;
        this.passenger_details = passenger_details;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_2.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_2,parent,false);
        return new ViewHolder2(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter_2.ViewHolder2 holder , int position) {
        holder.bind(position);




    }




    @Override
    public int getItemCount()
    {
        return passenger_details.size();
    }



    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView p_name;

        public TextView p_age;
        public TextView p_gender;



        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            p_name = itemView.findViewById(R.id.p_name);
            p_age = itemView.findViewById(R.id.p_age);
            p_gender = itemView.findViewById(R.id.p_gender);
        }


        public void bind(int pos)
        {
            Passenger_details pd = passenger_details.get(pos);
            if (pd.b_id != 0 ) {
                p_name.setText(pd.p_name);
                String ct = String.valueOf(pd.p_age);
                p_age.setText(ct);
                p_gender.setText(pd.p_gender);
            }

        }


    }
}
