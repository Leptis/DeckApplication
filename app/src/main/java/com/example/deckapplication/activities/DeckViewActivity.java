package com.example.deckapplication.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deckapplication.norration.Card;
import com.example.deckapplication.norration.adapters.DeckCardAdapter;
import com.example.deckapplication.norration.Norration;
import com.example.deckapplication.R;

public class DeckViewActivity extends AppCompatActivity implements View.OnClickListener, DeckCardAdapter.OnDeckCardEventListener {

    final int RESULT_DECK_ONLY = 2;
    final int CARD_CREATE_ACTIVITY = 3;

    private Norration norration;

    private EditText        text_search;
    private Button          button_add;
    private RecyclerView    recycler_view_card_list;

    private DeckCardAdapter deck_card_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        readNorration();

        text_search = findViewById(R.id.deck_text_search);
        button_add = findViewById(R.id.deck_button_add);

        button_add.setOnClickListener(this);

        recycler_view_card_list = findViewById(R.id.deck_recycler_view_card_list);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(this);
        recycler_view_card_list.setLayoutManager(layout_manager);
        recycler_view_card_list.setHasFixedSize(true);
        deck_card_adapter = new DeckCardAdapter(norration.deck, this);
        recycler_view_card_list.setAdapter(deck_card_adapter);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("norration", norration);
        setResult(RESULT_DECK_ONLY, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_add.getId()){
//            String name = text_search.getText().toString();
//            if (!name.isEmpty()) {
//                norration.deck.put(name, new FinalCard(name, "Awesome description"));
//                deck_card_adapter.refreshData(norration.deck);
//            }
            Intent intent = new Intent(this, CardCreateActivity.class);
            intent.putExtra("norration", norration);
            startActivityForResult(intent, CARD_CREATE_ACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == CARD_CREATE_ACTIVITY){
                Card card = ((Card) data.getSerializableExtra("card"));
                norration.deck.put(card.name, card);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readNorration(){
        Intent intent = getIntent();
        norration = ((Norration) intent.getSerializableExtra("norration"));
    }


    @Override
    public void onDeckCardLayoutClick(String key) {
        Intent intent = new Intent();
        intent.putExtra("norration", norration);
        intent.putExtra("cards_to_add", key);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDeckCardViewClick(String key) {

    }

    @Override
    public void onDeckCardRemoveClick(String key) {
        norration.deck.remove(key);
        deck_card_adapter.refreshData(norration.deck);
    }
}
