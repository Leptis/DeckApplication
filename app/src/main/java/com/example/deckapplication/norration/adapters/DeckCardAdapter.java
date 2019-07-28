package com.example.deckapplication.norration.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.deckapplication.R;
import com.example.deckapplication.norration.Card;

import java.util.Map;

public class DeckCardAdapter extends RecyclerView.Adapter<DeckCardAdapter.DeckCardViewHolder> {

    Map<String, Card> deck;
    OnDeckCardEventListener on_card_click_listener;

    public DeckCardAdapter(Map<String, Card> new_deck, OnDeckCardEventListener on_card_click_listener){
        deck = new_deck;
        this.on_card_click_listener = on_card_click_listener;
    }

    public void refreshData(Map<String, Card> new_deck){
        deck = new_deck;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeckCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.deck_card_list_item, parent, false);
        DeckCardViewHolder view_holder = new DeckCardViewHolder(view);

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeckCardViewHolder view_holder, int i) {
        view_holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return deck.size();
    }

    class DeckCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View        layout_main;
        TextView    text_name_value;
        TextView    text_type_value;
        Button      button_view;
        Button      button_remode;

        public DeckCardViewHolder(@NonNull View item_view) {
            super(item_view);
            layout_main =       item_view.findViewById(R.id.deck_card_layout_main);
            text_name_value =   item_view.findViewById(R.id.deck_card_text_name_value);
            text_type_value =   item_view.findViewById(R.id.deck_card_text_type_value);
            button_view =       item_view.findViewById(R.id.deck_card_button_view);
            button_remode =     item_view.findViewById(R.id.deck_card_button_remove);

            layout_main.setOnClickListener(this);
            button_view.setOnClickListener(this);
            button_remode.setOnClickListener(this);
        }

        void bind (int ind){
            int i = 0;
            String name = "";
            for (Map.Entry<String, Card> entry : deck.entrySet()){
                if (i == ind){
                    name = entry.getKey();
                    break;
                }else{
                    i++;
                }
            }

            text_name_value.setText(name);
            Card card = deck.get(name);
            text_type_value.setText(card.isMorph() ? "Morph" : "Final");
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == layout_main.getId()) {
                on_card_click_listener.onDeckCardLayoutClick(text_name_value.getText().toString());
            }
            if (v.getId() == button_view.getId()) {
                on_card_click_listener.onDeckCardViewClick(text_name_value.getText().toString());
            }
            if (v.getId() == button_remode.getId()) {
                on_card_click_listener.onDeckCardRemoveClick(text_name_value.getText().toString());
            }
        }
    }

    public interface OnDeckCardEventListener {
        void onDeckCardLayoutClick(String key);
        void onDeckCardViewClick(String key);
        void onDeckCardRemoveClick(String key);
    }

}
