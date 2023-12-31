package com.example.myhappyfarm.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class Game {
    private ArrayList<Player> players;
    private FoodMarket foodMarket;
    private AnimalMarket animalMarket;
    private int currentPlayer;
    private int purchaseCounter;
    private int phase;

    public Game(ArrayList<String> players, int seed) {
        this.foodMarket = new FoodMarket(seed);
        this.animalMarket = new AnimalMarket(seed);
        this.players = new ArrayList<>();
        Player newPlayer;
        for (String nickname : players) {
            newPlayer = new Player(nickname);
            newPlayer.addFoodToHand(foodMarket.buy(0));
            newPlayer.addFoodToHand(foodMarket.buy(0));
            this.players.add(newPlayer);
        }
        this.currentPlayer = -1;
        this.purchaseCounter = 0;
        nextTurn();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public HashMap<String, ArrayList<Integer>> getAnimals(int player) {
        return players.get(player).getAnimals();
    }

    public ArrayList<ArrayList<FoodCard>> getBarnPlants(int player) {
        return players.get(player).getBarn().getPlants();
    }

    public ArrayList<FoodCard> getFoodMarket() {
        return foodMarket.getFaceup();
    }


    public ArrayList<AnimalCard> getAnimalMarket() {
        return animalMarket.getFaceup();
    }

    public ArrayList<FoodCard> getHand(int player) {
        return players.get(player).getHand();
    }

    public Integer getCoins(int player) {
        return players.get(player).getCoins();
    }

    public ArrayList<FoodCard> getBarnInventory(int player) {
        return players.get(player).getBarn().getInventory();
    }

    public int getPhase() {
        return phase;
    }

    public void nextPhase() {
        purchaseCounter = 0;
        if (phase == 0) {
            phase = 2;
        } else {
            nextTurn();
        }
    }

    public void nextTurn() {
        if (currentPlayer == players.size() - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        phase = 0;
        players.get(currentPlayer).getBarn().grow();
    }


    public boolean buyFood(int i) {
        if (phase != 2) {
            if (players.get(currentPlayer).getCoins() > 0 & getHand(currentPlayer).size() != 6) {
                players.get(currentPlayer).addFoodToHand(foodMarket.buy(i));
                players.get(currentPlayer).setCoins(-1);
                purchaseCounter++;
                if (purchaseCounter == 3 | getHand(currentPlayer).size() == 6 | getCoins(currentPlayer) == 0) {
                    purchaseCounter = 0;
                    if (phase == 0) {
                        phase = 2;
                    } else {
                        nextTurn();
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean buyAnimal(ArrayList<Integer> animalCards, ArrayList<Integer> foodCards) {
        if (phase != 5 & purchaseCounter == 0) {
            ArrayList<Integer> requiredCards = new ArrayList<>();
            for (Integer animalCard : animalCards) {
                requiredCards.addAll(getAnimalMarket().get(animalCard).getCost());
            }
            if (requiredCards.size() != foodCards.size()) {
                return false;
            }
            ArrayList<Integer> foodValues = new ArrayList<>();
            for (Integer f : foodCards) {
                foodValues.add(getBarnInventory(currentPlayer).get(f).getValue());
            }
            int k = 0;
            for (Integer c : requiredCards) {
                if (c == 0) {
                    k++;
                } else {
                    foodValues.remove(c);
                }
            }
            if (foodValues.size() == k) {
                Collections.sort(animalCards);
                Collections.reverse(animalCards);
                for (Integer animalCard : animalCards) {
                    players.get(currentPlayer).addAnimal(animalMarket.buy(animalCard));
                }
                sellFoodCards(foodCards, false);
                if (phase == 0) {
                    phase = 5;
                } else {
                    nextTurn();
                }
                return true;
            }
        }
        return false;
    }


    public boolean checkEnd() {
        return getAnimalMarket().size() == 0;
    }


    public ArrayList<HashMap<String, ArrayList<Integer>>> getPlayersScores() {
        ArrayList<HashMap<String, ArrayList<Integer>>> res = new ArrayList<>(3);
        for (int i = 0; i < players.size(); i++) {
            res.add(new HashMap<>());
        }
        ArrayList<ArrayList<Integer>> animalTerms = new ArrayList<>(3);
        for (String animal : new String[]{"rabbit", "sheep", "pig", "cow"}) {
            for (int i = 0; i < players.size(); i++) {
                animalTerms.add(players.get(i).getAnimal(animal));
                animalTerms.get(i).add(0, 0);
            }
            int[] maxnindex = new int[]{-1, 0};
            for (int i = 0; i < players.size(); i++) {
                int size = animalTerms.get(i).size();
                if (size == 1) {
                    animalTerms.get(i).set(0, -5);
                } else if (maxnindex[0] < size) {
                    maxnindex[0] = size;
                    maxnindex[1] = i;
                } else if (maxnindex[0] == size) {
                    maxnindex[0] = -1;
                    maxnindex[1] = i;
                }
            }
            if (maxnindex[0] != -1) {
                animalTerms.get(maxnindex[1]).set(0, 3);
            }
            for (int i = 0; i < players.size(); i++) {
                res.get(i).put(animal, animalTerms.get(i));
            }
            animalTerms.clear();
        }
        return res;
    }

    public boolean harvest(int i) {
        if (phase != 3 & purchaseCounter == 0) {
            if (6 - getBarnInventory(currentPlayer).size() >= getBarnPlants(currentPlayer).get(i).size()) {
                if (i == 1) {
                    for (FoodCard card : getBarnPlants(currentPlayer).get(i)) {
                        if (card.getValue() > 3) {
                            return false;
                        }
                    }
                }
                players.get(currentPlayer).getBarn().harvest(i);
                if (phase == 0) {
                    phase = 3;
                } else {
                    nextTurn();
                }
                return true;
            }
        }
        return false;
    }

    public boolean plant(ArrayList<Integer> cards) {
        if (phase != 1 & purchaseCounter == 0) {
            if (cards.size() == 3) {
                if (!(Objects.equals(players.get(currentPlayer).getHand().get(cards.get(0)).getValue(), players.get(currentPlayer).getHand().get(cards.get(1)).getValue()) & Objects.equals(players.get(currentPlayer).getHand().get(cards.get(1)).getValue(), players.get(currentPlayer).getHand().get(cards.get(2)).getValue()))) {
                    return false;
                }
            }
            Collections.sort(cards);
            players.get(currentPlayer).plant(cards);
            if (phase == 0) {
                phase = 1;
            } else {
                nextTurn();
            }
            return true;
        }
        return false;
    }

    public boolean sell(ArrayList<Integer> cards) {
        if (phase != 4 & purchaseCounter == 0) {
            sellFoodCards(cards, true);
            if (phase == 0) {
                phase = 4;
            } else {
                nextTurn();
            }
            return true;
        }
        return false;
    }

    private void sellFoodCards(ArrayList<Integer> cards, boolean b) {
        Collections.sort(cards);
        Collections.reverse(cards);
        for (Integer card : cards) {
            foodMarket.sell(players.get(currentPlayer).getBarn().getInventory().get(card));
            if (b) {
                players.get(currentPlayer).setCoins(players.get(currentPlayer).getBarn().getInventory().get(card).getValue());
            }
            players.get(currentPlayer).getBarn().getInventory().remove((int) card);
        }
    }

    public boolean coin() {
        if (phase != 6 & purchaseCounter == 0) {
            players.get(currentPlayer).setCoins(1);
            if (phase == 0) {
                phase = 6;
            } else {
                nextTurn();
            }
            return true;
        }
        return false;
    }
}
