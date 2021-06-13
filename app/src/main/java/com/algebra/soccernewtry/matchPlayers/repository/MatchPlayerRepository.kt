package com.algebra.soccernewtry.matchPlayers.repository

import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.matchPlayers.model.MatchPlayer
import com.algebra.soccernewtry.player.database.AppDatabaseAllPlayers
import java.util.concurrent.Executor
import javax.inject.Inject

class MatchPlayerRepository @Inject constructor(private val databaseAllPlayers: AppDatabaseAllPlayers,
                                                @DatabaseIoExecutor private val databaseExecutor: Executor
) {

    private val matchPlayerDao = databaseAllPlayers.matchPlayersDao()

    fun addMatchPlayer(matchPlayer: MatchPlayer){
        databaseExecutor.execute {
            matchPlayerDao.addMatchPlayer(matchPlayer)
        }
    }

    fun getAllMatchPlayerRepo() = matchPlayerDao.getAllMatchPlayers()

    fun deleteAllPlayersFromMatch(){
        databaseExecutor.execute {
            matchPlayerDao.deleteAllMatchPlayers()
        }
    }

    suspend fun getAll() = matchPlayerDao.getAll()

    fun deleteAllMatchPlayersForMatch(matchId: Int){
        databaseExecutor.execute {
            matchPlayerDao.deleteMatchPlayers(matchId)
        }
    }
}