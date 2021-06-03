package com.algebra.soccernewtry.runningGame.main

import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.runningGame.model.History
import com.algebra.soccernewtry.runningGame.repository.RunningGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunningGameViewModel @Inject constructor(private val runningGameRepo: RunningGameRepository): ViewModel() {

    fun getAllHistory() = runningGameRepo.getAllHistory()

    fun addHistory(history: History) {
        runningGameRepo.addHistoryRepo(history)
    }

    fun deleteAll(){
        runningGameRepo.deleteAll()
    }

    fun deleteHistory(id: Int){
        runningGameRepo.deleteHistory(id)
    }
}