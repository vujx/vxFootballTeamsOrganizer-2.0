package com.algebra.soccernewtry.game.history.edit

import androidx.fragment.app.FragmentActivity
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.history.History
import com.algebra.soccernewtry.game.teams.blue.BlueTeamFragment
import com.algebra.soccernewtry.game.teams.red.RedTeamFragment

class EditHistory(private val activity: SubmitTeamsActivity,private val requireActivity: FragmentActivity) {

    fun editHistory(item: History){
        defaultValue()
        when{
            item.autoGoal?.isNotEmpty()!! -> {
                getAutogoal(item)
                val dialog = EditDialog( item)
                dialog.show(requireActivity.supportFragmentManager, "EDIT HISTORY")
                activity.setScore()
            }
            item.isRed -> {
                getAssAndGoalgetter(item)
                val dialog = EditDialog( item)
                dialog.show(requireActivity.supportFragmentManager, "EDIT HISTORY")
                activity.setScore()
            }
            else -> {
                getAssAndGoalgetter(item)
                val dialog = EditDialog( item)
                dialog.show(requireActivity.supportFragmentManager, "EDIT HISTORY")
                activity.setScore()
            }
        }
    }

    private fun defaultValue(){
        BlueTeamFragment.checkListOfPlayers.forEach {
            it.isAssist = false
            it.isGoalgeter = false
            it.isAutoGol = false
        }
        RedTeamFragment.checkListOfPlayers.forEach {
            it.isAutoGol = false
            it.isGoalgeter = false
            it.isAssist = false
        }
    }

    private fun getAssAndGoalgetter(item: History){
        RedTeamFragment.checkListOfPlayers.forEach {
            if(item.goalGeter == it.name) it.isGoalgeter = true
            if(item.assisst == it.name) it.isAssist = true
        }
        BlueTeamFragment.checkListOfPlayers.forEach {
            if(item.goalGeter == it.name)  it.isGoalgeter = true
            if(item.assisst == it.name) it.isAssist = true
        }
    }

    private fun getAutogoal(item: History){
        RedTeamFragment.checkListOfPlayers.forEach {
            if(item.autoGoal == it.name) it.isAutoGol = true
        }
        BlueTeamFragment.checkListOfPlayers.forEach {
            if(item.autoGoal == it.name) if(item.autoGoal == it.name) it.isAutoGol = true
        }
    }

    fun updateAllList(resultForHistory: History, position: Int) {
        if (resultForHistory.isRed) {
            for (counter: Int in position until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                if (SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalRed != 0) {
                    setNewHistory(counter, SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalRed - 1, SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalBlue,
                        resultForHistory.goalGeterId, resultForHistory.assisterId)
                }
            }
        } else {
            for (counter: Int in position until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                if (SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalBlue != 0) {
                    setNewHistory(counter, SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalRed, SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalBlue - 1,
                        resultForHistory.goalGeterId, resultForHistory.assisterId)
                }
            }
        }
    }

    private fun setNewHistory(counter: Int, goalRed: Int, goalBlue: Int, goalgetterId: Int, assiterId: Int){
        val newItem = History(
            goalRed, goalBlue, SubmitTeamsActivity.adapterHistory.listOfResults[counter].assisst,
            SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalGeter,
            SubmitTeamsActivity.adapterHistory.listOfResults[counter].isRed,
            SubmitTeamsActivity.adapterHistory.listOfResults[counter].id,
            SubmitTeamsActivity.adapterHistory.listOfResults[counter].autoGoal,
            goalgetterId, assiterId
        )
        SubmitTeamsActivity.adapterHistory.listOfResults[counter] = newItem
        // DatabaseMethodsRunningGame().updatePlayerToDatabase(newItem, requireActivity)
    }
}