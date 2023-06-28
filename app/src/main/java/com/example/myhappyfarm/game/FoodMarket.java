package com.example.myhappyfarm.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class FoodMarket {
    private ArrayList<FoodCard> deck;

    public FoodMarket(int seed) {
        this.deck = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            this.deck.add(new FoodCard(2));
        }
        for (int i = 0; i < 21; i++) {
            this.deck.add(new FoodCard(4));
        }
        for (int i = 0; i < 9; i++) {
            this.deck.add(new FoodCard(5));
        }
        for (int i = 0; i < 11; i++) {
            this.deck.add(new FoodCard(3));
        }
        Collections.shuffle(deck, new Random(seed));
    }


    public void sell(FoodCard card) {
        deck.add(new Random().nextInt(deck.size() - 7) + 7, card);
    }

    public FoodCard buy(int i) {
        FoodCard r = deck.get(i);
        if (deck.size() > 6) {
            if (i != 6) {
                deck.set(i, deck.get(6));
            }
            deck.remove(6);
        } else {
            deck.remove(i);
        }
        ArrayList<Integer> faceupint = new ArrayList<>();
        for (FoodCard f : getFaceup()) {
            faceupint.add(f.getValue());
        }
        if (faceupint.stream().allMatch(faceupint.get(0)::equals)) {
            deck.subList(0, 6).clear();
        }
        return r;
    }

    public ArrayList<FoodCard> getFaceup() {
        return (ArrayList<FoodCard>) deck.stream().limit(6).collect(Collectors.toList());
    }
}
