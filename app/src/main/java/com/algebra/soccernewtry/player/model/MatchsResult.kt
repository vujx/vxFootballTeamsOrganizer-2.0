package com.algebra.soccernewtry.player.model

import androidx.room.ColumnInfo

data class MatchsResult(
    @ColumnInfo(name = "matchId")
    val matchId: Int,

    @ColumnInfo(name = "teamGoals")
    val teamGoals: Int,

    @ColumnInfo(name = "teamId")
    val teamId: Int
)