package com.example.myhappyfarm.game;


import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String nickname;
    private Integer coins;
    private ArrayList<FoodCard> hand;
    private Barn barn;
    private HashMap<String, ArrayList<Integer>> animals;


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

    public HashMap<String, ArrayList<Integer>> getAnimals() {
        return animals;
    }

    public ArrayList<Integer> getAnimal(String animal) {
        return animals.get(animal);
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


    public void plant(ArrayList<Integer> cards) {
        ArrayList<FoodCard> c = new ArrayList<>();
        for (int i = cards.size() - 1; i >= 0; i--) {
            c.add(hand.get(cards.get(i)));
            hand.remove((int) cards.get(i));
        }
        barn.plant(c);
    }
}
