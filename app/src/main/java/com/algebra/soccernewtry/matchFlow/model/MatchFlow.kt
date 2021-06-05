package com.algebra.soccernewtry.matchFlow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MatchFlow")
data class MatchFlow(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "matchId")
    val matchId: Int,

    @ColumnInfo(name = "goalgetterId")
    val goalgetterId: Int,

    @ColumnInfo(name = "assisterId")
    val assisterId: Int,

    @ColumnInfo(name = "teamId")
    val teamId: Int,

    @ColumnInfo(name = "isAutoGoal")
    val isAutoGoal: Int
)