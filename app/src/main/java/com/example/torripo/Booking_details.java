package com.example.torripo;

public class Booking_details {
    int b_id;
    String start_date;
    String booking_date;
    String p_id;
    String uname;
    String status;
    int no_of_persons_booked;

    public Booking_details(int b_id,String start_date,String booking_date,String p_id,String uname,String status,int no_of_persons_booked)
    {
        this.b_id=b_id;
        this.start_date=start_date;
        this.booking_date=booking_date;
        this.p_id=p_id;
        this.uname=uname;
        this.status=status;
        this.no_of_persons_booked=no_of_persons_booked;
    }

}
