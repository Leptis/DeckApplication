package com.example.deckapplication.norration;

import java.io.Serializable;

public abstract class Card implements Serializable {
    public String name;
    public String text;

    Card(String new_name, String new_text){
        name = new_name;
        text= new_text;
    }

    public abstract boolean isMorph();

    public abstract boolean isFinal();
}
