package com.example.myhappyfarm.managers;

import android.graphics.Color;
import android.widget.TextView;

public class NicknameManager {
    private TextView nickname;

    public NicknameManager(TextView nickname, String nick) {
        this.nickname = nickname;
        nickname.setText(nick);
    }

    public void update(Boolean value) {
        if (value) {
            nickname.setTextColor(Color.parseColor("#FFF200"));
        } else {
            nickname.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }
}
