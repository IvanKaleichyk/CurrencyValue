package com.koleychik.currencyvalue.additions

import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.RoundingMode

class Convert {

    companion object{
        fun convert(num : BigDecimal, value1 : BigDecimal, value2: BigDecimal) : String{
            if (value1 == value2) {
                return num.toString()
            }

//        сокращение гривны IPA
            val ipa = num.multiply(value1)
            return ipa.divide(value2,2, RoundingMode.HALF_EVEN).toString()
        }
        fun deleteAllSpace(value : String) : String{
            if (value == "") return "0"
            val stringBuilder = StringBuilder()
            for (i in value){
                if ( i != ' '){
                    stringBuilder.append(i)
                }
            }
            return stringBuilder.toString()
        }
    }
}