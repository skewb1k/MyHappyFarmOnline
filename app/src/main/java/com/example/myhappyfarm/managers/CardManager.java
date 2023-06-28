package com.example.myhappyfarm.managers;

import android.view.View;
import android.widget.ImageView;
import com.example.myhappyfarm.game.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CardManager {
    private ArrayList<ImageView> cards;
    private HashMap<ImageView, Integer> cardsMap;
    private ArrayList<ImageView> selectors;
    private ArrayList<Integer> selection;
    private final ArrayList<Integer> EMPTY_LIST = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
    private int maxSelectionSize;

    public CardManager(ArrayList<ImageView> cards, ArrayList<ImageView> selectors, int maxSelectionSize) {
        this.cards = cards;
        this.maxSelectionSize = maxSelectionSize;
        this.cardsMap = new HashMap<>();
        for (int i = 0; i < cards.size(); i++) {
            this.cardsMap.put(cards.get(i), i);
        }
        this.selectors = selectors;
        clearSelection();
    }

    public void cardClicked(ImageView clickedButton) {
        int i = cardsMap.get(clickedButton);
        if (selection.get(i) == 1) {
            selection.set(i, 0);
            selectors.get(i).setVisibility(View.INVISIBLE);
        } else if (getSelection().size() < maxSelectionSize) {
            selection.set(i, 1);
            selectors.get(i).setVisibility(View.VISIBLE);
        }
    }

    public ArrayList<Integer> getSelection() {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (this.selection.get(i) == 1) {
                res.add(i);
            }
        }
        return res;
    }

    public void update(ArrayList<Card> newCards) {
        clearSelection();
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

    public void clearSelection() {
        for (ImageView selector : selectors) {
            selector.setVisibility(View.INVISIBLE);
        }
        this.selection = new ArrayList<>(EMPTY_LIST);
    }
}
