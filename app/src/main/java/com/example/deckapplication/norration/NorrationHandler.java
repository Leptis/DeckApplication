package com.example.deckapplication.norration;

public class NorrationHandler {
    Norration norration;
    Integer round;
    Integer turn;

    Card getNextCard(){
        String  card_name_to_play   = norration.rounds.get(round).turns.get(turn).getFromPool();
        Card    card_to_play        = norration.deck.get(card_name_to_play);
        turn++;
        if (turn == norration.rounds.get(round).turns.size()){
            turn = 0;
            round++;
        }
        if (round == norration.rounds.size()){
            return null;
        }

        return card_to_play;
    }
}
