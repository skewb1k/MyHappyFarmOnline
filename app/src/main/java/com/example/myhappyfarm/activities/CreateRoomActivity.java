package com.example.myhappyfarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myhappyfarm.R;
import com.example.myhappyfarm.utils.Utils;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CreateRoomActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance(Utils.DB_REF_URL).getReference();
    private String nickname;
    private String room_id;
    long count;
    private final ValueEventListener roomIdListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            count = snapshot.getChildrenCount();
            setNumOfPlayers(count);
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createroom);
        nickname = getIntent().getExtras().getString("nickname");
        room_id = databaseReference.child("rooms").push().getKey();
        databaseReference.child("rooms").child(room_id).child("seed").setValue(new Random().nextInt(899) + 100);
        databaseReference.child("rooms").child(room_id).child("players").child(nickname).setValue(true);
        databaseReference.child("rooms").child(room_id).child("players").addValueEventListener(roomIdListener);
    }

    @Override
    public void onBackPressed() {
        databaseReference.child("rooms").child(room_id).removeValue();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        databaseReference.child("rooms").child(room_id).removeValue();
        super.onDestroy();
    }

    public void startGameClicked(View view) {
        databaseReference.child("rooms").child(room_id).child("players").removeEventListener(roomIdListener);
        databaseReference.child("games").child(room_id).child("players").child(nickname).setValue(true);
        Intent intent = new Intent(this, GameActivity.class);
        databaseReference.child("games").child(room_id).child("players").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() == count) {
                    intent.putExtra("nickname", nickname);
                    intent.putExtra("room_id", room_id);
                    intent.putExtra("host", true);
                    databaseReference.child("games").child(room_id).child("players").removeEventListener(this);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private void setNumOfPlayers(long count) {
        TextView connected_players = (TextView) findViewById(R.id.connectedplayers);
        connected_players.setText(getResources().getString(R.string.players_title) + " " + count + "/3");
    }
}