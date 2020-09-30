package com.koleychik.currencyvalue.additions

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils(private val context: Context) {

    private val sPref: SharedPreferences =
        context.getSharedPreferences(Keys.MAIN_SPREF, Context.MODE_PRIVATE)

    fun getListFavorites() : List<String>{
        val value = sPref.getString(Keys.LIST_FAVORITES, null)
        return if (value == null) listOf()
        else WorkWithString.getListFromString(value)
    }

    fun saveToSPrefListFavorites(list: List<String>){
        val editor = sPref.edit()
        val string = WorkWithString.getStringFromList(list)
        if (string != "") {
            editor.putString(Keys.LIST_FAVORITES, string)
            editor.apply()
        }
    }

    fun saveFloat(keys: String, value: Float) {
        val editor = sPref.edit()
        editor.putFloat(keys, value)
        editor.apply()
    }

    fun getFloat(keys: String) = sPref.getFloat(keys, 0F)

}