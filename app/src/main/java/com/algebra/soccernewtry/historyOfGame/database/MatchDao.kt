package com.algebra.soccernewtry.historyOfGame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.historyOfGame.model.Match

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatch(match: Match)

    @Query("DELETE FROM matches WHERE :id = id")
    fun deleteMatch(id: Int)

    @Query("SELECT * FROM matches")
    fun getAllMatched(): LiveData<List<Match>>
}