package com.example.myhappyfarm.utils;

import java.util.ArrayList;

public class Utils {
    public static String DB_REF_URL = "https://myhappyfarmonline-default-rtdb.europe-west1.firebasedatabase.app/";

    public static String listToString(ArrayList<Integer> list) {
        StringBuilder r = new StringBuilder();
        for (Integer i : list) {
            r.append(i.toString());
        }
        return r.toString();
    }

    public static ArrayList<Integer> stringToList(String string) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            r.add(Integer.valueOf(string.charAt(i)) - '0');
        }
        return r;
    }
}