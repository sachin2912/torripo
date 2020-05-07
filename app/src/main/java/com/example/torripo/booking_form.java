package com.example.torripo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class booking_form extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private String rec_p_id;
    private String nop;
    private static final String ARG_PARAM1 = "parm1";
    private static final String ARG_PARAM2 = "parm2";
    private String uname;
    Booking_details booking_details;
    DatePicker startdate;
    EditText no_of_persons;
    Button proceed;
    String start_date;
    int max_nop;
    int noofperson;
    ProgressBar pbar;
    SharedPreferences sharedPreferences;
    public booking_form() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static booking_form newInstance(String rec_p_id,String nop) {
        booking_form fragment = new booking_form();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, rec_p_id);
        args.putString(ARG_PARAM2, nop);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rec_p_id = getArguments().getString(ARG_PARAM1);
            max_nop = Integer.parseInt(getArguments().getString(ARG_PARAM2));
        }
        sharedPreferences=getActivity().getSharedPreferences("torripo_login",Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("uname","none");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_booking_form, container, false);
        no_of_persons=view.findViewById(R.id.no_of_person_booked);
        pbar = view.findViewById(R.id.progress);
        startdate = view.findViewById(R.id.starting_date);
        startdate.setMinDate(System.currentTimeMillis()+(1000*60*60*24*5));

        startdate.setMaxDate(System.currentTimeMillis()+(1000*60*60*24*24));
        proceed = view.findViewById(R.id.submit_btn);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_date=startdate.getDayOfMonth()+"/"+(startdate.getMonth()+1)+"/"+startdate.getYear();
                String temp;
                temp=no_of_persons.getText().toString();
                if (temp.isEmpty())
                {
                    Toast.makeText(getContext(),"Please enter no of Persons !!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    noofperson = Integer.parseInt(temp);
                    if (noofperson > max_nop || noofperson == 0)
                    {
                        Toast.makeText(getContext(), "Max no of traveller are " + max_nop, Toast.LENGTH_LONG).show();
                    } else {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                        if (!((MainActivity)getActivity()).isNetworkAvailable(getContext()))
                        {
                            Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        }
                        pbar.setVisibility(View.VISIBLE);
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                                addConverterFactory(GsonConverterFactory.create()).build();
                        APIService service = retrofit.create(APIService.class);
                        Call<Booking_details> call = service.booking("booking", start_date, rec_p_id, uname, noofperson);
                        call.enqueue(new Callback<Booking_details>() {
                            @Override
                            public void onResponse(Call<Booking_details> call, Response<Booking_details> response) {
                                booking_details = response.body();
                                if ("error".equals(booking_details.uname)) {
                                    pbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getContext(), "Error : User Already booked the package", Toast.LENGTH_LONG).show();
                                } else {
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.frame_layout, passenger_form.newInstance(String.valueOf(noofperson), String.valueOf(booking_details.b_id)), "passenger_form").
                                            addToBackStack("multiple_package").commit();
                                }
                            }

                            @Override
                            public void onFailure(Call<Booking_details> call, Throwable t) {
                                pbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(),"Error Encountered !!",Toast.LENGTH_LONG).show();
                                Log.e("Error", t.getMessage());
                            }
                        });

                    }
                }

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
