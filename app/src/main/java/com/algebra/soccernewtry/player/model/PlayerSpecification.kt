package com.algebra.soccernewtry.player.model

import androidx.room.ColumnInfo

data class PlayerSpecification(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,//dohvatiti iz player tablice

    @ColumnInfo(name = "attendance")
    val attendance: Int,

    @ColumnInfo(name = "bonusPoints")
    val bonusPoints: Int//dohvatiti iz player tablice
)