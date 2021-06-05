package com.algebra.soccernewtry.runningGame.model

import androidx.room.*
import androidx.annotation.Nullable

data class HistoryName(
    @ColumnInfo(name = "goalgetter")
    @Nullable
    val goalgetter: String,
    @ColumnInfo(name = "assister")
    @Nullable
    val assister: String,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "goalgetterId")
    val goalgetterId: Int,
    @ColumnInfo(name = "assisterId")
    @Nullable
    val assisterId: Int,
    @ColumnInfo(name = "teamId")
    val teamId: Int,
    @ColumnInfo(name = "isAutoGoal")
    val isAutoGoal: Int,
    @ColumnInfo(name = "goalRed")
    val goalRed: Int,
    @ColumnInfo(name = "goalBlue")
    val goalBlue: Int
)