package com.algebra.soccernewtry.di

import android.content.Context
import androidx.room.Room
import com.algebra.soccernewtry.historyOfGame.database.AppDatabaseMatches
import com.algebra.soccernewtry.player.database.AppDatabaseAllPlayers
import com.algebra.soccernewtry.runningGame.database.AppDatabaseRunningGame
import com.algebra.soccernewtry.stateactivity.database.AppDatabaseStateOfActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabaseAllPlayers{
        return Room.databaseBuilder(context, AppDatabaseAllPlayers::class.java, "allplayers").build()
    }

    @Singleton
    @Provides
    fun provideDatabaseAllMacthes(@ApplicationContext context: Context): AppDatabaseMatches{
        return Room.databaseBuilder(context, AppDatabaseMatches::class.java, "matches").build()
    }

    @Singleton
    @Provides
    fun provideDatabaseStateOfActivity(@ApplicationContext context: Context): AppDatabaseStateOfActivity{
        return Room.databaseBuilder(context, AppDatabaseStateOfActivity::class.java, "stateactivity").build()
    }

    @Singleton
    @Provides
    fun provideDatabaseRunningGame(@ApplicationContext context: Context): AppDatabaseRunningGame{
        return Room.databaseBuilder(context, AppDatabaseRunningGame::class.java, "runninggame").build()
    }
}