package com.example.deckapplication.norration;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MorphCard extends Card implements Serializable {
    public Map<String, Probability> pool;
    public boolean skip_show;
    public Integer duration;

    public MorphCard(String new_name, String new_text, boolean new_auto_morph) {
        super(new_name, new_text);
        skip_show = new_auto_morph;
        pool = new TreeMap<>();
        duration = 3;
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

    @Override
    public boolean isMorph() {
        return true;
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
