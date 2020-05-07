package com.example.torripo;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class multiple_package extends Fragment {
    ArrayList<Holiday_Package> packages = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar pbar;
    ScrollView mainview;
    public multiple_package() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static multiple_package newInstance() {
        multiple_package fragment = new multiple_package();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view= inflater.inflate(R.layout.fragment_multiple_package, container, false);
        ((MainActivity)getActivity()).show_menu();
        pbar=view.findViewById(R.id.progress);
        mainview=view.findViewById(R.id.main_view);
        recyclerView = view.findViewById(R.id.package_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), packages);
        recyclerView.setAdapter(recyclerViewAdapter);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        APIService service = retrofit.create(APIService.class);
        Call<List<Holiday_Package>> call = service.getholiday_packages("get_packages");
        call.enqueue(new Callback<List<Holiday_Package>>() {
            @Override
            public void onResponse(Call<List<Holiday_Package>> call, Response<List<Holiday_Package>> response) {
                packages = (ArrayList) response.body();
                recyclerViewAdapter.setmultiplePackages(packages);
                recyclerViewAdapter.notifyDataSetChanged();
                mainview.setVisibility(View.VISIBLE);
                pbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Holiday_Package>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



}
