package com.example.myhappyfarm.game;


import java.util.ArrayList;

public class Barn {
    private ArrayList<ArrayList<FoodCard>> plants;
    private ArrayList<FoodCard> inventory;


    public Barn() {
        this.plants = new ArrayList<>(3);
        this.plants.add(new ArrayList<>());
        this.plants.add(new ArrayList<>());
        this.plants.add(new ArrayList<>());
        this.inventory = new ArrayList<>();
    }

    public void plant(ArrayList<FoodCard> cards) {
        plants.set(0, cards);
    }

    public void grow() {
        plants.set(2, plants.get(1));
        plants.set(1, new ArrayList<>(plants.get(0)));
        plants.get(0).clear();
    }

    public void harvest(int i) {
        inventory.addAll(new ArrayList<>(plants.get(i)));
        plants.get(i).clear();
    }

    public void buy(ArrayList<FoodCard> cards) {
        for (FoodCard i : cards) {
            inventory.remove(i);
        }
    }

    public ArrayList<ArrayList<FoodCard>> getPlants() {
        return plants;
    }

    public ArrayList<FoodCard> getInventory() {
        return inventory;
    }
}
