package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.model

import androidx.room.ColumnInfo

data class PlayerMatchScore(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "goals")
    val goals: Int,

    @ColumnInfo(name = "assists")
    val assists: Int,

    @ColumnInfo(name = "owngoals")
    val owngoals: Int
)