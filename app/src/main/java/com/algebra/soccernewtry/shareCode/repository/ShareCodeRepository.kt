package com.algebra.soccernewtry.shareCode.repository

import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.player.database.AppDatabaseAllPlayers
import com.algebra.soccernewtry.shareCode.model.ShareCode
import java.util.concurrent.Executor
import javax.inject.Inject


class ShareCodeRepository @Inject constructor(private val databaseAllPlayers: AppDatabaseAllPlayers,
                                              @DatabaseIoExecutor private val databaseExecutor: Executor){

    private val shareCodeDao = databaseAllPlayers.shareCodeDao()

    fun addShareCode(code: ShareCode){
        databaseExecutor.execute {
            shareCodeDao.insertShareCode(code)
        }
    }

    fun getAllCodesRepo() = shareCodeDao.getAllCodes()

    fun deleteShareCodeById(id: Int){
        databaseExecutor.execute {
            shareCodeDao.deleteShareCode(id)
        }
    }

    fun deleteAllCodes(){
        databaseExecutor.execute {
            shareCodeDao.deleteAll()
        }
    }
}