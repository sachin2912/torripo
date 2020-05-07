package com.example.torripo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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


public class user_account_view extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    TextView uname,user_name,user_email;
    user_detail user_1;
    SharedPreferences sharedPreferences;
    String uname_1;
    ProgressBar pbar;
    CardView main_user;
    public user_account_view() {
        // Required empty public constructor
    }

    public static user_account_view newInstance() {
        user_account_view fragment = new user_account_view();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("torripo_login",Context.MODE_PRIVATE);
        uname_1 = sharedPreferences.getString("uname","none");

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
        View view=inflater.inflate(R.layout.fragment_user_account_view, container, false);
        uname=view.findViewById(R.id.uname);
        user_name=view.findViewById(R.id.name);
        user_email=view.findViewById(R.id.email);
        pbar=view.findViewById(R.id.progress);
        main_user = view.findViewById(R.id.user_main);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        APIService service = retrofit.create(APIService.class);
        Call<user_detail> call = service.getuserdetail("user_details",uname_1);
        call.enqueue(new Callback<user_detail>() {
            @Override
            public void onResponse(Call<user_detail> call, Response<user_detail> response) {
                user_1 = response.body();
                pbar.setVisibility(View.GONE);
                main_user.setVisibility(View.VISIBLE);
                uname.setText("Username :  "+user_1.uname);
                user_name.setText("Name :  "+user_1.name);
                user_email.setText("Email :  "+user_1.email);
            }

            @Override
            public void onFailure(Call<user_detail> call, Throwable t) {
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
