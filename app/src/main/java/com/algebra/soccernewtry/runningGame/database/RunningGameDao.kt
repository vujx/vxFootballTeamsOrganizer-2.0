package com.algebra.soccernewtry.runningGame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.runningGame.model.History
import com.algebra.soccernewtry.runningGame.model.HistoryName

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

   @Query("SELECT * FROM (SELECT ap1.Name AS goalgetter, ap2.Name AS assister, rg.* FROM runningGame rg\n" +
            "LEFT JOIN AllPlayers ap1 ON ap1.id = rg.goalgetterId\n" +
            "LEFT JOIN AllPlayers ap2 ON ap2.id = rg.assisterId WHERE rg.assisterId != -1\n" +
            "UNION\n" +
            "SELECT ap1.Name AS goalgetter, ' ' AS assister, rg.* FROM runningGame rg\n" +
            "LEFT JOIN AllPlayers ap1 ON ap1.id = rg.goalgetterId\n" +
            "WHERE rg.assisterId == -1)\n" +
            "GROUP BY id\n" +
            "ORDER BY id DESC")
    fun getAllHistorybyId(): LiveData<List<HistoryName>>

    @Query("SELECT * FROM (SELECT ap1.Name AS goalgetter, ap2.Name AS assister, rg.* FROM runningGame rg\n" +
            "LEFT JOIN AllPlayers ap1 ON ap1.id = rg.goalgetterId\n" +
            "LEFT JOIN AllPlayers ap2 ON ap2.id = rg.assisterId WHERE rg.assisterId != -1\n" +
            "UNION\n" +
            "SELECT ap1.Name AS goalgetter, ' ' AS assister, rg.* FROM runningGame rg\n" +
            "LEFT JOIN AllPlayers ap1 ON ap1.id = rg.goalgetterId\n" +
            "WHERE rg.assisterId == -1)\n" +
            "GROUP BY id\n" +
            "ORDER BY id DESC")
    suspend fun getAllHistoryByCorutine(): List<HistoryName>

}