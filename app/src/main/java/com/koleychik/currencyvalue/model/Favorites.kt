package com.koleychik.currencyvalue.model

data class Favorites(
    val id: Int,
    val nameFirst: String,
    val nameSecond: String,
    var valueLastDay: Float,
    var valueToday: Float,
)