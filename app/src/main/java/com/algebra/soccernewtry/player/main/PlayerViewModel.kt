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
}