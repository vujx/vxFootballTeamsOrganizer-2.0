package com.algebra.soccernewtry.historyOfGame.repository

import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.historyOfGame.model.Match
import com.algebra.soccernewtry.player.database.AppDatabaseAllPlayers
import java.util.concurrent.Executor
import javax.inject.Inject

class MatchRepository @Inject constructor(private val database: AppDatabaseAllPlayers,
                                          @DatabaseIoExecutor private val databaseExecutor: Executor
) {

    private val historyMatchDao = database.matchesDao()

    fun addHistoryOfMatch(match: Match){
        databaseExecutor.execute {
            historyMatchDao.insertMatch(match)
        }
    }

    fun getAllMatchesRepo() = historyMatchDao.getAllMatched()

    fun deleteAllMatches(){
        databaseExecutor.execute {
            historyMatchDao.deleteAllMatches()
        }
    }

    suspend fun getAllMatches() = historyMatchDao.getAllMatchesCourtine()
}