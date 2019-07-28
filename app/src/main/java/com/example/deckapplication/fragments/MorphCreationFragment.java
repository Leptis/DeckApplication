package com.example.deckapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.deckapplication.R;
import com.example.deckapplication.activities.NorrationActivity;
import com.example.deckapplication.norration.MorphCard;
import com.example.deckapplication.norration.adapters.CardAdapter;


public class MorphCreationFragment extends Fragment implements  View.OnClickListener,
                                                                TextWatcher,
                                                                CompoundButton.OnCheckedChangeListener {

    ViewGroup view_root;

    MorphCard morph_card;

    Switch          switch_skip_show;
    EditText        text_duration_value;
    EditText        text_search;
    Button          button_edit;
    public RecyclerView    recycler_view_card_list;

    public CardAdapter     card_adapter;
    OnMorphCreationListener event_listener;
//    private OnMorphCreationListener mListener;
    private Context context;

    public MorphCreationFragment() {
        // Required empty public constructor
    }


    public static MorphCreationFragment newInstance(String param1, String param2) {
        MorphCreationFragment fragment = new MorphCreationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        morph_card = new MorphCard("", "", false);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_root = ((ViewGroup) inflater.inflate(R.layout.fragment_morph_create, container, false));

        switch_skip_show = view_root.findViewById(R.id.morph_create_switch_skip_show);
        text_duration_value = view_root.findViewById(R.id.morph_create_text_duration_value);
        text_search = view_root.findViewById(R.id.morph_create_text_search);
        button_edit = view_root.findViewById(R.id.morph_create_button_edit);
        recycler_view_card_list = view_root.findViewById(R.id.morph_create_recycler_view);

        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(context);
        recycler_view_card_list.setLayoutManager(layout_manager);
        card_adapter = new CardAdapter(morph_card.pool, ((NorrationActivity) context).deck_frag.card_create_layout);
        recycler_view_card_list.setAdapter(card_adapter);

        button_edit.setOnClickListener(this);
        text_duration_value.addTextChangedListener(this);
        switch_skip_show.setOnCheckedChangeListener(this);

        return view_root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (((NorrationActivity) context).deck_frag.card_create_layout instanceof OnMorphCreationListener) {
            this.context = context;
            event_listener = ((NorrationActivity) context).deck_frag.card_create_layout;
            event_listener.onMorphCreationAttach(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMorphCreationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_edit.getId()){
            event_listener.onMorphCreationEditClick();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        event_listener.onMorphCreationDurationChanged(s.toString());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            text_duration_value.setEnabled(true);
        }else{
            text_duration_value.setEnabled(false);
        }
        event_listener.onMorphCreationSkipChanged(isChecked);
    }

    public interface OnMorphCreationListener {
        void onMorphCreationAttach(Fragment fragment);
        void onMorphCreationEditClick();
        void onMorphCreationDurationChanged(String s);
        void onMorphCreationSkipChanged(boolean is_checked);
    }

}
