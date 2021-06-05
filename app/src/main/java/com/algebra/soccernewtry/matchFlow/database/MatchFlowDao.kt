package com.algebra.soccernewtry.matchFlow.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.matchFlow.model.MatchFlow

@Dao
interface MatchFlowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatchFlow(matchFlow: MatchFlow)

    @Query("SELECT * FROM MatchFlow")
    fun getAllMatchFlowRepo(): LiveData<List<MatchFlow>>


}