package com.example.deckapplication.norration;

import java.io.Serializable;

public class FinalCard extends Card implements Serializable {
    public FinalCard(String new_name, String new_text){
        super(new_name, new_text);
    }

    @Override
    public boolean isMorph() {
        return false;
    }

    @Override
    public boolean isFinal() {
        return true;
    }
}
