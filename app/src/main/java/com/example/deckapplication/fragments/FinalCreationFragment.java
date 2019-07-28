package com.example.deckapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deckapplication.R;
import com.example.deckapplication.activities.NorrationActivity;



public class FinalCreationFragment extends Fragment {


    private OnInteractionListener event_listener;

    public FinalCreationFragment() {
        // Required empty public constructor
    }


    public static FinalCreationFragment newInstance(String param1, String param2) {
        FinalCreationFragment fragment = new FinalCreationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_final_create, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (((NorrationActivity) context).deck_frag.card_create_layout instanceof OnInteractionListener) {
            event_listener = ((NorrationActivity) context).deck_frag.card_create_layout;
            event_listener.onAttach(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMorphCreationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        event_listener = null;
    }

    public interface OnInteractionListener {
        void onAttach(Fragment fragment);
    }

    String getFragTag(){
        return "FinalCreationFragment";
    }
}
