package com.algebra.soccernewtry.player.database

import android.service.autofill.FieldClassification
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.RoomDatabase
import com.algebra.soccernewtry.historyOfGame.database.MatchDao
import com.algebra.soccernewtry.historyOfGame.model.Match
import com.algebra.soccernewtry.matchFlow.database.MatchFlowDao
import com.algebra.soccernewtry.matchFlow.model.MatchFlow
import com.algebra.soccernewtry.matchPlayers.database.MatchPlayerDao
import com.algebra.soccernewtry.matchPlayers.model.MatchPlayer
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.runningGame.database.RunningGameDao
import com.algebra.soccernewtry.runningGame.model.History
import com.algebra.soccernewtry.stateactivity.database.StateActivityDao
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity

@Database(entities = [Player::class, History::class, StateOfActivity::class, Match::class, MatchFlow::class, MatchPlayer::class], version = 4, exportSchema = false)
abstract class AppDatabaseAllPlayers: RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun runningGameDao(): RunningGameDao
    abstract fun stateActivityDao(): StateActivityDao
    abstract fun matchesDao(): MatchDao
    abstract fun matchesFlowDao(): MatchFlowDao
    abstract fun matchPlayersDao(): MatchPlayerDao
}