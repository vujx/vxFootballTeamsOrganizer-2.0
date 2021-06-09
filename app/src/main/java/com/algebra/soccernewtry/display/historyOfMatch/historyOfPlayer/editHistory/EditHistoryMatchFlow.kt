package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.editHistory

import androidx.fragment.app.FragmentActivity
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.FragmentBlueTeamHistoryOfPlayer
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.FragmentRedTeamHistoryOfPlayers
import com.algebra.soccernewtry.game.history.History

class EditHistoryMatchFlow(private val requireActivity: FragmentActivity) {

    fun editHistory(item: History){
        defaultValue()
        when{
            item.autoGoal?.isNotEmpty()!! -> {
                getAutogoal(item)
                val dialog = EditDialogMatchFlow( item)
                dialog.show(requireActivity.supportFragmentManager, "EDIT HISTORY")
            }
            item.isRed -> {
                getAssAndGoalgetter(item)
                val dialog = EditDialogMatchFlow( item)
                dialog.show(requireActivity.supportFragmentManager, "EDIT HISTORY")
            }
            else -> {
                getAssAndGoalgetter(item)
                val dialog = EditDialogMatchFlow( item)
                dialog.show(requireActivity.supportFragmentManager, "EDIT HISTORY")
            }
        }
    }

    private fun defaultValue(){
        FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.forEach {
            it.isAssist = false
            it.isGoalgeter = false
            it.isAutoGol = false
        }
        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.forEach {
            it.isAutoGol = false
            it.isGoalgeter = false
            it.isAssist = false
        }
    }

    private fun getAssAndGoalgetter(item: History){
        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.forEach {
            if(item.goalGeter == it.name) it.isGoalgeter = true
            if(item.assisst == it.name) it.isAssist = true
        }
        FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.forEach {
            if(item.goalGeter == it.name)  it.isGoalgeter = true
            if(item.assisst == it.name) it.isAssist = true
        }
    }

    private fun getAutogoal(item: History){
        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.forEach {
            if(item.autoGoal == it.name) it.isAutoGol = true
        }
        FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.forEach {
            if(item.autoGoal == it.name) if(item.autoGoal == it.name) it.isAutoGol = true
        }
    }
}