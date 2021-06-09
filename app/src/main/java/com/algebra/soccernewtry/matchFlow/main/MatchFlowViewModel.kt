package com.algebra.soccernewtry.matchFlow.main

import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.matchFlow.model.MatchFlow
import com.algebra.soccernewtry.matchFlow.repository.MatchFlowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchFlowViewModel @Inject constructor(private val matchFlowRepository: MatchFlowRepository): ViewModel(){

    fun getAllMatchFlow() = matchFlowRepository.getAllMatchFlowRepo()

    fun addMatchFlow(matchFlow: MatchFlow){
        matchFlowRepository.addMatchFlowRepo(matchFlow)
    }

    fun deleteAll(){
        matchFlowRepository.deleteAllRepo()
    }

    suspend fun getAll() = matchFlowRepository.getAllMatchFlowCourtine()

    suspend fun getMatchScore(playerId: Int, matchId: Int) = matchFlowRepository.getMatchScoreResult(playerId, matchId)

    suspend fun getRedTeam(matchId: Int) = matchFlowRepository.getRedTeamsPlayersRepo(matchId)

    suspend fun getBlueTeam(matchId: Int) = matchFlowRepository.getBlueTeamsPlayersRepo(matchId)

    suspend fun getAllMatchFlowByMatchId(matchId: Int) = matchFlowRepository.getAllMatchFlow(matchId)
}