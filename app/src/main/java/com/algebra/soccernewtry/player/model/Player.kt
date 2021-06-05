package com.algebra.soccernewtry.player.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "AllPlayers")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "defense")
    val defense: Int,
    @ColumnInfo(name = "agility")
    val agility: Int,
    @ColumnInfo(name = "technique")
    val technique: Int,
    @ColumnInfo(name = "isPlaying")
    var isPlaying: Int,
    @ColumnInfo(name = "teamId")
    var teamId: Int,
    @ColumnInfo(name = "bonusPoints")
    var bonusPoints: Int,
    @ColumnInfo(name = "isDeleted")
    var isDeleted: Int
): Serializable