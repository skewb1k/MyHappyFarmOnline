package com.example.myhappyfarm.drawers;

import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.myhappyfarm.R;
import com.example.myhappyfarm.game.Card;
import com.example.myhappyfarm.game.FoodCard;
import com.example.myhappyfarm.managers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PlayerDrawer {
    private final CardManager barnInventoryManager;
    private final BarnPlantsManager barnPlantsManager;
    private final CoinManager coinManager;
    private final AnimalsManager animalsManager;
    private final NicknameManager nicknameManager;


    public PlayerDrawer(String nickname, ConstraintLayout playerLayout, Button plantButton) {
        this.barnPlantsManager = new BarnPlantsManager(new ArrayList<>(Arrays.asList(playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants0), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants1), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants2), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants3), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants4), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants5), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants6), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants7), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants8), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants9), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants10), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants11), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants12), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants13), playerLayout.findViewById(R.id.barn).findViewById(R.id.barnplants14))), plantButton);
        this.barnInventoryManager = new CardManager(new ArrayList<>(Arrays.asList(playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory0), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory1), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory2), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory3), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory4), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory5))), new ArrayList<>(Arrays.asList(playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory0_selection), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory1_selection), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory2_selection), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory3_selection), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory4_selection), playerLayout.findViewById(R.id.barn).findViewById(R.id.barninventory5_selection))), 6);
        this.coinManager = new CoinManager(playerLayout.findViewById(R.id.barn).findViewById(R.id.coins));
        this.animalsManager = new AnimalsManager(new ArrayList<>(Arrays.asList(playerLayout.findViewById(R.id.animals).findViewById(R.id.rabbitcount), playerLayout.findViewById(R.id.animals).findViewById(R.id.sheepcount), playerLayout.findViewById(R.id.animals).findViewById(R.id.pigcount), playerLayout.findViewById(R.id.animals).findViewById(R.id.cowcount))));
        this.nicknameManager = new NicknameManager(playerLayout.findViewById(R.id.barn).findViewById(R.id.nickname), nickname);
    }

    public void updateBarnPlants(ArrayList<ArrayList<FoodCard>> values) {
        barnPlantsManager.update(values);
    }

    public void updateCoins(Integer value) {
        coinManager.update(value);
    }

    public void updateNickname(boolean value) {
        nicknameManager.update(value);
    }

    public void updateBarnInventory(ArrayList<Card> values) {
        barnInventoryManager.update(values);
    }

    public void updateAnimals(Map<String, ArrayList<Integer>> values) {
        animalsManager.update(values);
    }

    public CardManager getBarnInventoryManager() {
        return barnInventoryManager;
    }

    public BarnPlantsManager getBarnPlantsManager() {
        return barnPlantsManager;
    }
}
