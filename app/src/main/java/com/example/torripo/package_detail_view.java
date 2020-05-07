package com.example.torripo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class package_detail_view extends Fragment {
    private static final String ARG_PARAM1 = "parm1";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    TextView pid,location_name,starting_place,cost,des,hotel_name,no_of_days,no_of_person;
    Holiday_Package holiday_package;
    package_detail pk_detail;
    Button book;
    ProgressBar pbar;
    ScrollView package_main;

    private String rec_p_id;
    ImageView img_url;

    public package_detail_view() {
        // Required empty public constructor
    }

    public static package_detail_view newInstance(String rec_p_id) {
        package_detail_view fragment = new package_detail_view();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, rec_p_id);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rec_p_id = getArguments().getString(ARG_PARAM1);

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
        View view =  inflater.inflate(R.layout.fragment_package_detail_view, container, false);
        pid=view.findViewById(R.id.p_id);
        pbar=view.findViewById(R.id.progress);
        package_main = view.findViewById(R.id.package_main);
        location_name=view.findViewById(R.id.location_name);
        starting_place=view.findViewById(R.id.starting_place);
        cost=view.findViewById(R.id.cost);
        img_url = view.findViewById(R.id.img_url_d);
        des=view.findViewById(R.id.description);
        hotel_name=view.findViewById(R.id.hotel_name);
        no_of_days=view.findViewById(R.id.no_of_days);
        no_of_person=view.findViewById(R.id.no_of_person);
        book=view.findViewById(R.id.book_btn);
        book.setEnabled(false);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        APIService service = retrofit.create(APIService.class);
        Call<Holiday_Package> call = service.getspecificpackage("getspecificpackage",this.rec_p_id);
        call.enqueue(new Callback<Holiday_Package>() {
            @Override
            public void onResponse(Call<Holiday_Package> call, Response<Holiday_Package> response) {

                holiday_package =  response.body();
                pid.setText(holiday_package.p_id);
                location_name.setText(holiday_package.location);
                starting_place.setText(holiday_package.starting_place);


                cost.setText("Rs. "+holiday_package.cost);
                Picasso.with(img_url.getContext()).load("http://"+holiday_package.img_url).into(img_url);
                book.setEnabled(true);
                if (holiday_package.available == 0)
                {
                    book.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<Holiday_Package> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
        Call<package_detail> call_1 = service.getpackage_details("get_package_details",this.rec_p_id);
        call_1.enqueue(new Callback<package_detail>() {
            @Override
            public void onResponse(Call<package_detail> call_1, Response<package_detail> response) {
                pk_detail =  response.body();
                des.setText(pk_detail.description);
                hotel_name.setText(pk_detail.hotel_name);
                no_of_days.setText(String.valueOf(pk_detail.no_of_days));
                no_of_person.setText(String.valueOf(pk_detail.no_of_person));

                pbar.setVisibility(View.GONE);
                package_main.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<package_detail> call_1, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,booking_form.newInstance(pid.getText().toString(),no_of_person.getText().toString()),"booking_form").
                        addToBackStack(null).commit();
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
