package com.example.myhappyfarm.managers;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.myhappyfarm.drawers.ButtonDrawer;
import com.example.myhappyfarm.game.FoodCard;

import java.util.ArrayList;

public class BarnPlantsManager {
    private final ArrayList<ImageView> cards;
    private final ButtonDrawer buttonDrawer;


    public BarnPlantsManager(ArrayList<ImageView> cards, Button plantButton) {
        this.cards = cards;
        if (plantButton != null) {
            this.buttonDrawer = new ButtonDrawer(plantButton);
        } else {
            this.buttonDrawer = null;
        }
    }

    public void update(ArrayList<ArrayList<FoodCard>> newPlants) {
        for (ImageView btn : cards) {
            btn.setVisibility(View.INVISIBLE);
        }
        if (buttonDrawer != null) {
            if (newPlants.get(0).size() == 0) {
                buttonDrawer.showButton();
            } else {
                buttonDrawer.hideButton();
            }
        }
        for (int i = 0; i < 3; i++) {
            switch (newPlants.get(i).size()) {
                case 1 -> {
                    setFood(cards.get(2 + (i * 5)), newPlants.get(i).get(0));
                }
                case 2 -> {
                    setFood(cards.get(1 + (i * 5)), newPlants.get(i).get(0));
                    setFood(cards.get(3 + (i * 5)), newPlants.get(i).get(1));
                }
                case 3 -> {
                    setFood(cards.get(i * 5), newPlants.get(i).get(0));
                    setFood(cards.get(2 + (i * 5)), newPlants.get(i).get(1));
                    setFood(cards.get(4 + (i * 5)), newPlants.get(i).get(2));
                }
            }
        }
    }

    private void setFood(ImageView imgbtn, FoodCard foodCard) {
        imgbtn.setVisibility(View.VISIBLE);
        imgbtn.setImageResource(foodCard.getRes());
    }

    public ButtonDrawer getButtonDrawer() {
        return buttonDrawer;
    }
}

