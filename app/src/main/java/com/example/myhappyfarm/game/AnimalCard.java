package com.example.myhappyfarm.game;

import java.util.ArrayList;

public class AnimalCard extends Card {
    private String animal;
    private ArrayList<Integer> cost;
    private Integer score;

    public AnimalCard(String animal, ArrayList<Integer> cost, Integer score, int res) {
        super(res);
        this.animal = animal;
        this.cost = cost;
        this.score = score;
    }

    public String getAnimal() {
        return animal;
    }

    public ArrayList<Integer> getCost() {
        return cost;
    }

    public Integer getScore() {
        return score;
    }
}
