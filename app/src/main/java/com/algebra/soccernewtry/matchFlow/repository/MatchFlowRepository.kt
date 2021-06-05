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

    fun getAllMatchFlowRepo() = matchFlowDao.getAllMatchFlowRepo()
}