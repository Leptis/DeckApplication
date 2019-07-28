package com.example.deckapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.deckapplication.R;
import com.example.deckapplication.norration.adapters.SectionsPagerAdapter;
import com.example.deckapplication.fragments.NorrationDeckFrag;
import com.example.deckapplication.fragments.NorrationGameFrag;
import com.example.deckapplication.norration.Norration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NorrationActivity extends AppCompatActivity implements NorrationGameFrag.OnInteractionListener,
                                                                    NorrationDeckFrag.OnInteractionListener,
                                                                    ViewPager.OnPageChangeListener {

    public Norration norration;

    ViewPager viewPager;
    TabLayout tabs;

    SectionsPagerAdapter sectionsPagerAdapter;

    public NorrationDeckFrag deck_frag;
    NorrationGameFrag game_frag;

    int current_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readNorration();
        setContentView(R.layout.activity_norration);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onBackPressed() {

        if (current_page == 0 && !game_frag.onBackPressed()){
            super.onBackPressed();
        }
        if (current_page == 1 && !deck_frag.onBackPressed()){
            super.onBackPressed();
        }
    }

    private void readNorration(){
        Intent intent = getIntent();
        String nor_file = intent.getStringExtra("nor_file");
        try {
            FileInputStream file_input_stream = openFileInput(nor_file);
            ObjectInputStream object_input_stream = new ObjectInputStream(file_input_stream);
            norration = (Norration) object_input_stream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(NorrationGameFrag frag) {
        game_frag = frag;
    }

    @Override
    public void onAttach(NorrationDeckFrag frag) {
        deck_frag = frag;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        current_page = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}