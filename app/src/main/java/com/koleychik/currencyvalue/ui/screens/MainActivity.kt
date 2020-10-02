package com.koleychik.currencyvalue.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koleychik.currencyvalue.MainSingleton
import com.koleychik.currencyvalue.R
import com.koleychik.currencyvalue.additions.Keys
import com.koleychik.currencyvalue.additions.SharedPreferenceUtils
import com.koleychik.currencyvalue.ui.dialogs.DialogErrorInternetConnection
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferenceUtils = SharedPreferenceUtils(this)

        val list = sharedPreferenceUtils.getListFavorites()

        if (list.isEmpty()) {
            Log.d(Keys.APP, "list.isEmpty()")
            MainSingleton.getMainSingleton().listFavorite = ArrayList()
        } else {
            Log.d(Keys.APP, "list.isNotEmpty()")
            MainSingleton.getMainSingleton().listFavorite = list
                    as ArrayList<String>
        }


        val dialog = DialogErrorInternetConnection(this)
        dialog.startCheckInternet()
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferenceUtils = SharedPreferenceUtils(this)

        Log.d(Keys.APP, "start save")

        sharedPreferenceUtils.saveToSPrefListFavorites(MainSingleton.getMainSingleton().listFavorite)
    }
}