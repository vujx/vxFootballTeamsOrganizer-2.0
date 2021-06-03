package com.algebra.soccernewtry.game.history

data class History(
    var goalRed: Int ,
    var goalBlue: Int ,
    val assisst: String,
    val goalGeter: String ,
    val isRed: Boolean,
    val id: Int,
    val autoGoal: String = ""
)