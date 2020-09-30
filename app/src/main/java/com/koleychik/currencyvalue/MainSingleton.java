package com.koleychik.currencyvalue;

import com.koleychik.currencyvalue.model.Currencies;

import java.util.ArrayList;
import java.util.HashMap;

public class MainSingleton {

//    private HashSet<String> favorites = new HashSet<>();
    private ArrayList<String> listFavoriteID = new ArrayList<>();
    private ArrayList<Currencies> list = new ArrayList<>();

    private HashMap<String, Float> mapValueLastDay = new HashMap<>();

    public static MainSingleton instance = null;

    private MainSingleton() {
    }

    public static MainSingleton getMainSingleton() {
        if (instance == null) instance = new MainSingleton();
        return instance;
    }

    public void addToMap(String keys, float value){
        mapValueLastDay.put(keys, value);
    }

    public float getValueFromMap(String keys){
        Float res = mapValueLastDay.get(keys);
        if (res == null) return 0;
        return res;
    }

    public ArrayList<Currencies> getList() {
        return list;
    }

    public void setList(ArrayList<Currencies> list) {
        this.list = list;
    }

    public ArrayList<String> getListFavorite() {
        return listFavoriteID;
    }

    public void setListFavorite(ArrayList<String> listFavorite) {
        this.listFavoriteID = listFavorite;
    }
}
