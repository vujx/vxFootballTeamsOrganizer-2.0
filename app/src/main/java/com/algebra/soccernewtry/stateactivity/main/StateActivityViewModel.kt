package com.algebra.soccernewtry.stateactivity.main

import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity
import com.algebra.soccernewtry.stateactivity.repository.StateActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StateActivityViewModel @Inject constructor(private val stateActivityRepo: StateActivityRepository): ViewModel() {

    fun addStateActiity(stateActivity: StateOfActivity){
        stateActivityRepo.addStateOfActivityRepo(stateActivity)
    }

    fun getStateOfActivity() = stateActivityRepo.getAllStateActivityRepo()
}