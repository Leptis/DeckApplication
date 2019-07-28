package com.example.deckapplication.layout_creators;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.deckapplication.R;
import com.example.deckapplication.activities.NorrationActivity;
import com.example.deckapplication.norration.Norration;
import com.example.deckapplication.norration.Probability;
import com.example.deckapplication.norration.adapters.CardCheckAdapter;

import java.util.Map;

public class DeckListLayout implements CardCheckAdapter.OnEventListener {

    OnDeckListEventListener event_listener;
    Norration norration;

    ViewGroup view_root;
    ViewGroup parent;
    EditText        text_search;
    RecyclerView    recycler_list;

    CardCheckAdapter adapter;

    public DeckListLayout(ViewGroup parent, OnDeckListEventListener event_listener){
        this.parent = parent;
        norration = ((NorrationActivity) parent.getContext()).norration;
        this.event_listener = event_listener;
        onCreate();
    }

    void onCreate(){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view_root = (ViewGroup) inflater.inflate(R.layout.layout_deck_list, parent, false);
        text_search = view_root.findViewById(R.id.deck_list_text_search);
        recycler_list = view_root.findViewById(R.id.deck_list_recycler_list);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(parent.getContext());
        adapter = new CardCheckAdapter(norration.deck, this);
        recycler_list.setLayoutManager(manager);
        recycler_list.setAdapter(adapter);

        event_listener.onDeckListCreate(adapter);


//        view_root.setClickable(true);
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
    }

    public void onDestroy(){
        parent.removeView(view_root);
        setEnabledRequrse(parent, true);
    }

    @Override
    public boolean onInUseInit(String key) {
        return event_listener.onDeckListInUseInit(key);
    }

    @Override
    public void onInUsChanged(String key, boolean is_checked) {
        event_listener.onDeckListInUsChanged(key, is_checked);
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

    public interface OnDeckListEventListener{
        boolean onDeckListInUseInit(String key);
        void onDeckListInUsChanged(String key, boolean is_checked);
        void onDeckListCreate(CardCheckAdapter adapter);
    }
}
