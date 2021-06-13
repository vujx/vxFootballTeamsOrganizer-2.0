package com.algebra.soccernewtry.game.teams.red

import androidx.fragment.app.FragmentActivity
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.history.History

class RedTeamAction {

    fun actionAfterAutogoal(requireActivity: FragmentActivity, playerNameAutoGoal: String){
        var goalgetterId = 0
        RedTeamFragment.goalBlue++
        RedTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() ==  playerNameAutoGoal.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, "", "", false,
            RedTeamFragment.idForHistory, playerNameAutoGoal, goalgetterId, -1)
        SubmitTeamsActivity.adapterHistory.addResult(history)
    }

    fun actionAfterClickOnGoalButtonIfThereIsJustGoalgeter(requireActivity: FragmentActivity, playerNameGoalGeter: String){
        RedTeamFragment.goalRed++
        var goalgetterId = 0
        RedTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() == playerNameGoalGeter.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, "", playerNameGoalGeter, true,
            RedTeamFragment.idForHistory, "", goalgetterId, -1)
        SubmitTeamsActivity.adapterHistory.addResult(history)
    }

    fun actionAfterClickOnGoalButton(requireActivity: FragmentActivity, playerNameGoalGeter: String, playerNameAssist: String){
        RedTeamFragment.goalRed++
        var assisterId: Int = 0
        var goalgetterId: Int = 0
        RedTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() == playerNameAssist.toLowerCase()) assisterId = it.id
            if(it.name.toLowerCase() == playerNameGoalGeter.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, playerNameAssist, playerNameGoalGeter, true,
            RedTeamFragment.idForHistory,"" ,goalgetterId ,assisterId)
        SubmitTeamsActivity.adapterHistory.addResult(history)
    }
}