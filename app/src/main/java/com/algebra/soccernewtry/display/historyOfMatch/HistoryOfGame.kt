package com.algebra.soccernewtry.display.historyOfMatch

import androidx.room.ColumnInfo

data class HistoryOfGame(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "goalRed")
    val goalRed: Int,

    @ColumnInfo(name = "goalBlue")
    val goalBlue: Int,

    @ColumnInfo(name = "date")
    val date: String)