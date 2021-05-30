package com.algebra.soccernewtry.player.repository

import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.player.database.AppDatabaseAllPlayers
import com.algebra.soccernewtry.player.model.Player
import java.util.concurrent.Executor
import javax.inject.Inject

class PlayerRepository @Inject constructor(private val databaseAllPlayers: AppDatabaseAllPlayers,
                            @DatabaseIoExecutor private val databaseExecutor: Executor) {

    private val playerDao = databaseAllPlayers.playerDao()

    fun addPlayerRepo(player: Player){
        databaseExecutor.execute {
            playerDao.insertPlayer(player)
        }
    }

    fun deletePlayerRepo(id: Int){
        databaseExecutor.execute {
            playerDao.deletePlayer(id)
        }
    }

    fun getAllPlayerLiveRepo() = playerDao.getAllPlayers()

    fun updatePlayerRepo(player: Player){
        databaseExecutor.execute {
            playerDao.updatePlayer(player)
        }
    }
}