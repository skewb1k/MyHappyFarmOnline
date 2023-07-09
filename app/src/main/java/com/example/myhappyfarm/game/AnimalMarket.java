package com.example.myhappyfarm.game;


import com.example.myhappyfarm.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AnimalMarket {
    private ArrayList<AnimalCard> deck;

    public AnimalMarket(int seed) {
        this.deck = new ArrayList<>();
        this.deck.add(new AnimalCard("pig", new ArrayList<>(List.of(0)), 2, R.drawable.pig_2));
        this.deck.add(new AnimalCard("pig", new ArrayList<>(List.of(0, 2)), 3, R.drawable.pig_31));
        this.deck.add(new AnimalCard("pig", new ArrayList<>(List.of(0, 2)), 3, R.drawable.pig_32));
        this.deck.add(new AnimalCard("pig", new ArrayList<>(List.of(0, 3, 4)), 4, R.drawable.pig_4));
        this.deck.add(new AnimalCard("pig", new ArrayList<>(List.of(0, 3, 4)), 4, R.drawable.pig_4));
        this.deck.add(new AnimalCard("pig", new ArrayList<>(List.of(0, 3, 4, 5)), 5, R.drawable.pig_5));
        this.deck.add(new AnimalCard("rabbit", new ArrayList<>(List.of(2)), 2, R.drawable.rabbit_2));
        this.deck.add(new AnimalCard("rabbit", new ArrayList<>(List.of(2, 3)), 3, R.drawable.rabbit_3));
        this.deck.add(new AnimalCard("rabbit", new ArrayList<>(List.of(2, 3)), 3, R.drawable.rabbit_3));
        this.deck.add(new AnimalCard("rabbit", new ArrayList<>(List.of(2, 3, 4)), 4, R.drawable.rabbit_4));
        this.deck.add(new AnimalCard("rabbit", new ArrayList<>(List.of(2, 3, 4)), 4, R.drawable.rabbit_4));
        this.deck.add(new AnimalCard("rabbit", new ArrayList<>(List.of(2, 3, 3, 4)), 5, R.drawable.rabbit_5));
        this.deck.add(new AnimalCard("cow", new ArrayList<>(List.of(2, 4, 4)), 5, R.drawable.cow_5));
        this.deck.add(new AnimalCard("cow", new ArrayList<>(List.of(2, 2, 4, 4)), 6, R.drawable.cow_6));
        this.deck.add(new AnimalCard("cow", new ArrayList<>(List.of(2, 2, 4, 4)), 6, R.drawable.cow_6));
        this.deck.add(new AnimalCard("cow", new ArrayList<>(List.of(2, 2, 4, 4, 5)), 7, R.drawable.cow_7));
        this.deck.add(new AnimalCard("cow", new ArrayList<>(List.of(2, 2, 4, 4, 5)), 7, R.drawable.cow_7));
        this.deck.add(new AnimalCard("cow", new ArrayList<>(List.of(2, 2, 4, 4, 5, 5)), 8, R.drawable.cow_8));
        this.deck.add(new AnimalCard("sheep", new ArrayList<>(List.of(3, 4, 4)), 6, R.drawable.sheep_6));
        this.deck.add(new AnimalCard("sheep", new ArrayList<>(List.of(2, 3, 4, 4)), 7, R.drawable.sheep_7));
        this.deck.add(new AnimalCard("sheep", new ArrayList<>(List.of(2, 3, 4, 4)), 7, R.drawable.sheep_7));
        this.deck.add(new AnimalCard("sheep", new ArrayList<>(List.of(2, 4, 4, 5, 5)), 8, R.drawable.sheep_8));
        this.deck.add(new AnimalCard("sheep", new ArrayList<>(List.of(2, 4, 4, 5, 5)), 8, R.drawable.sheep_8));
        this.deck.add(new AnimalCard("sheep", new ArrayList<>(List.of(2, 2, 3, 4, 5, 5)), 9, R.drawable.sheep_9));
        Collections.shuffle(deck, new Random(seed));
    }

    public AnimalCard buy(int i) {
        AnimalCard r = deck.get(i);
        if (deck.size() > 6) {
            deck.set(i, deck.get(6));
            deck.remove(6);
        } else {
            deck.remove(i);
        }
        return r;
    }

    public ArrayList<AnimalCard> getFaceup() {
        return (ArrayList<AnimalCard>) deck.stream().limit(6).collect(Collectors.toList());
    }
}
