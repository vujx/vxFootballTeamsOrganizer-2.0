package com.algebra.soccernewtry.player.main

import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.player.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val allPlayersRepo: PlayerRepository): ViewModel(){

    fun getAllPlayers() = allPlayersRepo.getAllPlayerLiveRepo()

    fun addPlayer(player: Player){
        allPlayersRepo.addPlayerRepo(player)
    }

    fun updatePlayer(player: Player){
        allPlayersRepo.updatePlayerRepo(player)
    }

    fun deletePlayer(id: Int){
        allPlayersRepo.deletePlayerRepo(id)
    }

    fun deleteAll(){
        allPlayersRepo.deleteAllTable()
    }

    suspend fun getAllPlayersForStat() = allPlayersRepo.getAllPlayers()

    suspend fun getAllPlayersSpecification(id: Int) = allPlayersRepo.getAllPlayersStatRepo(id)

    suspend fun getNumberOfGoals(id: Int) = allPlayersRepo.getNumberOfGolasRepo(id)

    suspend fun getNumberOfAssist(id: Int) = allPlayersRepo.getNumberOfAssistRepo(id)

    suspend fun getNumberOfAutogoals(id: Int) = allPlayersRepo.getNumberOfAutogoals(id)

    suspend fun getMatchResult(id: Int) = allPlayersRepo.getResultOfMatch(id)

    suspend fun getResultBlueTeam(id: Int) = allPlayersRepo.getResultOfMatchBlueTeam(id)

    suspend fun getTeamIdPLayer(matchId: Int, playerId: Int) = allPlayersRepo.getTeamIdPlayer(matchId, playerId)

    suspend fun getPlayersMatches(id: Int) = allPlayersRepo.getPlayersMatchesRepo(id)
}