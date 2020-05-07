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
import android.widget.Button;
import android.widget.EditText;
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



public class login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText username_1,password_1;
    String uname,password_user;
    Result_1 result_1;
    Button login_btn;
    SharedPreferences sharedPreferences;
    TextView signup;
    ProgressBar pbar;
    ScrollView main_view;

    public login() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static login newInstance() {
        login fragment = new login();

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
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        main_view = view.findViewById(R.id.login_main);
        pbar = view.findViewById(R.id.progress);
        sharedPreferences = this.getActivity().getSharedPreferences("torripo_login",Context.MODE_PRIVATE);
        ((MainActivity)getActivity()).hide_menu();
        signup=view.findViewById(R.id.sign_up);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,sign_up.newInstance(),"Sign_up").
                        addToBackStack(null).commit();
            }
        });
        login_btn = (Button) view.findViewById(R.id.login_btn);
        username_1=view.findViewById(R.id.username);
        password_1=view.findViewById(R.id.password);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = username_1.getText().toString();

                password_user = password_1.getText().toString();
                if (uname.isEmpty() || password_user.isEmpty())
                {
                    Toast.makeText(getContext(),"Username or password is empty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    pbar.setVisibility(View.VISIBLE);
                    if (!((MainActivity)getActivity()).isNetworkAvailable(getContext()))
                    {
                        Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).build();
                    APIService service = retrofit.create(APIService.class);
                    Call<Result_1> call = service.login_user("login",uname,password_user);
                    call.enqueue(new Callback<Result_1>() {
                        @Override
                        public void onResponse(Call<Result_1> call, Response<Result_1> response) {
                            result_1 =  response.body();
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                            if ("True".equals(result_1.res))
                            {
                                SharedPreferences.Editor myEdit=sharedPreferences.edit();
                                myEdit.putString("uname",uname);
                                myEdit.apply();

                                getActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.frame_layout,multiple_package.newInstance(),"multiple_package").
                                        addToBackStack("multiple_package").commit();

                            }
                            else
                            {
                                pbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(),"Username or Password does not match",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result_1> call, Throwable t) {
                            pbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(),"Error Encountered",Toast.LENGTH_LONG).show();
                            Log.e("Error", t.getMessage());
                        }
                    });
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

}
