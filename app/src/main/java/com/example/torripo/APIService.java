package com.example.torripo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("mad_project.php")
    Call<List<Holiday_Package>> getholiday_packages(@Query("action") String action);

    @GET("mad_project.php")
    Call<Result_1> send_otp(@Query("action") String action,@Query("email") String email,@Query("name") String name);

    @GET("mad_project.php")
    Call<package_detail> getpackage_details(@Query("action") String action , @Query("p_id") String p_id);

    @GET("mad_project.php")
    Call<user_detail> getuserdetail(@Query("action") String action,@Query("uname") String uname);

    @GET("mad_project.php")
    Call<Result_1> register_user(@Query("action") String action, @Query("uname") String uname,@Query("name") String name,@Query("password") String password,@Query("email") String email,@Query("otp") int OTP);

    @GET("mad_project.php")
    Call<Result_1> login_user(@Query("action") String action, @Query("uname") String uname,@Query("password") String password);

    @GET("mad_project.php")
    Call<List<Booking_details>> get_booking_details(@Query("action") String action,@Query("uname") String uname);

    @GET("mad_project.php")
    Call<Result_1> addpassenger(@Query("action") String action,@Query("values") String values);

    @GET("mad_project.php")
    Call<List<Passenger_details>> get_passenger_details(@Query("action") String action,@Query("b_id") Integer b_id);

    @GET("mad_project.php")
    Call<Result_1> cancelbooking(@Query("action") String action,@Query("b_id") Integer b_id ,@Query("p_id") String p_id);

    @GET("mad_project.php")
    Call<Holiday_Package> getspecificpackage(@Query("action") String action,@Query("p_id") String p_id);

    @GET("mad_project.php")
    Call<Booking_details> booking(@Query("action") String action,@Query("start_date") String start_date,@Query("p_id") String p_id,@Query("uname") String uname,@Query("no_of_person") int noofperson);
}
