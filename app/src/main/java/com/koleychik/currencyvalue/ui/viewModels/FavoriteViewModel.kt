package com.koleychik.currencyvalue.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koleychik.currencyvalue.ui.state.FavoriteState

class FavoriteViewModel : ViewModel() {

    val state = MutableLiveData<FavoriteState>(FavoriteState.Loading)
}