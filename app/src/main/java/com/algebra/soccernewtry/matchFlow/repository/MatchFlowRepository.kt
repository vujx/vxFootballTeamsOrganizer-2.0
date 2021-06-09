package com.algebra.soccernewtry.matchFlow.repository

import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.matchFlow.model.MatchFlow
import com.algebra.soccernewtry.player.database.AppDatabaseAllPlayers
import java.util.concurrent.Executor
import javax.inject.Inject

class MatchFlowRepository @Inject constructor(private val databaseAllPlayers: AppDatabaseAllPlayers,
                                              @DatabaseIoExecutor private val databaseExecutor: Executor
) {

    private val matchFlowDao = databaseAllPlayers.matchesFlowDao()

    fun addMatchFlowRepo(matchFlow: MatchFlow){
        databaseExecutor.execute {
            matchFlowDao.insertMatchFlow(matchFlow)
        }
    }

    fun deleteAllRepo(){
        databaseExecutor.execute {
            matchFlowDao.deleteAll()
        }
    }
    fun getAllMatchFlowRepo() = matchFlowDao.getAllMatchFlowRepo()

    suspend fun getAllMatchFlowCourtine() = matchFlowDao.getAllMatchFlow()

    suspend fun getMatchScoreResult(playerId: Int, matchId: Int) = matchFlowDao.getResultForCurrentPlayer(playerId, matchId)

    suspend fun getRedTeamsPlayersRepo(matchId: Int) = matchFlowDao.getRedTeamsPlayers(matchId)

    suspend fun getBlueTeamsPlayersRepo(matchId: Int) = matchFlowDao.getBlueTeamsPlayers(matchId)

    suspend fun getAllMatchFlow(matchId: Int) = matchFlowDao.getAllMatchFlowByMacthId(matchId)
}