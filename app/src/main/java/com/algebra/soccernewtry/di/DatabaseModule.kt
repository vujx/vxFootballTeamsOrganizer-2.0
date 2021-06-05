package com.algebra.soccernewtry.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
        val MIGRATION_3_4 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
        return Room.databaseBuilder(context, AppDatabaseAllPlayers::class.java, "mydb22").addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
    }
}