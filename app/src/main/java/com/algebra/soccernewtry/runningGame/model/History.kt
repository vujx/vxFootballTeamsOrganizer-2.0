package com.algebra.soccernewtry.runningGame.model

import androidx.annotation.Nullable
import androidx.room.*

@Entity(tableName = "runningGame")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "goalgetterId")
    @Nullable
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
    val goalBlue: Int,
)