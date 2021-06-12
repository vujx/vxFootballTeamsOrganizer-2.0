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

    suspend fun getAllPlayers(): List<Player>{
        return playerDao.getAllPlayersForStat()
    }

    fun getAllPlayerLiveRepo() = playerDao.getAllPlayers()

    fun updatePlayerRepo(player: Player){
        databaseExecutor.execute {
            playerDao.updatePlayer(player)
        }
    }

    fun deleteAllTable(){
        databaseExecutor.execute {
            playerDao.deleteAll()
        }
    }

    suspend fun getAllPlayersStatRepo(id: Int) = playerDao.getAllPlayerStat(id)

    suspend fun getNumberOfGolasRepo(id: Int) = playerDao.getNumberOfGoals(id)

    suspend fun getNumberOfAssistRepo(id: Int) = playerDao.getNumberOfAssist(id)

    suspend fun getNumberOfAutogoals(id: Int) = playerDao.getNumberOfAutogoal(id)

    suspend fun getResultOfMatch(id: Int) = playerDao.getMatchResult(id)

    suspend fun getResultOfMatchBlueTeam(id: Int) = playerDao.getMatchResultBlue(id)

    suspend fun getTeamIdPlayer(matchId: Int, playerId: Int) = playerDao.getPlayerTeamId(matchId, playerId)

    suspend fun getPlayersMatchesRepo(id: Int) = playerDao.getPlayersMatches(id)
}