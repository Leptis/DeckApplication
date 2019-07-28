package com.example.deckapplication.norration.adapters;

import android.media.MediaDrm;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.deckapplication.R;
import com.example.deckapplication.norration.Card;
import com.example.deckapplication.norration.Probability;

import java.util.Map;

public class CardCheckAdapter extends RecyclerView.Adapter<CardCheckAdapter.CardCheckViewHolder> {

    Map<String, Probability> turn;
    Map<String, Card> pool;
    OnEventListener event_listener;
//    Map<String, Boolean> checked;

    public CardCheckAdapter(Map<String, Card> pool, OnEventListener event_listener){
        this.pool = pool;
        this.turn = turn;
        this.event_listener = event_listener;
    }

    @NonNull
    @Override
    public CardCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_card_check, parent, false);
        CardCheckViewHolder view_holder = new CardCheckViewHolder(view);

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardCheckViewHolder holder, int i) {
        holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return pool.size();
    }

    class CardCheckViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        TextView    text_name;
        TextView    text_type;
        Switch      switch_in_use;

        public CardCheckViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name =     itemView.findViewById(R.id.card_check_text_name);
            text_type =     itemView.findViewById(R.id.card_check_text_type);
            switch_in_use = itemView.findViewById(R.id.card_check_switch_in_use);

            switch_in_use.setOnCheckedChangeListener(this);
        }

        void bind(int ind){

            String name = "";
            int i = 0;
            for (Map.Entry<String, Card> entry : pool.entrySet()){
                if (i == ind){
                    name = entry.getKey();
                    break;
                }else{
                    i++;
                }
            }

            text_name.setText(name);
            text_type.setText(pool.get(name).isFinal() ? new String("Final") : new String("Morph"));
            switch_in_use.setChecked(event_listener.onInUseInit(name));
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            event_listener.onInUsChanged(text_name.getText().toString(), isChecked);
        }
    }

    public interface OnEventListener{
        boolean onInUseInit(String key);
        void    onInUsChanged(String key, boolean is_checked);
    }
}
