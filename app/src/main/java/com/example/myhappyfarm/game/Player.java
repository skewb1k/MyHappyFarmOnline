package com.example.myhappyfarm.game;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private String nickname;
    private Integer coins;
    private ArrayList<FoodCard> hand;
    private Barn barn;
    private Map<String, ArrayList<Integer>> animals;


    public Player(String nickname) {
        this.nickname = nickname;
        this.coins = 2;
        this.hand = new ArrayList<>();
        this.barn = new Barn();
        this.animals = new HashMap<>();
        this.animals.put("pig", new ArrayList<>());
        this.animals.put("rabbit", new ArrayList<>());
        this.animals.put("cow", new ArrayList<>());
        this.animals.put("sheep", new ArrayList<>());
    }

    public void addFoodToHand(FoodCard food) {
        hand.add(food);
    }

    public void addAnimal(AnimalCard animalCard) {
        animals.get(animalCard.getAnimal()).add(animalCard.getScore());
    }

    public ArrayList<FoodCard> getHand() {
        return hand;
    }

    public String getNickname() {
        return nickname;
    }

    public Map<String, ArrayList<Integer>> getAnimals() {
        return animals;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer delta) {
        coins += delta;
    }

    public Barn getBarn() {
        return barn;
    }

    public ArrayList<Integer> getScore(String animal) {
        if (animals.get(animal).size() == 0) {
            return new ArrayList<>(Arrays.asList(-5, 0));
        } else {
            return new ArrayList<>(Arrays.asList(animals.get(animal).stream().mapToInt(Integer::intValue).sum(), animals.get(animal).size()));
        }
    }

    public void plant(ArrayList<Integer> cards) {
        ArrayList<FoodCard> c = new ArrayList<>();
        for (int i = cards.size() - 1; i >= 0; i--) {
            c.add(hand.get(cards.get(i)));
            hand.remove((int) cards.get(i));
        }
        barn.plant(c);
    }
}
