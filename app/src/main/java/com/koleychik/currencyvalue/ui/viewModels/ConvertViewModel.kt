package com.koleychik.currencyvalue.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koleychik.currencyvalue.R
import com.koleychik.currencyvalue.model.Currencies
import com.koleychik.currencyvalue.spinner.SpinnerModel
import com.koleychik.currencyvalue.ui.state.ConvertState
import java.lang.StringBuilder

class ConvertViewModel : ViewModel() {

    val list = MutableLiveData<List<Currencies>>()

    val number1 = MutableLiveData(0)
    val number2 = MutableLiveData(1)

    val state = MutableLiveData<ConvertState>(ConvertState.Loading)

    private var keys = 0

    val isFavorite = MutableLiveData(false)

    fun putPoint(text: String): String {
        val str = deleteAllPoints(text)
        val builder = StringBuilder()

        val masWithPoint = str.split(".").toMutableList()

        var mas = masWithPoint[0].toCharArray()
        mas = mas.reversedArray()
        var num = 0

        for (i in mas) {
            num++
            builder.append(i)
            if (num == 3) {
                builder.append(" ")
                num = 0
            }
        }
        masWithPoint[0] = builder.reverse().toString()

        return if (masWithPoint.size > 1) {
            "${masWithPoint[0]}.${masWithPoint[1]}"
        } else {
            masWithPoint[0]
        }
    }

    private fun deleteAllPoints(text: String): String {
        val mas = text.toCharArray()
        val str = StringBuilder()
        for (i in mas) {
            if (i == ' ') continue

            str.append(i)
        }
        return str.toString()
    }

    fun clickOnKeyBoard(): Boolean {
        keys++
        if (keys - 3 == 1) {
            keys = 0
            return true
        }
        return false
    }

    fun makeListSpinnerModel(): ArrayList<SpinnerModel>{
        val arrayList = ArrayList<SpinnerModel>()
        arrayList.add(SpinnerModel(R.drawable.usa_flag))
        arrayList.add(SpinnerModel(R.drawable.euro_flag))
        arrayList.add(SpinnerModel(R.drawable.russian_flag))
        arrayList.add(SpinnerModel(R.drawable.poland_flag))
        arrayList.add(SpinnerModel(R.drawable.switzerland_flag))
        arrayList.add(SpinnerModel(R.drawable.uk_flag))
        arrayList.add(SpinnerModel(R.drawable.belarusian_flag))
        return arrayList
    }
}