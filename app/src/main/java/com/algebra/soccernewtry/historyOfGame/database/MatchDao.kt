package com.algebra.soccernewtry.historyOfGame.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.algebra.soccernewtry.display.historyOfMatch.HistoryOfGame
import com.algebra.soccernewtry.historyOfGame.model.Match
import com.algebra.soccernewtry.runningGame.model.HistoryName

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatch(match: Match)

    @Query("DELETE FROM matches WHERE :id = id")
    fun deleteMatch(id: Int)

    @Query("DELETE FROM matches")
    fun deleteAllMatches()

    @Query("DELETE FROM MatchFlow WHERE matchId = :matchId")
    fun deleteMatchFlowMatch(matchId: Int)

    @Query("SELECT * FROM matches")
    fun getAllMatched(): LiveData<List<Match>>

    @Query("SELECT * FROM matches")
    suspend fun getAllMatchesCourtine(): List<Match>

    @Query("select \n" +
            "m.id AS id, \n" +
            "(SELECT COUNT(*) FROM MatchFlow WHERE teamId = 1 AND matchId = m.id) AS goalRed,\n" +
            "(SELECT COUNT(*) FROM MatchFlow WHERE teamId = 2 AND matchId = m.id) AS goalBlue,\n" +
            "m.name AS date\n" +
            "FROM matches m")
    fun getAllMatchResults(): LiveData<List<HistoryOfGame>>
}