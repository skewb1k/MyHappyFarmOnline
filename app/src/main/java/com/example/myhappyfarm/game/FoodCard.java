package com.example.myhappyfarm.game;

import com.example.myhappyfarm.R;

public class FoodCard extends Card {
    private final Integer value;

    public FoodCard(Integer value) {
        super(defineRes(value));
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    private static int defineRes(Integer value) {
        switch (value) {
            case 2 -> {
                return R.drawable.melon;
            }
            case 3 -> {
                return R.drawable.carrot;
            }
            case 4 -> {
                return R.drawable.wheat;
            }
            case 5 -> {
                return R.drawable.beetroot;
            }
        }
        return 0;
    }
}
