package com.example.myhappyfarm.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myhappyfarm.R;

public class StartActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText nickname_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        nickname_edit = findViewById(R.id.enter_nickname);
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname", null);
        if (nickname != null) {
            nickname_edit.setText(nickname);
        }
    }

    public void enterNicknameClicked(View view) {
        String nickname = nickname_edit.getText().toString();
        if (!nickname.equals("")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nickname", nickname);
            editor.apply();
            Intent intent = new Intent(this, RoomsActivity.class);
            intent.putExtra("nickname", nickname);
            startActivity(intent);
        }
    }
}