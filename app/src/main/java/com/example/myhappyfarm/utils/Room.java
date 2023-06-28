package com.example.myhappyfarm.utils;

import java.util.ArrayList;

public class Room {
    private String id;
    private ArrayList<String> players;

    public Room(String id, ArrayList<String> players) {
        this.id = id;
        this.players = players;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }
}
