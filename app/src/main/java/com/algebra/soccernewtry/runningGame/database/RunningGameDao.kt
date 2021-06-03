package com.algebra.soccernewtry.runningGame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.runningGame.model.History

@Dao
interface RunningGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Query("SELECT * FROM runningGame")
    fun getAllHistory(): LiveData<List<History>>

    @Query("DELETE FROM runningGame WHERE id = :id")
    fun deleteHistory(id: Int)

    @Query("DELETE FROM runningGame")
    fun deleteAll()


}