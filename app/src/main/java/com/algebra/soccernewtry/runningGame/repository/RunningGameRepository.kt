package com.algebra.soccernewtry.runningGame.repository

import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.runningGame.database.AppDatabaseRunningGame
import com.algebra.soccernewtry.runningGame.model.History
import java.util.concurrent.Executor
import javax.inject.Inject

class RunningGameRepository @Inject constructor(private val database: AppDatabaseRunningGame,
                                @DatabaseIoExecutor private val databaseExecutor: Executor){

    private val runningGameDao = database.runningGameDao()

    fun addHistoryRepo(history: History){
        databaseExecutor.execute {
            runningGameDao.insertHistory(history)
        }
    }

    fun deleteHistory(id: Int){
        databaseExecutor.execute {
            runningGameDao.deleteHistory(id)
        }
    }

    fun deleteAll(){
        databaseExecutor.execute {
            runningGameDao.deleteAll()
        }
    }

    fun getAllHistory() = runningGameDao.getAllHistory()
}