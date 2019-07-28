package com.example.deckapplication.norration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Norration implements Serializable {
    public String name;
    public ArrayList<Round> rounds;
    public Map<String, Card> deck;

    public Norration(String new_name){
        name = new_name;
        rounds = new ArrayList<Round>();
        deck = new TreeMap<>();
    }

    public void fitRoundsToSize(int size){
        while(rounds.size() < size){
            rounds.add(new Round());
        }
        while(rounds.size() > size){
            rounds.remove(rounds.size() - 1);
        }
    }
}
