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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class sign_up extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    EditText name_of_user,username,password,emailofuser,otpvalue;
    String nameofuser,uname,password_user,user_email;
    int otp;
    Button signup,send_otp;
    TextView log_in;
    Result_1 result_1;
    SharedPreferences sharedPreferences;
    LinearLayout email_otp;
    ProgressBar pbar;
    ScrollView main_sign;

    public sign_up() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static sign_up newInstance() {
        sign_up fragment = new sign_up();

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
        final View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        pbar = view.findViewById(R.id.progress);
        main_sign = view.findViewById(R.id.sign_main);

        sharedPreferences=getActivity().getSharedPreferences("torripo_login",Context.MODE_PRIVATE);

        log_in=view.findViewById(R.id.log_in);

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,login.newInstance(),"login").
                        addToBackStack(null).commit();
            }
        });
        email_otp = view.findViewById(R.id.email_otp);
        username=view.findViewById(R.id.uname);
        name_of_user=view.findViewById(R.id.name_of_user);
        password=view.findViewById(R.id.password);
        emailofuser=view.findViewById(R.id.email_of_user);
        send_otp = view.findViewById(R.id.otp_btn);
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameofuser=name_of_user.getText().toString();
                user_email=emailofuser.getText().toString();
                if (nameofuser.isEmpty() || user_email.isEmpty())
                {
                    Toast.makeText(getContext(),"Name or Email Field is empty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (!((MainActivity)getActivity()).isNetworkAvailable(getContext()))
                    {
                        Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).build();
                    APIService service = retrofit.create(APIService.class);
                    Call<Result_1> call = service.send_otp("send_otp",user_email,nameofuser);
                    call.enqueue(new Callback<Result_1>() {
                        @Override
                        public void onResponse(Call<Result_1> call, Response<Result_1> response) {
                            result_1 =  response.body();
                            if ("OTP Sent , Check your mail".equals(result_1.res))
                            {
                                email_otp.setVisibility(View.GONE);
                                Toast.makeText(getContext(),"OTP Sent , Check your mail",Toast.LENGTH_LONG).show();


                            }
                            else if ("Email not Found".equals(result_1.res))
                            {
                                Toast.makeText(getContext(),"Email not Found",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(),"Email already in use !!!",Toast.LENGTH_LONG).show();
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




        signup = view.findViewById(R.id.sign_up_btn);
        otpvalue = view.findViewById(R.id.OTP);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = username.getText().toString();
                nameofuser = name_of_user.getText().toString();
                password_user = password.getText().toString();
                String temp=otpvalue.getText().toString();

                if (nameofuser.isEmpty() || uname.isEmpty() || password_user.isEmpty() || temp.isEmpty())
                {
                    Toast.makeText(getContext(),"Username or Name or password or OTP is empty",Toast.LENGTH_LONG).show();
                }
                else if(password_user.length() < 6 )
                {
                    Toast.makeText(getContext(),"Password length must be more or equal to 6",Toast.LENGTH_LONG).show();
                }

                else
                {
                    otp=Integer.parseInt(temp);
                    pbar.setVisibility(View.VISIBLE);
                    if (!((MainActivity)getActivity()).isNetworkAvailable(getContext()))
                    {
                        Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).build();
                    APIService service = retrofit.create(APIService.class);
                    Call<Result_1> call = service.register_user("new_user",uname,nameofuser,password_user,user_email,otp);
                    call.enqueue(new Callback<Result_1>() {
                        @Override
                        public void onResponse(Call<Result_1> call, Response<Result_1> response) {
                            result_1 =  response.body();
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                            pbar.setVisibility(View.INVISIBLE);
                            if ("True".equals(result_1.res))
                            {

                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("uname",uname);
                                myEdit.apply();
                                Toast.makeText(getContext(),"Successfully Signed Up !!",Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.frame_layout,multiple_package.newInstance(),"multiple_package").
                                        addToBackStack("multiple_package").commit();
                            }
                            else if ("Username Already is Taken".equals(result_1.res))
                            {
                                Toast.makeText(getContext(),"Username already taken",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(),"User not registered",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result_1> call, Throwable t) {
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
