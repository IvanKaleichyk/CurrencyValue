package com.koleychik.currencyvalue.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.koleychik.currencyvalue.R
import kotlinx.android.synthetic.main.layout_internet_connection.*

class DialogErrorInternetConnection(private val context: Context ){

    private val dialog = Dialog(context)

    init {
        dialog.setContentView(R.layout.layout_internet_connection)
        dialog.btnConnect.setOnClickListener {
            startCheckInternet()
        }
    }

     fun startCheckInternet(){
        if (check()){
            dialog.dismiss()
        }
        else{
            dialog.show()
        }
    }

    private fun check(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

}