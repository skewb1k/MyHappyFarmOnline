package com.example.myhappyfarm.managers;

import android.widget.TextView;

public class CoinManager {
    private TextView coin;

    public CoinManager(TextView coin) {
        this.coin = coin;
    }

    public void update(Integer value) {
        coin.setText("x" + value.toString());
    }
}
