package com.example.deckapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.deckapplication.R;
import com.example.deckapplication.activities.NorrationActivity;
import com.example.deckapplication.layout_creators.DeckListLayout;
import com.example.deckapplication.norration.FinalCard;
import com.example.deckapplication.norration.MorphCard;
import com.example.deckapplication.norration.Norration;
import com.example.deckapplication.norration.Probability;
import com.example.deckapplication.norration.adapters.CardAdapter;
import com.example.deckapplication.norration.adapters.CardCheckAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static android.content.Context.MODE_PRIVATE;


public class NorrationGameFrag extends Fragment implements  View.OnClickListener,
                                                            AdapterView.OnItemSelectedListener,
                                                            CardAdapter.OnCardEventListener,
                                                            DeckListLayout.OnDeckListEventListener {

    Norration norration;
    private Context context;
    final int RESULT_DECK_ONLY = 2;
    final int DECK_ACTIVITY = 1;

//    Stack<CardLayoutCreator> card_layout_immersion;

    ViewGroup       root_view;

    Button          button_save;
    Button          button_add_card;
    EditText        text_norration;
    EditText        text_search_card;
    Spinner         spinner_round;
    Spinner         spinner_round_count;
    Spinner         spinner_turn;
    Spinner         spinner_turn_count;
    RecyclerView    recycler_view_cards;

    ArrayAdapter<String>    round_adapter;
    ArrayAdapter<String>    round_count_adapter;
    ArrayAdapter<String>    turn_adapter;
    ArrayAdapter<String>    turn_count_adapter;
    CardAdapter             card_adapter;
    CardCheckAdapter        card_check_adapter = null;

    DeckListLayout deck_list_layout = null;

    public NorrationGameFrag() {
        // Required empty public constructor
    }

    public static NorrationGameFrag newInstance(String param1, String param2) {
        NorrationGameFrag fragment = new NorrationGameFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup frag_view = ((ViewGroup) inflater.inflate(R.layout.frag_norration_game, container, false));

        root_view = frag_view;

        text_norration      = root_view.findViewById(R.id.game_text_norration);
        text_search_card    = root_view.findViewById(R.id.game_text_search_card);
        button_save         = root_view.findViewById(R.id.game_button_save);
        button_add_card     = root_view.findViewById(R.id.game_button_add_card);
        spinner_round       = root_view.findViewById(R.id.game_spinner_round);
        spinner_round_count = root_view.findViewById(R.id.game_spinner_round_count);
        spinner_turn        = root_view.findViewById(R.id.game_spinner_turn);
        spinner_turn_count  = root_view.findViewById(R.id.game_spinner_turn_count);

        text_norration.setText(((NorrationActivity) context).norration.name);

        button_save.setOnClickListener(this);
        button_add_card.setOnClickListener(this);

        round_count_adapter = initSimpleSpinner(spinner_round_count, 0, 100);
        round_adapter = initSimpleSpinner(spinner_round, 0, Integer.parseInt(spinner_round_count.getSelectedItem().toString()));
        spinner_round_count.setSelection(norration.rounds.size());

        turn_count_adapter = initSimpleSpinner(spinner_turn_count, 0, 100);
        turn_adapter = initSimpleSpinner(spinner_turn, 0, Integer.parseInt(spinner_turn_count.getSelectedItem().toString()));

        spinner_round_count.setOnItemSelectedListener(this);
        spinner_round.setOnItemSelectedListener(this);
        spinner_turn_count.setOnItemSelectedListener(this);
        spinner_turn.setOnItemSelectedListener(this);

        recycler_view_cards = root_view.findViewById(R.id.game_recycler_view_cards);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(context);
        recycler_view_cards.setLayoutManager(layout_manager);
        recycler_view_cards.setHasFixedSize(true);
        card_adapter = new CardAdapter(new TreeMap<String, Probability>(), this);
        recycler_view_cards.setAdapter(card_adapter);

        return  frag_view;
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
        if (v.getId() == button_save.getId()){
            if (!text_norration.getText().toString().isEmpty()){
                try {
                    context.deleteFile(((NorrationActivity) context).getIntent().getStringExtra("nor_file"));

                    norration.name = text_norration.getText().toString();
                    FileOutputStream file_output_stream = context.openFileOutput(norration.name + ".nor", MODE_PRIVATE);
                    ObjectOutputStream object_output_stream = new ObjectOutputStream(file_output_stream);
                    object_output_stream.writeObject(norration);
                    object_output_stream.close();
                    file_output_stream.close();
                }catch(IOException e){

                }catch(Exception e){

                }
            }else{

            }
        }
        if (v.getId() == button_add_card.getId()){
//            Intent intent = new Intent(context, DeckViewActivity.class);
////            intent.putExtra("nor_file", getIntent().getStringExtra("nor_file"));
//            intent.putExtra("norration", norration);
//            startActivityForResult(intent, DECK_ACTIVITY);
            deck_list_layout = new DeckListLayout(root_view, this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view == null) return;
        if (((View)view.getParent()).getId() == spinner_round_count.getId()) {
            int selected_round_count = Integer.parseInt(spinner_round_count.getSelectedItem().toString());
            norration.fitRoundsToSize(selected_round_count);
            round_adapter = refreshSpinnerBorders(round_adapter, spinner_round, 0, selected_round_count);
        }
        if (((View)view.getParent()).getId() == spinner_round.getId()) {                                            // Если изменился текущий раунд
            int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
            if (selected_round == 0){                                                                               // Если он 0 (пустота)
                spinner_turn_count.setEnabled(false);                                                               // Делаем операции с ходами не активными и устанавливаем в 0
                spinner_turn.setEnabled(false);
                spinner_turn_count.setSelection(0);
                //spinner_turn.setSelection(0);
            }else{                                                                                                  // Если он не 0
                spinner_turn_count.setEnabled(true);                                                                // Включаем операции с ходами и загружаем кол-во ходов в этом раунде
                spinner_turn.setEnabled(true);
                spinner_turn_count.setSelection(norration.rounds.get(selected_round - 1).turns.size());
            }
        }
        if (((View)view.getParent()).getId() == spinner_turn_count.getId()) {                                       // Если изменилось кол-во ходов
            int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
            int selected_turn_count = Integer.parseInt(spinner_turn_count.getSelectedItem().toString());
            if (selected_round > 0){                                                                                // Выбран какой-то раунд
                norration.rounds.get(selected_round - 1).fitTurnsToSize(selected_turn_count);                       // Меняем кол-во ходов в объекте
            }
            turn_adapter = refreshSpinnerBorders(turn_adapter, spinner_turn, 0, Integer.parseInt(spinner_turn_count.getSelectedItem().toString()));
        }
        if (((View)view.getParent()).getId() == spinner_turn.getId()) {                                             // Если изменился текущий ход
//            int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
            int selected_turn = Integer.parseInt(spinner_turn.getSelectedItem().toString());
            if (selected_turn != 0){
                int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
                card_adapter.refreshData(norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool);

                text_search_card.setEnabled(true);
                button_add_card.setEnabled(true);
                recycler_view_cards.setEnabled(true);
            }else{
                card_adapter.refreshData(new TreeMap<String, Probability>());

                text_search_card.setEnabled(false);
                button_add_card.setEnabled(false);
                recycler_view_cards.setEnabled(false);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProbabilityChangedListener(String key, int value) {
        int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
        int selected_turn = Integer.parseInt(spinner_turn.getSelectedItem().toString());
        norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool.get(key).probability = value;
    }

    @Override
    public void onSwitchFixCheckedChanged(String key, boolean is_checked) {
        int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
        int selected_turn = Integer.parseInt(spinner_turn.getSelectedItem().toString());
        norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool.get(key).fixed = is_checked;
    }

    @Override
    public void onViewButtonClick(String key) {
//        int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
//        int selected_turn = Integer.parseInt(spinner_turn.getSelectedItem().toString());
//        CardLayoutCreator layout_creator;
//        View parent = card_layout_immersion.empty() ? root_view.findViewById(R.id.game_root) : card_layout_immersion.peek().card_layout;
//        if (norration.deck.get(key).isMorph()){
//            layout_creator = new MorphCardLayoutCreator(((ViewGroup) parent), ((MorphCard) norration.deck.get(key)));
//        }else/*        if (norration.deck.get(key).isFinal())*/{
//            layout_creator = new FinalCardLayoutCreator(((ViewGroup) parent), ((FinalCard) norration.deck.get(key)));
//        }
//        layout_creator.create();
//        card_layout_immersion.push(layout_creator);
    }

    @Override
    public void onRemoveButtonClick(String key) {
        int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
        int selected_turn = Integer.parseInt(spinner_turn.getSelectedItem().toString());
        norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool.remove(key);
        card_adapter.refreshData(norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onBackPressed() {
//        if (card_layout_immersion.empty()) {
//            return false;
//        }else{
//            card_layout_immersion.pop().destroy();
//            return true;
//        }
        if (deck_list_layout != null){
//            deck_list_layout.destroy();
            deck_list_layout.onDestroy();
            deck_list_layout = null;
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

    private ArrayAdapter<String> initSimpleSpinner(Spinner spinner, int from, int to){
        ArrayList<String> simple_spinner_list = initSimpleSpinnerList(from, to);
        String[] tmp_mass = simple_spinner_list.toArray(new String[simple_spinner_list.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, tmp_mass);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return adapter;
    }

    private ArrayAdapter<String> refreshSpinnerBorders(ArrayAdapter<String> adapter, Spinner spinner, int from, int to){
        int prev = Integer.parseInt(spinner.getSelectedItem().toString());
        ArrayList<String> simple_spinner_list = initSimpleSpinnerList(from, to);
        String[] tmp_mass = simple_spinner_list.toArray(new String[simple_spinner_list.size()]);
        ArrayAdapter<String> new_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, tmp_mass);
        new_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(new_adapter);
        if (to >= from) {
            spinner.setSelection(bound(prev, from, to) - from);
        }
        return new_adapter;
    }

    ArrayList<String> initSimpleSpinnerList(int from, int to){
        ArrayList<String> array_list = new ArrayList<String>();
        for (int i = from; i <= to; i++){
            array_list.add(Integer.toString(i));
        }
        return array_list;
    }


    int bound(int target, int from, int to){
        if (target < from)  return from;
        if (target > to)    return to;
        return target;
    }

    private void fixTurns(){
        for (int i = 0; i < norration.rounds.size(); i++){
            for (int j = 0; j < norration.rounds.get(i).turns.size(); j++){
                Map<String, Probability> new_pool = new TreeMap<>();
                for (TreeMap.Entry<String, Probability> entry : norration.rounds.get(i).turns.get(j).pool.entrySet()){
                    if (norration.deck.containsKey(entry.getKey())){
                        new_pool.put(entry.getKey(), entry.getValue());
                    }
                }
                norration.rounds.get(i).turns.get(j).pool = new_pool;
            }
        }
    }

    @Override
    public boolean onDeckListInUseInit(String key) {
        int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
        int selected_turn = Integer.parseInt(spinner_turn.getSelectedItem().toString());
        return norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool.containsKey(key);
    }

    @Override
    public void onDeckListInUsChanged(String key, boolean is_checked) {
        int selected_round = Integer.parseInt(spinner_round.getSelectedItem().toString());
        int selected_turn = Integer.parseInt(spinner_turn.getSelectedItem().toString());
        if (is_checked && !norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool.containsKey(key)){
            norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool.put(key, new Probability(0, false));
        }
        card_adapter.refreshData(norration.rounds.get(selected_round - 1).turns.get(selected_turn - 1).pool);
    }

    @Override
    public void onDeckListCreate(CardCheckAdapter adapter) {
        card_check_adapter = adapter;
    }

    public interface OnInteractionListener {
        // TODO: Update argument type and name
        void onAttach(NorrationGameFrag frag);
    }
}
