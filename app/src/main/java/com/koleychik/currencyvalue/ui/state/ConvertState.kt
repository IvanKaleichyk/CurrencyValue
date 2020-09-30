package com.koleychik.currencyvalue.ui.state

import com.koleychik.currencyvalue.model.Currencies

sealed class ConvertState {

    object Loading : ConvertState()
    class Show(val list: List<Currencies>) : ConvertState()
    class Error(val textRes: Int) : ConvertState()
}