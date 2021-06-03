package com.algebra.soccernewtry.stateactivity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stateofactivity")
data class StateOfActivity (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "isEndedGame")
    val isEndedGame: Int
    )