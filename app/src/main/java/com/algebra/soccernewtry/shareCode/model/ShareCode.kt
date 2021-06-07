package com.algebra.soccernewtry.shareCode.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dagger.multibindings.IntoMap

@Entity(tableName = "ShareCode")
data class ShareCode(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "code")
    val code: String
)