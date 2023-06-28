package com.example.myhappyfarm.managers;

import android.view.View;
import android.widget.ImageView;
import com.example.myhappyfarm.game.FoodCard;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodMarketManager {
    private final ArrayList<ImageView> cards;
    private final HashMap<ImageView, Integer> cardsMap;

    public FoodMarketManager(ArrayList<ImageView> cards) {
        this.cards = cards;
        this.cardsMap = new HashMap<>();
        for (int i = 0; i < cards.size(); i++) {
            this.cardsMap.put(cards.get(i), i);
        }
    }

    public int cardClicked(ImageView clickedButton) {
        int i = cardsMap.get(clickedButton);
        return i;
    }


    public void update(ArrayList<FoodCard> newCards) {
        int i = 0;
        for (ImageView card : cards) {
            try {
                card.setImageResource(newCards.get(i).getRes());
                card.setVisibility(View.VISIBLE);
            } catch (IndexOutOfBoundsException e) {
                card.setVisibility(View.INVISIBLE);
            }
            i++;
        }
    }
}
