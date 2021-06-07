package com.algebra.soccernewtry.game

import androidx.lifecycle.lifecycleScope
import com.algebra.soccernewtry.game.teams.blue.BlueTeamFragment
import com.algebra.soccernewtry.game.teams.red.RedTeamFragment
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.historyOfGame.model.Match
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchFlow.model.MatchFlow
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.matchPlayers.model.MatchPlayer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import java.util.*

class EndOperation(private val viewModelPlayers: PlayerViewModel, private val viewModelMatch: MatchViewModel,
                private val viewModelMatchFlow: MatchFlowViewModel, private val viewModelMatchPlayer: MatchPlayerViewModel
, private val activity: SubmitTeamsActivity) {

    fun endMatchOperation(){
        viewModelMatch.insertMatch(Match(0, getTodayDate()))
        activity.lifecycleScope.launchWhenResumed {
            viewModelPlayers.getAllPlayersForStat().forEach {
                if(it.isPlaying == 1 && it.isDeleted != 1) viewModelPlayers.addPlayer(Player(it.id, it.name, it.defense, it.agility, it.technique,
                    0,0, it.bonusPoints, it.isDeleted))
            }
        }
        var matchId: Int = -1
        activity.lifecycleScope.launchWhenResumed {
            val list = viewModelMatch.getAllMatchesCourtine()
            matchId = list[list.size - 1].id
        }


        SubmitTeamsActivity.adapterHistory.listOfResults.forEach {
            val isAutoGoal = if(it.autoGoal.isNullOrEmpty()) 0 else 1
            val teamId = if(it.isRed) 1
            else 2
            val matchFlow = MatchFlow(0, matchId, it.goalGeterId, it.assisterId, teamId, isAutoGoal)
            viewModelMatchFlow.addMatchFlow(matchFlow)
        }

        RedTeamFragment.checkListOfPlayers.forEach {
            viewModelMatchPlayer.addMatchPlayer(MatchPlayer(0 , matchId, it.id, it.teamId))
        }

        BlueTeamFragment.checkListOfPlayers.forEach {
            viewModelMatchPlayer.addMatchPlayer(MatchPlayer(0 ,matchId, it.id, it.teamId))
        }

    }
    private fun getTodayDate(): String{
        val today = Calendar.getInstance()

        val day = today.get(Calendar.DAY_OF_MONTH)
        val month = today.get(Calendar.MONTH) + 1
        val year = today.get(Calendar.YEAR)

        return "$day.$month.$year"
    }
}