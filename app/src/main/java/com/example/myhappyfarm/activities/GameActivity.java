package com.example.myhappyfarm.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myhappyfarm.R;
import com.example.myhappyfarm.drawers.ButtonDrawer;
import com.example.myhappyfarm.drawers.PlayerDrawer;
import com.example.myhappyfarm.game.Card;
import com.example.myhappyfarm.game.Game;
import com.example.myhappyfarm.managers.CardManager;
import com.example.myhappyfarm.managers.FoodMarketManager;
import com.example.myhappyfarm.utils.EndDialogFragment;
import com.example.myhappyfarm.utils.IEndGame;
import com.example.myhappyfarm.utils.Utils;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GameActivity extends AppCompatActivity implements IEndGame {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance(Utils.DB_REF_URL).getReference();
    private Game game;
    private ArrayList<String> players;
    private ArrayList<PlayerDrawer> playerDrawers;
    private String nickname;
    private String room_id;
    private Boolean isHost;
    private ButtonDrawer okButtonDrawer;
    private CardManager handManager;
    private CardManager animalMarketManager;
    private FoodMarketManager foodMarketManager;
    private int player;
    private int turn;
    private final ChildEventListener turnsListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            String value = snapshot.getValue(String.class);
            int cplayer = value.charAt(0) - '0';
            if (!isMyTurn() & cplayer != player) {
                turn++;
                switch (value.charAt(1)) {
                    case '1' -> {
                        game.plant(Utils.stringToList(value.substring(2)));
                        playerDrawers.get(cplayer).updateBarnPlants(game.getBarnPlants(cplayer));
                    }
                    case '2' -> {
                        int card = value.charAt(2) - '0';
                        game.buyFood(card);
                        if (card != 6) {
                            updateFoodMarket();
                        }
                        playerDrawers.get(cplayer).updateCoins(game.getCoins(cplayer));
                    }
                    case '3' -> {
                        game.harvest(value.charAt(2) - '0');
                        playerDrawers.get(cplayer).updateBarnPlants(game.getBarnPlants(cplayer));
                        playerDrawers.get(cplayer).updateBarnInventory((ArrayList<Card>) ((ArrayList<?>) game.getBarnInventory(cplayer)));
                    }
                    case '4' -> {
                        game.sell(Utils.stringToList(value.substring(2)));
                        playerDrawers.get(cplayer).updateCoins(game.getCoins(cplayer));
                        playerDrawers.get(cplayer).updateBarnInventory((ArrayList<Card>) ((ArrayList<?>) game.getBarnInventory(cplayer)));
                    }
                    case '5' -> {
                        String[] splitted = value.split("-");
                        game.buyAnimal(Utils.stringToList(splitted[0].substring(2)), Utils.stringToList(splitted[1]));
                        endGame();
                        playerDrawers.get(cplayer).updateBarnInventory((ArrayList<Card>) ((ArrayList<?>) game.getBarnInventory(cplayer)));
                        playerDrawers.get(cplayer).updateAnimals(game.getAnimals(cplayer));
                        updateAnimalMarket();
                    }
                    case '6' -> {
                        game.coin();
                        playerDrawers.get(cplayer).updateCoins(game.getCoins(cplayer));
                    }
                    case '7' -> {
                        game.nextPhase();
                    }
                }
                updateTurn();
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
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        okButtonDrawer = new ButtonDrawer(findViewById(R.id.ok));
        nickname = getIntent().getExtras().getString("nickname");
        room_id = getIntent().getExtras().getString("room_id");
        isHost = getIntent().getExtras().getBoolean("host");
        players = new ArrayList<>();
        playerDrawers = new ArrayList<>();
        handManager = new CardManager(new ArrayList<>(Arrays.asList(findViewById(R.id.hand0), findViewById(R.id.hand1), findViewById(R.id.hand2), findViewById(R.id.hand3), findViewById(R.id.hand4), findViewById(R.id.hand5))), new ArrayList<>(Arrays.asList(findViewById(R.id.hand0_selection), findViewById(R.id.hand1_selection), findViewById(R.id.hand2_selection), findViewById(R.id.hand3_selection), findViewById(R.id.hand4_selection), findViewById(R.id.hand5_selection))), 3);
        foodMarketManager = new FoodMarketManager(new ArrayList<>(Arrays.asList(findViewById(R.id.foodmarket0), findViewById(R.id.foodmarket1), findViewById(R.id.foodmarket2), findViewById(R.id.foodmarket3), findViewById(R.id.foodmarket4), findViewById(R.id.foodmarket5))));
        animalMarketManager = new CardManager(new ArrayList<>(Arrays.asList(findViewById(R.id.animalmarket0), findViewById(R.id.animalmarket1), findViewById(R.id.animalmarket2), findViewById(R.id.animalmarket3), findViewById(R.id.animalmarket4), findViewById(R.id.animalmarket5))), new ArrayList<>(Arrays.asList(findViewById(R.id.animalmarket0_selection), findViewById(R.id.animalmarket1_selection), findViewById(R.id.animalmarket2_selection), findViewById(R.id.animalmarket3_selection), findViewById(R.id.animalmarket4_selection), findViewById(R.id.animalmarket5_selection))), 6);
        turn = 1;
        databaseReference.child("rooms").child(room_id).child("seed").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                final int seed = snapshot.getValue(Integer.class);
                if (isHost) {
                    databaseReference.child("rooms").child(room_id).removeValue();
                }
                databaseReference.child("games").child(room_id).child("players").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        int otherPlayers = 0;
                        int i = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            players.add(dataSnapshot.getKey());
                            if (Objects.equals(dataSnapshot.getKey(), nickname)) {
                                player = i;
                                playerDrawers.add(new PlayerDrawer(players.get(i), findViewById(R.id.player1), findViewById(R.id.plant)));
                            } else {
                                if (otherPlayers == 0) {
                                    findViewById(R.id.player2).setVisibility(View.VISIBLE);
                                    playerDrawers.add(new PlayerDrawer(players.get(i), findViewById(R.id.player2), null));
                                    otherPlayers++;
                                } else {
                                    findViewById(R.id.player3).setVisibility(View.VISIBLE);
                                    playerDrawers.add(new PlayerDrawer(players.get(i), findViewById(R.id.player3), null));
                                }
                            }
                            i++;
                        }
                        game = new Game(players, seed);
                        updateHand();
                        updateFoodMarket();
                        updateAnimalMarket();
                        updateTurn();
                        for (int j = 0; j < players.size(); j++) {
                            playerDrawers.get(j).updateCoins(game.getCoins(j));
                            playerDrawers.get(j).updateBarnPlants(game.getBarnPlants(j));
                            playerDrawers.get(j).updateBarnInventory((ArrayList<Card>) ((ArrayList<?>) game.getBarnInventory(j)));
                            playerDrawers.get(j).updateAnimals(game.getAnimals(j));
                        }
                        databaseReference.child("games").child(room_id).child("turns").addChildEventListener(turnsListener);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.child("games").child(room_id).removeValue();
    }

    public boolean endGame() {
        if (game.checkEnd()) {
            ArrayList<HashMap<String, ArrayList<Integer>>> playersScores = game.getPlayersScores();
            ArrayList<Integer> winners = new ArrayList<>(3);
            int sum;
            StringBuilder finalMessage = new StringBuilder();
            StringBuilder statsMessage;
            for (int k = 0; k < playersScores.size(); k++) {
                statsMessage = new StringBuilder();
                sum = 0;
                for (String animal : playersScores.get(k).keySet()) {
                    statsMessage.append("\n  ").append(animal).append(": (");
                    for (int i = 0; i < playersScores.get(k).get(animal).size(); i++) {
                        sum += playersScores.get(k).get(animal).get(i);
                        statsMessage.append(playersScores.get(k).get(animal).get(i));
                        if (i == 0) {
                            statsMessage.append(")");
                        }
                        if (i != playersScores.get(k).get(animal).size() - 1) {
                            statsMessage.append(" + ");
                        }
                    }
                }
                finalMessage.append(players.get(k)).append(" - total ").append(sum).append(": ");
                finalMessage.append(statsMessage);
                finalMessage.append("\n");
                winners.add(sum);
            }
            int winnerScore = Collections.max(winners);
            finalMessage.append(getResources().getString(R.string.player_title)).append(" ").append(players.get(winners.indexOf(winnerScore)));
            finalMessage.append(" ").append(getResources().getString(R.string.wonwithmaxscore)).append(" ").append(winnerScore).append("!");
            EndDialogFragment dialog = new EndDialogFragment();
            Bundle args = new Bundle();
            args.putString("message", finalMessage.toString());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "custom");
            return true;
        }
        return false;
    }

    @Override
    public void gameEnd() {
        databaseReference.child("games").child(room_id).child("turns").removeEventListener(turnsListener);
        databaseReference.child("games").child(room_id).removeValue();
        Intent intent = new Intent(this, RoomsActivity.class);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
    }

    private void makeTurn(String turn) {
        databaseReference.child("games").child(room_id).child("turns").child(String.valueOf(this.turn)).setValue(player + turn);
        this.turn++;
    }

    public boolean isMyTurn() {
        return game.getCurrentPlayer() == player;
    }

    //  click listeners
    public void okButtonClicked(View view) {
        if (isMyTurn()) {
            ArrayList<Integer> barnInventoryManagerSelection = playerDrawers.get(player).getBarnInventoryManager().getSelection();
            ArrayList<Integer> animalMarketManagerSelection = animalMarketManager.getSelection();
            if (barnInventoryManagerSelection.size() != 0 & animalMarketManagerSelection.size() != 0) {
                if (game.buyAnimal(animalMarketManagerSelection, barnInventoryManagerSelection)) {
                    makeTurn("5" + Utils.listToString(animalMarketManagerSelection) + "-" + Utils.listToString(barnInventoryManagerSelection));
                    endGame();
                    playerDrawers.get(player).updateBarnInventory((ArrayList<Card>) ((ArrayList<?>) game.getBarnInventory(player)));
                    okButtonDrawer.hideButton();
                    playerDrawers.get(player).updateAnimals(game.getAnimals(player));
                    updateAnimalMarket();
                }
            } else if (barnInventoryManagerSelection.size() == 0 & animalMarketManagerSelection.size() == 0) {
                makeTurn("7");
                okButtonDrawer.hideButton();
                game.nextPhase();
            }
            updateTurn();
        }
    }

    public void foodMarketClicked(View view) {
        ImageView clickedButton = findViewById(view.getId());
        buyFood(foodMarketManager.cardClicked(clickedButton));
    }

    public void foodMarketHiddenCardClicked(View view) {
        buyFood(6);
    }

    private void buyFood(int i) {
        if (isMyTurn()) {
            if (game.buyFood(i)) {
                makeTurn("2" + i);
                if (game.getPhase() == 2 | !isMyTurn()) {
                    okButtonDrawer.hideButton();
                    playerDrawers.get(player).updateBarnPlants(game.getBarnPlants(player));
                } else {
                    okButtonDrawer.showButton();
                }
                updateFoodMarket();
                playerDrawers.get(player).updateCoins(game.getCoins(player));
                updateHand();
            }
            updateTurn();
        }
    }

    public void plantClicked(View view) {
        if (isMyTurn()) {
            ArrayList<Integer> handCardSelection = handManager.getSelection();
            if (handCardSelection.size() != 0) {
                if (game.plant(handCardSelection)) {
                    playerDrawers.get(player).updateBarnPlants(game.getBarnPlants(player));
                    makeTurn("1" + Utils.listToString(handCardSelection));
                    updateHand();
                }
            }
            updateTurn();
        }
    }

    public void barnPlants2Clicked(View view) {
        harvest(1);
    }

    public void barnPlants3Clicked(View view) {
        harvest(2);
    }

    public void harvest(int i) {
        if (isMyTurn()) {
            if (game.harvest(i)) {
                makeTurn("3" + i);
                playerDrawers.get(player).updateBarnPlants(game.getBarnPlants(player));
                playerDrawers.get(player).updateBarnInventory((ArrayList<Card>) ((ArrayList<?>) game.getBarnInventory(player)));
            }
            updateTurn();
        }
    }

    public void handClicked(View view) {
        handManager.cardClicked(findViewById(view.getId()));
    }

    public void barnInventoryClicked(View view) {
        playerDrawers.get(player).getBarnInventoryManager().cardClicked(findViewById(R.id.barn).findViewById(view.getId()));
        checkOkButton();
    }

    public void animalmarketClicked(View view) {
        animalMarketManager.cardClicked(findViewById(view.getId()));
        checkOkButton();
    }

    private void checkOkButton() {
        if (playerDrawers.get(player).getBarnInventoryManager().getSelection().size() != 0 & animalMarketManager.getSelection().size() != 0) {
            okButtonDrawer.showButton();
        } else {
            okButtonDrawer.hideButton();
        }
    }

    public void coinClicked(View view) {
        if (isMyTurn()) {
            ArrayList<Integer> barnInventoryManagerSelection = playerDrawers.get(player).getBarnInventoryManager().getSelection();
            if (barnInventoryManagerSelection.size() != 0) {
                if (game.sell(barnInventoryManagerSelection)) {
                    makeTurn("4" + Utils.listToString(barnInventoryManagerSelection));
                    playerDrawers.get(player).updateCoins(game.getCoins(player));
                    playerDrawers.get(player).updateBarnInventory((ArrayList<Card>) ((ArrayList<?>) game.getBarnInventory(player)));
                }
            } else {
                if (game.coin()) {
                    makeTurn("6");
                    playerDrawers.get(player).updateCoins(game.getCoins(player));
                }
            }
            updateTurn();
        }
    }

    //  updating layout
    private void updateHand() {
        handManager.update((ArrayList<Card>) ((ArrayList<?>) game.getHand(player)));
    }

    private void updateAnimalMarket() {
        animalMarketManager.update((ArrayList<Card>) ((ArrayList<?>) game.getAnimalMarket()));
    }

    private void updateFoodMarket() {
        foodMarketManager.update(game.getFoodMarket());
    }

    private void updateTurn() {
        if (isMyTurn()) {
            playerDrawers.get(player).getBarnPlantsManager().getButtonDrawer().enableButton();
            okButtonDrawer.enableButton();
        } else {
            playerDrawers.get(player).getBarnPlantsManager().getButtonDrawer().disableButton();
            okButtonDrawer.disableButton();
        }
        playerDrawers.get(game.getCurrentPlayer()).updateBarnPlants(game.getBarnPlants(game.getCurrentPlayer()));
        for (int i = 0; i < players.size(); i++) {
            playerDrawers.get(i).updateNickname(game.getCurrentPlayer() == i);
        }
    }
}
