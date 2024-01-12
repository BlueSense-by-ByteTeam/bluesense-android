package com.byteteam.bluesense.core.presentation.helper

object WaterQuality {
//    Air bersih
//    TDS max 1500 PPM
//    PH 6.5-9.0
//
//    Air minum
//    TDS max 500 PPM
//    PH 6.5 - 8.5
    fun checkWater(tds: Double, ph: Double): Pair<Boolean, Boolean> {
        var drinkable: Boolean
    var isClean: Boolean

    if(tds in 50.0..500.0 && ph in 6.5..8.5 ){
            drinkable = true
            isClean = true
        }else if(tds in 501.0..1500.0 && ph in 6.5..9.0){
            drinkable = false
            isClean = true
        }else{
            drinkable = false
            isClean = false
        }

        return Pair(isClean, drinkable)
    }

}