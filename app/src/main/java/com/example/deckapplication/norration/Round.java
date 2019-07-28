package com.example.deckapplication.norration;

import java.io.Serializable;
import java.util.ArrayList;

public class Round implements Serializable {
    public ArrayList<Turn> turns;

    Round(){
        turns = new ArrayList<Turn>();
    }

    public void fitTurnsToSize(int size){
        while(turns.size() < size){
            turns.add(new Turn());
        }
        while(turns.size() > size){
            turns.remove(turns.size() - 1);
        }
    }
}

