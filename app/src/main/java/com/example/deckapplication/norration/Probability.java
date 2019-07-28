package com.example.deckapplication.norration;

import java.io.Serializable;

public class Probability implements Serializable {
    public int probability;
    public boolean fixed;

    public Probability(int probability, boolean fixed){
        this.probability = probability;
        this.fixed = fixed;
    }
}
