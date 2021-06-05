package com.algebra.soccernewtry.display.achievement

data class PlayerStat(
    val id: Int,
    val name: String,//dohvatiti iz player tablice
    val wins: Int,
    val loses: Int,
    val draw: Int,
    val numberOfGoal: Int,
    val asistent: Int,
    val autoGoal: Int,
    val attendance: Int,
    val bonusPoints: Int//dohvatiti iz player tablice
)