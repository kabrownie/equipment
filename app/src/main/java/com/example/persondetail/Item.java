package com.example.persondetail;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item {

    public String name;
    public String description;

    public  String price;
    public  String number;


    public Item(String name, String desc , String price , String number) {
        this.name = name;
        this.description = desc;
        this.price = price;
        this.number = number;
    }

}
