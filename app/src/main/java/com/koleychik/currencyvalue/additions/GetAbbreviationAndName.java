package com.koleychik.currencyvalue.additions;


import com.koleychik.currencyvalue.R;

public class GetAbbreviationAndName {
    public static int getAbbreviation(String name){
        switch (name){
            case "ДОЛЛАР":
                return R.string.currency_abbreviation_usd;
            case "ЕВРО":
                return R.string.currency_abbreviation_eur;
            case "РУБЛЬ":
                return R.string.currency_abbreviation_rub;
            case "ПОЛЬСКИЙ ЗЛОТЫЙ":
                return R.string.currency_abbreviation_pln;
            case "ШВЕЙЦАРСКИЙ ФРАНК":
                return R.string.currency_abbreviation_chf;
            case "АНГЛИЙСКИЙ ФУНТ СТЕРЛИНГОВ":
                return R.string.currency_abbreviation_gbr;
//           i don't know i get 2 name of belarusian name currency
            case "БЕЛОРУССКИЙ РУБЛЬ  БЕЛОРУССКИЙ РУБЛЬ":
                return R.string.currency_abbreviation_byn;
        }
        return R.string.currency_abbreviation_usd;
    }

    public static int getFlag(String name){
        switch (name){
            case "ДОЛЛАР":
                return R.drawable.usa_flag;
            case "ЕВРО":
                return R.drawable.euro_flag;
            case "РУБЛЬ":
                return R.drawable.russian_flag;
            case "ПОЛЬСКИЙ ЗЛОТЫЙ":
                return R.drawable.poland_flag;
            case "ШВЕЙЦАРСКИЙ ФРАНК":
                return R.drawable.switzerland_flag;
            case "АНГЛИЙСКИЙ ФУНТ СТЕРЛИНГОВ":
                return R.drawable.uk_flag;
//           i don't know i get 2 name of belarusian name currency
            case "БЕЛОРУССКИЙ РУБЛЬ  БЕЛОРУССКИЙ РУБЛЬ":
                return R.drawable.belarusian_flag;
        }
        return R.string.currency_abbreviation_usd;
    }
}
