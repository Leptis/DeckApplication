package com.example.deckapplication.norration;


import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Turn implements Serializable {
    public Map<String, Probability> pool;

    Turn(){
        pool = new TreeMap<>();
    }

    String getFromPool(){
        int sum = 0;
        for (Map.Entry<String, Probability> entry : pool.entrySet()){
            sum += entry.getValue().probability;
        }

        Random rand = new Random(System.currentTimeMillis());
        int num = 1 + rand.nextInt(sum);


        for (Map.Entry<String, Probability> entry : pool.entrySet()){
            num -= entry.getValue().probability;
            if (num <= 0){
                return entry.getKey();
            }
        }

        return "";
    }
}
