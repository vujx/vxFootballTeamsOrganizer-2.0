package com.algebra.soccernewtry.shareCode.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.shareCode.model.ShareCode

@Dao
interface ShareCodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShareCode(shareCode: ShareCode)

    @Query("SELECT * FROM ShareCode")
    fun getAllCodes(): LiveData<List<ShareCode>>

    @Query("DELETE FROM ShareCode WHERE id = :id")
    fun deleteShareCode(id: Int)

    @Query("DELETE FROM ShareCode")
    fun deleteAll()
}