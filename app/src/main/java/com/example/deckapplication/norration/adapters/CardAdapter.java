package com.example.deckapplication.norration.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.deckapplication.R;
import com.example.deckapplication.norration.Probability;

import java.util.Map;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    OnCardEventListener on_card_event_listener;

    Map<String, Probability> card_list;

    public CardAdapter(Map<String, Probability> new_card_list, OnCardEventListener on_card_event_listener){
        card_list = new_card_list;
        this.on_card_event_listener = on_card_event_listener;
    }

    public void refreshData(Map<String, Probability> new_card_list){
        card_list = new_card_list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_list_item, parent, false);

        CardViewHolder view_holder = new CardViewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder view_holder, int i) {
        view_holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return card_list.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener,
                                                                    CompoundButton.OnCheckedChangeListener,
                                                                    View.OnClickListener,
                                                                    TextView.OnEditorActionListener,
                                                                    TextWatcher{

        TextView    text_name;
        SeekBar     seek_prob;
        Switch      switch_fix;
        EditText    text_prob;
        Button      button_view;
        Button      button_remove;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name =     itemView.findViewById(R.id.card_item_text_name);
            seek_prob =     itemView.findViewById(R.id.card_item_seek_prob);
            switch_fix =    itemView.findViewById(R.id.card_item_switch_fix);
            text_prob =     itemView.findViewById(R.id.card_item_text_prob);
            button_view =   itemView.findViewById(R.id.card_item_button_view);
            button_remove = itemView.findViewById(R.id.card_item_button_remove);
        }

        void bind(int ind){
            int i = 0;
            String name = "";
            for (Map.Entry<String, Probability> entry : card_list.entrySet()){
                if (i == ind){
                    name = entry.getKey();
                    break;
                }else{
                    i++;
                }
            }

            text_name.setText(name);
            seek_prob.setProgress(card_list.get(name).probability);
            text_prob.setText(Integer.toString(card_list.get(name).probability));
            switch_fix.setChecked(card_list.get(name).fixed);

            seek_prob.setOnSeekBarChangeListener(this);
//            text_prob.setOnEditorActionListener(this);
//            text_prob.setOnFocusChangeListener(this);
            text_prob.addTextChangedListener(this);
            switch_fix.setOnCheckedChangeListener(this);
            button_view.setOnClickListener(this);
            button_remove.setOnClickListener(this);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            card_list.get(text_name.getText().toString()).probability = progress;
            text_prob.setText(Integer.toString(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            on_card_event_listener.onProbabilityChangedListener(text_name.getText().toString(), seekBar.getProgress());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().isEmpty()) {
                card_list.get(text_name.getText().toString()).probability = Integer.parseInt(s.toString());
                int tmp = text_prob.getSelectionStart();
                seek_prob.setProgress(Integer.parseInt(text_prob.getText().toString()));
                text_prob.setSelection(tmp);
                on_card_event_listener.onProbabilityChangedListener(text_name.getText().toString(), Integer.parseInt(s.toString()));
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            card_list.get(text_name.getText().toString()).fixed = isChecked;
            on_card_event_listener.onSwitchFixCheckedChanged(text_name.getText().toString(), isChecked);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == button_view.getId()){
                on_card_event_listener.onViewButtonClick(text_name.getText().toString());
            }
            if (v.getId() == button_remove.getId()){
                on_card_event_listener.onRemoveButtonClick(text_name.getText().toString());
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                if (!event.isShiftPressed()) {
//                    // the user is done typing.
//
//                    if (!v.getText().toString().isEmpty()) {
//                        card_list.get(text_name.getText().toString()).probability = Integer.parseInt(v.getText().toString());
//                        seek_prob.setProgress(Integer.parseInt(v.getText().toString()));
//                        on_card_event_listener.onProbabilityChangedListener(text_name.getText().toString(), Integer.parseInt(v.getText().toString()));
//                    }
//                    return true; // consume.
//                }
//            }
            return false; // pass on to other listeners. } });
        }


    }

    public interface OnCardEventListener {
//        void onSeekProbProgressChanged(String key, int progress);
//        void onTextProbTextChangedListener(String key, int value);
        void onProbabilityChangedListener(String key, int value);
        void onSwitchFixCheckedChanged(String key, boolean is_checked);
        void onViewButtonClick(String key);
        void onRemoveButtonClick(String key);
    }
}
