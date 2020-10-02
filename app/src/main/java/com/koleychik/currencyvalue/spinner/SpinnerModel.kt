package com.koleychik.currencyvalue.spinner

import com.koleychik.currencyvalue.R


class SpinnerModel(var img: Int) {

    var name = 0
    var abbreviation = 0

    init {
        when (img){
            R.drawable.usa_flag -> {
                name = R.string.currency_name_usd
                abbreviation = R.string.currency_abbreviation_usd
            }
            R.drawable.euro_flag ->{
                name = R.string.currency_name_eur
                abbreviation = R.string.currency_abbreviation_eur
            }
            R.drawable.russian_flag ->{
                name = R.string.currency_name_rub
                abbreviation = R.string.currency_abbreviation_rub
            }
            R.drawable.poland_flag ->{
                name = R.string.currency_name_pln
                abbreviation = R.string.currency_abbreviation_pln
            }
            R.drawable.switzerland_flag ->{
                name = R.string.currency_name_chf
                abbreviation = R.string.currency_abbreviation_chf
            }
            R.drawable.uk_flag ->{
                name = R.string.currency_name_gbr
                abbreviation = R.string.currency_abbreviation_gbr
            }
            R.drawable.belarusian_flag ->{
                name = R.string.currency_name_byn
                abbreviation = R.string.currency_abbreviation_byn
            }
        }
    }
}