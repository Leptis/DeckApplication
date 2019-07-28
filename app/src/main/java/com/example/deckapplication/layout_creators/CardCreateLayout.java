package com.example.deckapplication.layout_creators;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deckapplication.fragments.FinalCreationFragment;
import com.example.deckapplication.fragments.MorphCreationFragment;
import com.example.deckapplication.R;
import com.example.deckapplication.activities.NorrationActivity;
import com.example.deckapplication.norration.Card;
import com.example.deckapplication.norration.FinalCard;
import com.example.deckapplication.norration.MorphCard;
import com.example.deckapplication.norration.Probability;
import com.example.deckapplication.norration.adapters.CardAdapter;
import com.example.deckapplication.norration.adapters.CardCheckAdapter;

import java.util.Map;
import java.util.TreeMap;

public class CardCreateLayout implements    AdapterView.OnItemSelectedListener,
                                            View.OnClickListener,
                                            FinalCreationFragment.OnInteractionListener,
                                            MorphCreationFragment.OnMorphCreationListener,
                                            CardAdapter.OnCardEventListener,
                                            DeckListLayout.OnDeckListEventListener {

    Context context;

    Card card_to_create;
    Map<String, Probability> pool_for_card;

    ViewGroup parent;
    OnCardCreateEventListener event_listener;

    EditText text_name;
    EditText text_text;
    Spinner spinner_type;
    Button button_create;
    ViewGroup fragment_additional;
    ViewGroup view_root;

    ArrayAdapter<String> spinner_type_adapter;

    FragmentManager manager;
    Fragment current_fragment;
    public DeckListLayout deck_list_layout = null;

    public CardCreateLayout(ViewGroup parent, OnCardCreateEventListener event_listener){
        context = parent.getContext();
        manager = ((NorrationActivity) parent.getContext()).getSupportFragmentManager();
        this.parent = parent;
        this.event_listener = event_listener;
        event_listener.onCardCreateCreate(this);
        onCreate();
    }

    void onCreate(){

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view_root = ((ViewGroup) inflater.inflate(R.layout.layout_card_create, parent, false));

        text_name = view_root.findViewById(R.id.card_create_text_name);
        text_text = view_root.findViewById(R.id.card_create_text_text);
        spinner_type = view_root.findViewById(R.id.card_create_spinner_type);
        button_create = view_root.findViewById(R.id.card_create_button_create);
        fragment_additional = view_root.findViewById(R.id.card_create_fragment_additional);

        String[] tmp_mass = {"Final", "Morph"};
        spinner_type_adapter = new ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item, tmp_mass);
        spinner_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(spinner_type_adapter);

        spinner_type.setOnItemSelectedListener(this);
        button_create.setOnClickListener(this);

        setEnabledRequrse(parent, false);

        ViewGroup.LayoutParams layout_params = view_root.getLayoutParams();
        layout_params.width = ConstraintSet.MATCH_CONSTRAINT;
        layout_params.height = ConstraintSet.MATCH_CONSTRAINT;
        view_root.setTranslationZ(10);
        view_root.setLayoutParams(layout_params);

        parent.addView(view_root);
        ConstraintSet constraint_set = new ConstraintSet();
        constraint_set.clone(((ConstraintLayout) parent));
        constraint_set.connect(view_root.getId(), ConstraintSet.START, parent.getId(), ConstraintSet.START, 100);
        constraint_set.connect(view_root.getId(), ConstraintSet.TOP, parent.getId(), ConstraintSet.TOP, 100);
        constraint_set.connect(view_root.getId(), ConstraintSet.END, parent.getId(), ConstraintSet.END, 100);
        constraint_set.connect(view_root.getId(), ConstraintSet.BOTTOM, parent.getId(), ConstraintSet.BOTTOM, 100);
        constraint_set.applyTo(((ConstraintLayout) parent));

        current_fragment = manager.findFragmentById(R.id.card_create_fragment_additional);

        card_to_create = new FinalCard("", "");
        pool_for_card = new TreeMap<>();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type = ((TextView) view).getText().toString();
//        int idd = fragment_additional.getId();
        Fragment fragment_to_plase = null;



        if (type == "Morph" && current_fragment instanceof FinalCreationFragment){
            fragment_to_plase = manager.findFragmentByTag("MorphCreationFragment");
            if (fragment_to_plase == null){
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.card_create_fragment_additional, new MorphCreationFragment(), "MorphCreationFragment");
                transaction.commitNow();
                fragment_to_plase = manager.findFragmentByTag("MorphCreationFragment");

            }
        }
        if (type == "Final" && current_fragment instanceof MorphCreationFragment){
            fragment_to_plase = manager.findFragmentByTag("FinalCreationFragment");
        }

        if (fragment_to_plase != null){
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.card_create_fragment_additional, fragment_to_plase);
            transaction.commitNow();
//            manager.get
            if (fragment_to_plase instanceof FinalCreationFragment){
                onBecomeFinal();
            }else{
                onBecomeMorph();
            }
        }
        current_fragment = manager.findFragmentById(R.id.card_create_fragment_additional);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_create.getId()){
            card_to_create.name = text_name.getText().toString();
            card_to_create.text = text_text.getText().toString();
            if (card_to_create instanceof MorphCard){
                ((MorphCard) card_to_create).pool = pool_for_card;
            }
            event_listener.onCardCreateCardCreated(card_to_create);
        }
    }

    @Override
    public void onAttach(Fragment fragment) {
    }


    public void onDestroy(){

        while (true){
            Fragment tmp_frag = manager.findFragmentById(R.id.card_create_fragment_additional);
            if (tmp_frag != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(tmp_frag);
                transaction.commitNow();
            }else{
                break;
            }
        }

        parent.removeView(view_root);
        setEnabledRequrse(parent, true);
    }

    void setEnabledRequrse(View v, boolean enabled){
        v.setEnabled(enabled);
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View c = ((ViewGroup) v).getChildAt(i);
                setEnabledRequrse(c, enabled);
            }
        }
    }

    void onBecomeFinal(){
        ViewGroup.LayoutParams layout_params;

        layout_params = text_text.getLayoutParams();
        layout_params.height = ConstraintSet.MATCH_CONSTRAINT;
        text_text.setLayoutParams(layout_params);

        layout_params = fragment_additional.getLayoutParams();
        layout_params.height = ConstraintSet.MATCH_CONSTRAINT;
        fragment_additional.setLayoutParams(layout_params);

        ConstraintSet constraint_set = new ConstraintSet();
        constraint_set.clone(((ConstraintLayout) view_root));
        constraint_set.clear(R.id.card_create_fragment_additional, ConstraintSet.TOP);
        constraint_set.connect(R.id.card_create_text_text, ConstraintSet.BOTTOM, R.id.card_create_fragment_additional, ConstraintSet.TOP);
        constraint_set.applyTo(((ConstraintLayout) view_root));

        card_to_create = new FinalCard(text_name.getText().toString(), text_text.getText().toString());
    }

    void onBecomeMorph(){
        ViewGroup.LayoutParams layout_params;

        layout_params = text_text.getLayoutParams();
        layout_params.height = 300;
        text_text.setLayoutParams(layout_params);

        layout_params = fragment_additional.getLayoutParams();
        layout_params.height = ConstraintSet.MATCH_CONSTRAINT;
        fragment_additional.setLayoutParams(layout_params);

        ConstraintSet constraint_set = new ConstraintSet();
        constraint_set.clone(((ConstraintLayout) view_root));
        constraint_set.clear(R.id.card_create_text_text, ConstraintSet.BOTTOM);
        constraint_set.connect(R.id.card_create_fragment_additional, ConstraintSet.TOP, R.id.card_create_text_text, ConstraintSet.BOTTOM);
        constraint_set.applyTo(((ConstraintLayout) view_root));

        card_to_create = new MorphCard(text_name.getText().toString(), text_text.getText().toString(), false);
    }

    public boolean onBackPressed(){
        if (deck_list_layout != null){
            deck_list_layout.onDestroy();
            deck_list_layout = null;
            ((MorphCreationFragment) current_fragment).card_adapter.refreshData(pool_for_card);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onProbabilityChangedListener(String key, int value) {

    }

    @Override
    public void onSwitchFixCheckedChanged(String key, boolean is_checked) {

    }

    @Override
    public void onViewButtonClick(String key) {

    }

    @Override
    public void onRemoveButtonClick(String key) {

    }

    @Override
    public void onMorphCreationAttach(Fragment fragment) {

    }

    @Override
    public void onMorphCreationEditClick() {
        deck_list_layout = new DeckListLayout(view_root, this);
    }

    @Override
    public void onMorphCreationDurationChanged(String s) {
        ((MorphCard) card_to_create).duration = Integer.parseInt(s);
    }

    @Override
    public void onMorphCreationSkipChanged(boolean is_checked) {
        ((MorphCard) card_to_create).skip_show = is_checked;
    }

    @Override
    public boolean onDeckListInUseInit(String key) {
        return pool_for_card.containsKey(key);
    }

    @Override
    public void onDeckListInUsChanged(String key, boolean is_checked) {
        if (is_checked) {
            pool_for_card.put(key, new Probability(0, false));
        }else{
            pool_for_card.remove(key);
        }
//        ((MorphCreationFragment) current_fragment).card_adapter.refreshData(pool_for_card);
//        setEnabledRequrse(((MorphCreationFragment) current_fragment).recycler_view_card_list, false);
    }

    @Override
    public void onDeckListCreate(CardCheckAdapter adapter) {    }

    public interface OnCardCreateEventListener{
        void onCardCreateCreate(CardCreateLayout layout);
        void onCardCreateCardCreated(Card card);
    }
}
