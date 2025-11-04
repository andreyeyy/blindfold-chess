package com.example.blindfoldchess

fun squareToStr(file: Int, rank: Int): String{
    require(file >= 0 && rank >= 0 && file < 8 && rank < 8)
    return "${'a'+file}${rank+1}"
}