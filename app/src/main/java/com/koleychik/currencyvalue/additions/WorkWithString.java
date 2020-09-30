package com.koleychik.currencyvalue.additions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkWithString {

    public static List<String> getListFromString(String value){
        String[] arr = value.split(",");
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static String getStringFromList(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (String i : list){
            stringBuilder.append(i).append(",");
        }
        try {
            return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        }
        catch (Exception e){
            return "";
        }
    }

    public static String getStringID(int id1, int id2){
        return String.valueOf(id1) + id2;
    }

}
