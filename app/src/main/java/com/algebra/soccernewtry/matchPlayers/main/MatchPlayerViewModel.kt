package com.algebra.soccernewtry.matchPlayers.main

import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.matchPlayers.model.MatchPlayer
import com.algebra.soccernewtry.matchPlayers.repository.MatchPlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchPlayerViewModel @Inject constructor(private val matchPlayerRepository: MatchPlayerRepository): ViewModel() {

    fun getAllMatchPlayer() = matchPlayerRepository.getAllMatchPlayerRepo()

    fun addMatchPlayer(matchPlayer: MatchPlayer){
        matchPlayerRepository.addMatchPlayer(matchPlayer)
    }

    fun deleteAllMatchPlayers() {
        matchPlayerRepository.deleteAllPlayersFromMatch()
    }

    suspend fun getAll() = matchPlayerRepository.getAll()
}