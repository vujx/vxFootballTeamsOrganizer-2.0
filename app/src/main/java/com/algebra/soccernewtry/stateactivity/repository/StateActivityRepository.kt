package com.algebra.soccernewtry.stateactivity.repository

import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.stateactivity.database.AppDatabaseStateOfActivity
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity
import java.util.concurrent.Executor
import javax.inject.Inject

class StateActivityRepository @Inject constructor(private val databaase: AppDatabaseStateOfActivity,
    @DatabaseIoExecutor private val databaseExecutor: Executor) {

    private val stateActivityDao = databaase.stateActivityDao()

    fun addStateOfActivityRepo(stateActivity: StateOfActivity){
        databaseExecutor.execute {
            stateActivityDao.insertStateOfActivity(stateActivity)
        }
    }

    fun getAllStateActivityRepo() = stateActivityDao.getStateOfActivity()
}