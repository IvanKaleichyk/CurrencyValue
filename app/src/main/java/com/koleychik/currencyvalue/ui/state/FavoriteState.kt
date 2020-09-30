package com.koleychik.currencyvalue.ui.state

import com.koleychik.currencyvalue.model.Favorites

sealed class FavoriteState {

    object Loading : FavoriteState()
    object NothingInDb : FavoriteState()
    class Show(val list: List<Favorites>) : FavoriteState()
}