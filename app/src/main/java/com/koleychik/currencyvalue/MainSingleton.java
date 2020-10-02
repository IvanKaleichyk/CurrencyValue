package com.koleychik.currencyvalue;

import com.koleychik.currencyvalue.model.Currencies;

import java.util.ArrayList;

public class MainSingleton {

    private ArrayList<String> listFavoriteID = new ArrayList<>();
    private ArrayList<Currencies> list = new ArrayList<>();

    public static MainSingleton instance = null;

    private MainSingleton() {
    }

    public static MainSingleton getMainSingleton() {
        if (instance == null) instance = new MainSingleton();
        return instance;
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
