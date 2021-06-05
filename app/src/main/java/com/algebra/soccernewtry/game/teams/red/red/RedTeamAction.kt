package com.algebra.soccernewtry.game.teams.red

import androidx.fragment.app.FragmentActivity
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.history.History

class RedTeamAction {

    fun actionAfterAutogoal(requireActivity: FragmentActivity, playerNameAutoGoal: String){
        updatePlayerToDatabase(requireActivity)
        var goalgetterId: Int = 0
        RedTeamFragment.goalBlue++
        RedTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() == it.name.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, "", "", false,
            RedTeamFragment.idForHistory, playerNameAutoGoal, goalgetterId, -1)
        SubmitTeamsActivity.adapterHistory.addResult(history)
        //databaseMethodsHistory.addHistoryToDatabase(history, requireActivity)

        setHistoryList(requireActivity)
    }

    private fun updatePlayerToDatabase(requireActivity: FragmentActivity){
   /*     RandomTeamsActivity.redTeam.forEach {
            if(it.isAutoGol){
                it.autoGoal++
             //   databaseMethods.updatePlayerToDatabase(it, requireActivity)
            }
        }
        RedTeamFragment.goalBlue++*/
    }

    private fun setHistoryList(requireActivity: FragmentActivity){
       /* val refreshDatabase = databaseMethodsHistory.getAllGoalsFromHistory(requireActivity)
        SubmitTeamActivity.adapterHistory.setList(refreshDatabase)
        RedTeamFragment.idForHistory++*/
    }

    fun actionAfterClickOnGoalButtonIfThereIsJustGoalgeter(requireActivity: FragmentActivity, playerNameGoalGeter: String){
        updatePlayerAfterGoalgetter(requireActivity)
        RedTeamFragment.goalRed++
        var goalgetterId = 0
        RedTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() == playerNameGoalGeter.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, "", playerNameGoalGeter, true,
            RedTeamFragment.idForHistory, "", goalgetterId, -1)
        SubmitTeamsActivity.adapterHistory.addResult(history)
      //  databaseMethodsHistory.addHistoryToDatabase(history, requireActivity)

        setHistoryList(requireActivity)

    }

    private fun updatePlayerAfterGoalgetter(requireActivity: FragmentActivity){
      /*  RandomTeamsActivity.redTeam.forEach {
            if(it.isGoalgeter){
                it.numberOfGoal++
                databaseMethods.updatePlayerToDatabase(it, requireActivity)
            }
        }
        RedTeamFragment.goalRed++*/
    }

    fun actionAfterClickOnGoalButton(requireActivity: FragmentActivity, playerNameGoalGeter: String, playerNameAssist: String){
        updateGoalgetterAndAss(requireActivity)
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
  //      databaseMethodsHistory.addHistoryToDatabase(history, requireActivity)

        setHistoryList(requireActivity)
    }

    private fun updateGoalgetterAndAss(requireActivity: FragmentActivity){
     /*   RedTeamFragment.goalRed++
        for(counter: Int in 0 until RandomTeamsActivity.redTeam.size){
            if(RandomTeamsActivity.redTeam[counter].isAssist){
                RandomTeamsActivity.redTeam[counter].asistent++
                databaseMethods.updatePlayerToDatabase(RandomTeamsActivity.redTeam[counter], requireActivity)
            }
            if(RandomTeamsActivity.redTeam[counter].isGoalgeter){
                RandomTeamsActivity.redTeam[counter].numberOfGoal++
                databaseMethods.updatePlayerToDatabase(RandomTeamsActivity.redTeam[counter], requireActivity)
            }
        }*/
    }

}