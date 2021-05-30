package com.algebra.soccernewtry.di

import android.content.Context
import androidx.room.Room
import com.algebra.soccernewtry.player.database.AppDatabaseAllPlayers
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
}