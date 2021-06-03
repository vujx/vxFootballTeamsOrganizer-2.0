package com.algebra.soccernewtry.stateactivity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity

@Dao
interface StateActivityDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertStateOfActivity(stateActivity: StateOfActivity)

    @Query("SELECT * FROM stateofactivity")
    fun getStateOfActivity(): LiveData<List<StateOfActivity>>
}