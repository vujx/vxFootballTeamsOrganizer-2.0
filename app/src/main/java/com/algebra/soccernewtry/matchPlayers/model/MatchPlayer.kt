package com.algebra.soccernewtry.matchPlayers.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MatchPlayer")
data class MatchPlayer(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "matchId")
    val matchId: Int,

    @ColumnInfo(name = "playerId")
    val playerId: Int,

    @ColumnInfo(name = "teamId")
    val teamId: Int
)