package com.example.myhappyfarm.managers;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class AnimalsManager {
    private ArrayList<TextView> animals;

    public AnimalsManager(ArrayList<TextView> animals) {
        this.animals = animals;
    }

    public void update(Map<String, ArrayList<Integer>> values) {
        animals.get(0).setText("x" + values.get("rabbit").size());
        animals.get(1).setText("x" + values.get("sheep").size());
        animals.get(2).setText("x" + values.get("pig").size());
        animals.get(3).setText("x" + values.get("cow").size());
    }
}
