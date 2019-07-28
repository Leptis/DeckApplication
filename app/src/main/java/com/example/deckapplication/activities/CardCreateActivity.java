package com.example.deckapplication.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.deckapplication.norration.adapters.CardAdapter;

public class CardCreateActivity extends AppCompatActivity implements    AdapterView.OnItemSelectedListener,
                                                                        View.OnClickListener,
                                                                        CardAdapter.OnCardEventListener {

    EditText text_name;
    Spinner spinner_type;
    Button button_create;
    ViewGroup fragment_additional;

    ArrayAdapter<String> spinner_type_adapter;

    Fragment current_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_card_create);

        text_name = findViewById(R.id.card_create_text_name);
        spinner_type = findViewById(R.id.card_create_spinner_type);
        button_create = findViewById(R.id.card_create_button_create);
        fragment_additional = findViewById(R.id.card_create_fragment_additional);

//        ArrayList<String> tmp_array_list = new ArrayList<>();
//        tmp_array_list.add()
        String[] tmp_mass = {"Final", "Morph"};
        spinner_type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tmp_mass);
        spinner_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(spinner_type_adapter);

        spinner_type.setOnItemSelectedListener(this);
        button_create.setOnClickListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type = ((TextView) view).getText().toString();
//        int idd = fragment_additional.getId();
        Fragment fragment_to_plase = null;

        if (type == "Morph" && current_fragment instanceof FinalCreationFragment){
            fragment_to_plase = new MorphCreationFragment();
        }
        if (type == "Final" && current_fragment instanceof MorphCreationFragment){
            fragment_to_plase = new FinalCreationFragment();
        }

        if (fragment_to_plase != null){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.card_create_fragment_additional, fragment_to_plase);
            transaction.commit();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

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
}
