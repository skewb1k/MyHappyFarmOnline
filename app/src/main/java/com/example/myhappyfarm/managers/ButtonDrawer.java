package com.example.myhappyfarm.managers;

import android.view.View;
import android.widget.Button;

public class ButtonDrawer {
    private final Button button;
    private boolean status;

    public ButtonDrawer(Button button) {
        this.button = button;
        this.status = true;
    }

    public void hideButton() {
        button.setVisibility(View.INVISIBLE);
    }

    public void showButton() {
        if (status) {
            button.setVisibility(View.VISIBLE);
        }
    }

    public void disableButton() {
        this.status = false;
        hideButton();
    }

    public void enableButton() {
        this.status = true;
    }
}
