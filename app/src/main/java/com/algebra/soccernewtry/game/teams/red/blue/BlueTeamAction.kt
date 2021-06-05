package com.algebra.soccernewtry.game.teams.blue

import androidx.fragment.app.FragmentActivity
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.history.History
import com.algebra.soccernewtry.game.teams.red.RedTeamFragment

class BlueTeamAction {

    fun actionAfterClickOnGoalButton(requireActivity: FragmentActivity, playerNameAssist: String, playerNameGoalGeter: String){
        RedTeamFragment.goalBlue++
        addToDatabaseGoalgetterAndAssisst(requireActivity)
        var assiterId = 0
        var goalgetterId = 0
        BlueTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() == playerNameAssist.toLowerCase()) assiterId = it.id
            if(it.name.toLowerCase() == playerNameGoalGeter.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, playerNameAssist, playerNameGoalGeter,
            false, RedTeamFragment.idForHistory,"",goalgetterId, assiterId)
        SubmitTeamsActivity.adapterHistory.addResult(history)
        //databaseMethodsHistory.addHistoryToDatabase(history, requireActivity)

        setHistoryList(requireActivity)
    }

    private fun addToDatabaseGoalgetterAndAssisst(requireActivity: FragmentActivity){
        /*for(counter: Int in 0 until RandomTeamsActivity.blueTeam.size){
            if(RandomTeamsActivity.blueTeam[counter].isAssist){
                RandomTeamsActivity.blueTeam[counter].asistent++
                databaseMethods.updatePlayerToDatabase(RandomTeamsActivity.blueTeam[counter], requireActivity)
            }
            if(RandomTeamsActivity.blueTeam[counter].isGoalgeter){
                RandomTeamsActivity.blueTeam[counter].numberOfGoal++
                databaseMethods.updatePlayerToDatabase(RandomTeamsActivity.blueTeam[counter], requireActivity)
            }
        }*/
    }

    fun actionAfterClickOnGoalButtonIfThereIsJustGoalgeter(requireActivity: FragmentActivity, playerNameGoalGeter: String){
        addGoalgetterToDatabase(requireActivity)
        RedTeamFragment.goalBlue++

        var goalgetterId = 0
        BlueTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() == playerNameGoalGeter.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, "", playerNameGoalGeter,
            false, RedTeamFragment.idForHistory, "",goalgetterId, -1)
        SubmitTeamsActivity.adapterHistory.addResult(history)
        //databaseMethodsHistory.addHistoryToDatabase(history, requireActivity)

        setHistoryList(requireActivity)
    }

    private fun addGoalgetterToDatabase(requireActivity: FragmentActivity){
       /* RandomTeamsActivity.blueTeam.forEach {
            if(it.isGoalgeter){
                it.numberOfGoal++
                databaseMethods.updatePlayerToDatabase(it, requireActivity)
            }
        }*/
    }

    fun actionAfterAutogoal(requireActivity: FragmentActivity, playerNameAutoGoal: String){
        addAutogoalToDatabase(requireActivity)
        RedTeamFragment.goalRed++
        var goalgetterId = 0
        BlueTeamFragment.checkListOfPlayers.forEach {
            if(it.name.toLowerCase() == playerNameAutoGoal.toLowerCase()) goalgetterId = it.id
        }
        val history = History(RedTeamFragment.goalRed, RedTeamFragment.goalBlue, "", "", true,
            RedTeamFragment.idForHistory, playerNameAutoGoal, goalgetterId, -1)
        SubmitTeamsActivity.adapterHistory.addResult(history)
        //databaseMethodsHistory.addHistoryToDatabase(history, requireActivity)

        setHistoryList(requireActivity)
    }

    private fun addAutogoalToDatabase(requireActivity: FragmentActivity){
       /* RandomTeamsActivity.blueTeam.forEach {
            if(it.isAutoGol){
                it.autoGoal++
                databaseMethods.updatePlayerToDatabase(it, requireActivity)
            }
        }*/
    }

    private fun setHistoryList(requireActivity: FragmentActivity){
       /* val refreshDatabase = databaseMethodsHistory.getAllGoalsFromHistory(requireActivity)
        SubmitTeamActivity.adapterHistory.setList(refreshDatabase)
        RedTeamFragment.idForHistory++*/
    }
}