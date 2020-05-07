package com.example.torripo;

public class package_detail {
    String p_id;
    String hotel_name;
    String description;
    int no_of_days;
    int no_of_person;

    public package_detail(String p_id,String hotel_name,String description,int no_of_days,int no_of_person)
    {
        this.p_id=p_id;
        this.hotel_name=hotel_name;
        this.description=description;
        this.no_of_days=no_of_days;
        this.no_of_person = no_of_person;
    }
}
