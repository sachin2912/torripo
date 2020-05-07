package com.example.torripo;

import android.content.Context;
import android.content.SharedPreferences;
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


public class bookings_view extends Fragment {
    ArrayList<Booking_details> booking_details = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter_1 recyclerViewAdapter;
    SharedPreferences sharedPreferences;
    String uname;
    ProgressBar pbar;
    ScrollView main_booking;
    TextView tv;

    public bookings_view() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static bookings_view newInstance() {
        bookings_view fragment = new bookings_view();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("torripo_login",Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("uname","none");

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
        View view =  inflater.inflate(R.layout.fragment_bookings_view, container, false);
        tv = view.findViewById(R.id.no_booking);
        pbar = view.findViewById(R.id.progress);
        main_booking = view.findViewById(R.id.main_booking_view);
        recyclerView = view.findViewById(R.id.booking_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter_1(getActivity(), booking_details);
        recyclerView.setAdapter(recyclerViewAdapter);



        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        APIService service = retrofit.create(APIService.class);
        Call<List<Booking_details>> call = service.get_booking_details("view_user_booking",uname);
        call.enqueue(new Callback<List<Booking_details>>() {
            @Override
            public void onResponse(Call<List<Booking_details>> call, Response<List<Booking_details>> response) {

                booking_details = (ArrayList) response.body();

                if (booking_details.get(0).b_id == 0 && booking_details.get(0).p_id.equalsIgnoreCase("error"))
                {
                    pbar.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.VISIBLE);
                }
                else {
                    main_booking.setVisibility(View.VISIBLE);

                    recyclerViewAdapter.setmultiplePackages(booking_details);
                    recyclerViewAdapter.notifyDataSetChanged();
                    pbar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Booking_details>> call, Throwable t) {

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
