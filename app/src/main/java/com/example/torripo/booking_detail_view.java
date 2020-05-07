package com.example.torripo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class booking_detail_view extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ArrayList<Passenger_details> passenger_details = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter_2 recyclerViewAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    // TODO: Rename and change types of parameters

    String p_id,booking_date,start_date,status;
    Integer bid,nop;
    TextView p_id_et,bid_et,start_date_et,status_et,nop_et,booking_date_et,cancel;
    Result_1 result_1;
    TextView tv_view;
    ProgressBar pbar,pbar1;
    ScrollView main_booking_detail;
    public booking_detail_view() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static booking_detail_view newInstance(String bid,String p_id,String start_date,String booking_date,String status,String nop) {
        booking_detail_view fragment = new booking_detail_view();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, bid);

        args.putString(ARG_PARAM2, p_id);

        args.putString(ARG_PARAM3, start_date);
        args.putString(ARG_PARAM4, booking_date);
        args.putString(ARG_PARAM5, status);
        args.putString(ARG_PARAM6, nop);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bid = Integer.parseInt(getArguments().getString(ARG_PARAM1));
            p_id = getArguments().getString(ARG_PARAM2);
            start_date = getArguments().getString(ARG_PARAM3);
            booking_date = getArguments().getString(ARG_PARAM4);
            status = getArguments().getString(ARG_PARAM5);
            nop = Integer.parseInt(getArguments().getString(ARG_PARAM6));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!((MainActivity)getActivity()).isNetworkAvailable(getContext()))
        {
            Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        View view = inflater.inflate(R.layout.fragment_booking_detail_view, container, false);
        pbar = view.findViewById(R.id.progress);
        pbar1 = view.findViewById(R.id.progress_cancel);
        tv_view = view.findViewById(R.id.tv_travellers_detail);
        main_booking_detail = view.findViewById(R.id.booking_detail_main);
        p_id_et = view.findViewById(R.id.p_id);
        bid_et = view.findViewById(R.id.b_id);
        start_date_et = view.findViewById(R.id.start_date);
        status_et = view.findViewById(R.id.status);
        nop_et = view.findViewById(R.id.no_of_persons_b);
        booking_date_et=view.findViewById(R.id.booking_date);
        p_id_et.setText(p_id);
        bid_et.setText(String.valueOf(bid));
        start_date_et.setText(start_date);
        status_et.setText(status);
        nop_et.setText(String.valueOf(nop));
        booking_date_et.setText(booking_date);

        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbar1.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                        addConverterFactory(GsonConverterFactory.create()).build();
                APIService service = retrofit.create(APIService.class);
                Call<Result_1> call = service.cancelbooking("booking_cancel",bid,p_id);
                call.enqueue(new Callback<Result_1>() {
                    @Override
                    public void onResponse(Call<Result_1> call, Response<Result_1> response) {
                        result_1 = response.body();
                        if ("Booking Deleted".equals(result_1.res))
                        {

                            pbar1.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(),"Booking cancelled , refund is initiated \n you will recieve the email shortly ",Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,bookings_view.newInstance(),"bookings_view").addToBackStack(null).commit();
                        }
                        else
                        {
                            pbar1.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(),"Cannot cancel booking , please try later ",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Result_1> call, Throwable t) {
                        pbar1.setVisibility(View.INVISIBLE);

                        Toast.makeText(getContext(),"Error Encountered !!",Toast.LENGTH_LONG).show();
                        Log.e("Error", t.getMessage());
                    }
                });


            }
        });


        recyclerView = view.findViewById(R.id.traveller_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter_2(getActivity(), passenger_details);
        recyclerView.setAdapter(recyclerViewAdapter);



        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        APIService service = retrofit.create(APIService.class);
        Call<List<Passenger_details>> call = service.get_passenger_details("get_passenger_details",bid);
        call.enqueue(new Callback<List<Passenger_details>>() {
            @Override
            public void onResponse(Call<List<Passenger_details>> call, Response<List<Passenger_details>> response) {
                passenger_details = (ArrayList) response.body();
                if (passenger_details.get(0).b_id != 0 && !passenger_details.get(0).p_name.equals("NONE"))
                {
                pbar.setVisibility(View.INVISIBLE);
                main_booking_detail.setVisibility(View.VISIBLE);
                recyclerViewAdapter.setmultiplePackages(passenger_details);
                recyclerViewAdapter.notifyDataSetChanged();
                }
                else
                {
                    pbar.setVisibility(View.INVISIBLE);
                    main_booking_detail.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"No Passenger Booked",Toast.LENGTH_LONG).show();
                    tv_view.setText("No passengers Booked");
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Passenger_details>> call, Throwable t) {
                pbar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(),"Error Encounter !!",Toast.LENGTH_LONG).show();
                getActivity().finish();
                Log.e("Error", t.getMessage());
            }
        });




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
