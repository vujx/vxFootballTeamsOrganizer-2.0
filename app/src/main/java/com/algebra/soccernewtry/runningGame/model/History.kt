package com.algebra.soccernewtry.runningGame.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runningGame")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "goalgetterId")
    val goalgetterId: Int,

    @ColumnInfo(name = "assisterId")
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