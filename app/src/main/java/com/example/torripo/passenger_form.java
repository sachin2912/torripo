package com.example.torripo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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


public class passenger_form extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int no_of_person,b_id;
    String values;
    LinearLayout main_layout;
    Button submit_btn;
    ProgressBar pbar;
    Result_1 result_1;
    public passenger_form() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static passenger_form newInstance(String param1, String param2) {
        passenger_form fragment = new passenger_form();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            no_of_person=Integer.parseInt(mParam1);
            b_id = Integer.parseInt(mParam2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_passenger_form, container, false);
        pbar = view.findViewById(R.id.progress);
        main_layout = view.findViewById(R.id.main_layout);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        submit_btn=view.findViewById(R.id.add_btn);
        for(int i=0;i<no_of_person;i++)
        {
            LinearLayout new_form = new LinearLayout(getContext());
            new_form.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            new_form.setOrientation(LinearLayout.VERTICAL);

            new_form.setPadding(15,15,15,15);

            EditText name= new EditText(getContext());
            EditText age= new EditText(getContext());
            EditText gender= new EditText(getContext());
            TextView title = new TextView(getContext());
            title.setText("Traveller "+(i+1));
            title.setGravity(Gravity.CENTER);
            title.setPadding(5,50,5,50);

            name.setTag("name_of_user".concat(String.valueOf(i+1)));
            name.setHint("Name of traveller "+(i+1));
            name.setPadding(5,50,5,50);
            age.setTag("age_of_user".concat(String.valueOf(i+1)));
            age.setInputType(InputType.TYPE_CLASS_NUMBER);
            age.setPadding(5,50,5,50);
            age.setHint("Age of traveller "+(i+1));
            gender.setTag("gender_of_user".concat(String.valueOf(i+1)));
            gender.setPadding(5,50,5,50);
            gender.setHint("Gender of traveller "+(i+1));
            new_form.addView(title);
            new_form.addView(name);
            new_form.addView(age);
            new_form.addView(gender);

            main_layout.addView(new_form);
        }
        values="";
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fl = 0;
                for (int i = 0; i < no_of_person; i++) {
                    EditText ed_name = new EditText(getContext());
                    EditText ed_age = new EditText(getContext());
                    EditText ed_gender = new EditText(getContext());
                    ed_name = view.findViewWithTag("name_of_user".concat(String.valueOf(i+1)));
                    ed_age = view.findViewWithTag("age_of_user".concat(String.valueOf(i+1)));
                    ed_gender = view.findViewWithTag("gender_of_user".concat(String.valueOf(i+1)));

                    if (ed_name.getText().toString().isEmpty() || ed_age.getText().toString().isEmpty() || ed_gender.getText().toString().isEmpty() ) {
                        Toast.makeText(getContext(), "Fill all the Fields", Toast.LENGTH_LONG).show();
                        fl = 1;
                        break;
                    }
                    if (!ed_age.getText().toString().isEmpty())
                    {
                        int age;
                        age=Integer.parseInt(ed_age.getText().toString());
                        if (age == 0 )
                        {
                            Toast.makeText(getContext(),"Age cannot be 0 !!!",Toast.LENGTH_LONG).show();
                            fl=1;
                            break;
                        }
                    }
                    if (!"M".equalsIgnoreCase(ed_gender.getText().toString())) {
                        if (!"F".equalsIgnoreCase(ed_gender.getText().toString())) {
                            if (!"O".equalsIgnoreCase(ed_gender.getText().toString())) {
                                Toast.makeText(getContext(), "Gender needs to be M or F or O !!! , for traveller"+ (i+2), Toast.LENGTH_LONG).show();
                                fl = 1;
                                break;
                            }

                        }

                    }
                }
                pbar.setVisibility(View.VISIBLE);
                if (fl == 0) {
                    for (int i = 0; i < no_of_person; i++) {
                        values += "(" + b_id + ",";
                        EditText ed_name = new EditText(getContext());
                        EditText ed_age = new EditText(getContext());
                        EditText ed_gender = new EditText(getContext());
                        ed_name = view.findViewWithTag("name_of_user".concat(String.valueOf(i+1)));
                        ed_age = view.findViewWithTag("age_of_user".concat(String.valueOf(i+1)));
                        ed_gender = view.findViewWithTag("gender_of_user".concat(String.valueOf(i+1)));
                        values += "'" + ed_name.getText().toString() + "'," + ed_age.getText().toString() + ",'" + ed_gender.getText().toString() + "')";
                        if (i != no_of_person-1)
                        {
                            values+=",";
                        }
                    }
                    if (!((MainActivity)getActivity()).isNetworkAvailable(getContext()))
                    {
                        Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).build();
                    final APIService service = retrofit.create(APIService.class);
                    Call<Result_1> call = service.addpassenger("add_passenger",values);
                    call.enqueue(new Callback<Result_1>() {
                        @Override
                        public void onResponse(Call<Result_1> call, Response<Result_1> response) {

                            result_1 =  response.body();
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                            if ("true".equals(result_1.res))
                            {
                                ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                                Toast.makeText(getContext(),"Booking complete , Enjoy the holidays ",Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.frame_layout,bookings_view.newInstance(),"bookings_view").
                                        addToBackStack("multiple_package").commit();

                            }
                            else
                            {

                                ((AppCompatActivity)getActivity()).getSupportActionBar().show();

                                Toast.makeText(getContext(),"Booking not Completed",Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.frame_layout,bookings_view.newInstance(),"bookings_view").
                                        addToBackStack("multiple_package").commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result_1> call, Throwable t) {
                            Log.e("Error", t.getMessage());
                        }
                    });
                }
            }
        });

        return  view;
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
