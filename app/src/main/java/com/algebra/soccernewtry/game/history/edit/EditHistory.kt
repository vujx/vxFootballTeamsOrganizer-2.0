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

}