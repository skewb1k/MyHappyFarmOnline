package com.example.myhappyfarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myhappyfarm.R;
import com.example.myhappyfarm.utils.Room;
import com.example.myhappyfarm.utils.RoomAdapter;
import com.example.myhappyfarm.utils.Utils;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoomsActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance(Utils.DB_REF_URL).getReference();
    private RoomAdapter roomAdapter;
    ArrayList<Room> rooms;
    private ListView rooms_list;
    private ArrayList<String> players;
    private String nickname;
    private final ValueEventListener rooms_listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            rooms.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                players = new ArrayList<>();
                for (DataSnapshot player : dataSnapshot.child("players").getChildren()) {
                    players.add(player.getKey());
                }
                rooms.add(new Room(dataSnapshot.getKey(), players));
            }
            roomAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms);
        rooms = new ArrayList<>();
        databaseReference.child("rooms").addValueEventListener(rooms_listener);
        roomAdapter = new RoomAdapter(this, R.layout.roomslist, rooms);
        rooms_list = findViewById(R.id.rooms_list);
        rooms_list.setAdapter(roomAdapter);
        nickname = getIntent().getExtras().getString("nickname");
    }

    public void createRoomClicked(View view) {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
    }

    public void joinClicked(String room_id) {
        Intent intent = new Intent(this, WaitingActivity.class);
        databaseReference.child("rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.child(room_id).getChildrenCount() < 4 & !snapshot.child(room_id).hasChild(nickname)) {
                    databaseReference.child("rooms").child(room_id).child("players").child(nickname).setValue(false);
                    intent.putExtra("nickname", nickname);
                    intent.putExtra("room_id", room_id);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }
}