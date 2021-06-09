package com.algebra.soccernewtry.historyOfGame.main

import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.historyOfGame.model.Match
import com.algebra.soccernewtry.historyOfGame.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val matchRepository: MatchRepository): ViewModel() {

    fun getAllMatched() = matchRepository.getAllMatchesRepo()

    fun insertMatch(match: Match){
        matchRepository.addHistoryOfMatch(match)
    }

    suspend fun getAllMatchesCourtine() = matchRepository.getAllMatches()

    fun deleteAllMatches(){
        matchRepository.deleteAllMatches()
    }

    fun getMatchesResults() = matchRepository.getMatchesResults()

  //  fun insertValue(value: String) = matchRepository.insertNewValueMatches(value)
}