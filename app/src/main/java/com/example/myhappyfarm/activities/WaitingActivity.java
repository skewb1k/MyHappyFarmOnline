package com.example.myhappyfarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myhappyfarm.R;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

public class WaitingActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://myhappyfarmonline-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    private String nickname;
    private String room_id;
    private final ChildEventListener removeRoomListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getKey().equals(room_id)) {
                leaveRoomClicked(null);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);
        Intent intent = new Intent(this, GameActivity.class);
        nickname = getIntent().getExtras().getString("nickname");
        room_id = getIntent().getExtras().getString("room_id");
        databaseReference.child("games").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.getKey().equals(room_id)) {
                    databaseReference.child("games").child(room_id).child("players").child(nickname).setValue(false);
                    final long[] count = new long[1];
                    databaseReference.child("rooms").child(room_id).child("players").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            count[0] = snapshot.getChildrenCount();
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });
                    databaseReference.child("games").child(room_id).child("players").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount() == count[0]) {
                                intent.putExtra("nickname", nickname);
                                intent.putExtra("room_id", room_id);
                                intent.putExtra("host", false);
                                startActivity(intent);
                                databaseReference.child("games").removeEventListener(this);
                                databaseReference.child("rooms").removeEventListener(removeRoomListener);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        databaseReference.child("rooms").addChildEventListener(removeRoomListener);
    }

    public void leaveRoomClicked(View view) {
        databaseReference.child("rooms").child(room_id).child("players").child(nickname).removeValue();
        Intent intent = new Intent(this, RoomsActivity.class);
        intent.putExtra("nickname", nickname);
        intent.putExtra("room_id", room_id);
        startActivity(intent);
    }
}