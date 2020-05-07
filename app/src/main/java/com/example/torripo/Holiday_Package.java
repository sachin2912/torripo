package com.example.torripo;

public class Holiday_Package {
    String p_id;
    String location;
    String cost;
    int count;
    int available;
    int total_count;
    String starting_place;
    String img_url;

    public Holiday_Package(String p_id,String location,String cost,int count,int available,int total_count,String starting_place,String img_url)
    {
        this.p_id=p_id;
        this.location=location;
        this.cost=cost;
        this.count=count;
        this.available=available;
        this.total_count=total_count;
        this.img_url=img_url;
        this.starting_place=starting_place;
    }
}
