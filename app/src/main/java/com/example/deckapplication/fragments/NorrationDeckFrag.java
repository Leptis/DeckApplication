package com.example.deckapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.deckapplication.R;
import com.example.deckapplication.activities.CardCreateActivity;
import com.example.deckapplication.activities.NorrationActivity;
import com.example.deckapplication.layout_creators.CardCreateLayout;
import com.example.deckapplication.layout_creators.FinalEditLayout;
import com.example.deckapplication.layout_creators.MorphEditLayout;
import com.example.deckapplication.norration.Card;
import com.example.deckapplication.norration.Norration;
import com.example.deckapplication.norration.adapters.DeckCardAdapter;


public class NorrationDeckFrag extends Fragment implements  DeckCardAdapter.OnDeckCardEventListener,
                                                            View.OnClickListener,
                                                            CardCreateLayout.OnCardCreateEventListener {

    CardCreateLayout card_create_layout = null;

    private Context context;

    final int RESULT_DECK_ONLY = 2;
    final int CARD_CREATE_ACTIVITY = 3;

    private Norration norration;

    private ViewGroup view_root;
    private EditText text_search;
    private Button button_add;
    private RecyclerView recycler_view_card_list;

    private DeckCardAdapter deck_card_adapter;


    public NorrationDeckFrag() {
        // Required empty public constructor
    }

    public static NorrationDeckFrag newInstance(String param1, String param2) {
        NorrationDeckFrag fragment = new NorrationDeckFrag();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_root = ((ViewGroup) inflater.inflate(R.layout.frag_norration_deck, container, false));

        text_search = view_root.findViewById(R.id.deck_text_search);
        button_add = view_root.findViewById(R.id.deck_button_add);

        button_add.setOnClickListener(this);

        recycler_view_card_list = view_root.findViewById(R.id.deck_recycler_view_card_list);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(context);
        recycler_view_card_list.setLayoutManager(layout_manager);
        recycler_view_card_list.setHasFixedSize(true);
        deck_card_adapter = new DeckCardAdapter(norration.deck, this);
        recycler_view_card_list.setAdapter(deck_card_adapter);

        return view_root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (context != null) {
//            ((OnMorphCreationListener) context).onInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            this.context = context;
            norration = ((NorrationActivity) context).norration;
            ((OnInteractionListener) context).onAttach(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_add.getId()){

//            Intent intent = new Intent(context, CardCreateActivity.class);
//            intent.putExtra("norration", norration);
//            startActivityForResult(intent, CARD_CREATE_ACTIVITY);
            card_create_layout = new CardCreateLayout(view_root, this);
        }
    }

    public boolean onBackPressed(){
        if (card_create_layout != null){
            if (!card_create_layout.onBackPressed()) {
                card_create_layout.onDestroy();
                card_create_layout = null;
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onDeckCardLayoutClick(String key) {

    }

    @Override
    public void onDeckCardViewClick(String key) {
        if (norration.deck.get(key).isMorph()){
            MorphEditLayout editLayout = new MorphEditLayout(view_root);
        }else{
            FinalEditLayout editLayout = new FinalEditLayout(view_root);

        }
    }

    @Override
    public void onDeckCardRemoveClick(String key) {

    }

    @Override
    public void onCardCreateCreate(CardCreateLayout layout) {
        card_create_layout = layout;
    }

    @Override
    public void onCardCreateCardCreated(Card card) {
        norration.deck.put(card.name, card);
        deck_card_adapter.refreshData(norration.deck);
        card_create_layout.onDestroy();
        card_create_layout = null;
    }

    public interface OnInteractionListener {
        // TODO: Update argument type and name
//        void onInteraction(Uri uri);
        void onAttach(NorrationDeckFrag frag);
    }
}
