package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.editHistory

import com.algebra.fuca.game.teams.SubmitTeamsAdapter
import com.algebra.soccernewtry.game.PlayerCheck

class ListOfEditHistoryMatchFlow {

    private fun clearListOfAssAndGoalgetter(it: PlayerCheck){
        it.isGoalgeter = false
        it.isAssist = false
        it.isAutoGol = false
    }

    fun setCheckBoxAfterClickRedTeam(adapter: SubmitTeamsAdapter, adapterBlue: SubmitTeamsAdapter,
                                     list: List<PlayerCheck>, listOfBlue: List<PlayerCheck>){
        adapter.listenerForGoalsAndAssist = object : SubmitTeamsAdapter.ListenerForGoalAndAssist {
            override fun getGoal(item: PlayerCheck) {
                clearBlueListAndSetRedList(adapter, adapterBlue, list, listOfBlue)
            }

            override fun getAssist(item: PlayerCheck) {
                clearBlueListAndSetRedList(adapter, adapterBlue, list, listOfBlue)
            }

            override fun getAutogoal(item: PlayerCheck) {
                clearBlueListAndSetRedList(adapter, adapterBlue, list, listOfBlue)
            }
        }
    }

    private fun clearBlueListAndSetRedList(adapter: SubmitTeamsAdapter, adapterBlue: SubmitTeamsAdapter, list: List<PlayerCheck>, listOfBlue: List<PlayerCheck>){
        adapter.setList(list)
        listOfBlue.forEach {
            clearListOfAssAndGoalgetter(it)
        }
        adapterBlue.setList(listOfBlue)
    }

    fun setCheckBoxAfterClick(adapter: SubmitTeamsAdapter, adapterBlue: SubmitTeamsAdapter, list: List<PlayerCheck>, listOfBlue: List<PlayerCheck>){
        adapterBlue.listenerForGoalsAndAssist = object : SubmitTeamsAdapter.ListenerForGoalAndAssist {
            override fun getGoal(item: PlayerCheck) {
                clearListRedTeamAndSetBlue(adapter, adapterBlue, list, listOfBlue)
            }

            override fun getAssist(item: PlayerCheck) {
                clearListRedTeamAndSetBlue(adapter, adapterBlue, list, listOfBlue)
            }

            override fun getAutogoal(item: PlayerCheck) {
                clearListRedTeamAndSetBlue(adapter, adapterBlue, list, listOfBlue)
            }
        }
    }

    private fun clearListRedTeamAndSetBlue(adapter: SubmitTeamsAdapter, adapterBlue: SubmitTeamsAdapter, list: List<PlayerCheck>, listOfBlue: List<PlayerCheck>){
        adapterBlue.setList(listOfBlue)
        list.forEach {
            clearListOfAssAndGoalgetter(it)
        }
        adapter.setList(list)
    }

    fun checkIfTheSameTeamBlue(listOfBlue: List<PlayerCheck>, oldAutoGoal: String, newAutoGol: String): Boolean{
        var checkIfAutogoalTheSameTEam = false
        var checkOldAutogoal = false

        listOfBlue.forEach {
            if(it.name == oldAutoGoal) checkIfAutogoalTheSameTEam = true
            if(it.name == newAutoGol) checkOldAutogoal = true
        }
        return checkIfAutogoalTheSameTEam && checkOldAutogoal
    }

    fun checkIfTeamsGoalsChanges(isRed: Boolean, list: List<PlayerCheck>, listOfBlue: List<PlayerCheck>): Boolean {
        var newIfRed = true
        if (isRed) {
            list.forEach {
                if (it.isGoalgeter || it.isAutoGol) newIfRed = false
            }
        } else {
            listOfBlue.forEach {
                if (it.isGoalgeter || it.isAutoGol) newIfRed = false
            }
        }
        return newIfRed
    }

    fun checkIfItsTheSameTeam(list: List<PlayerCheck>, oldAutoGoal: String, newAutoGol: String): Boolean{
        var checkIfAutogoalTheSameTEam = false
        var checkOldAutogoal = false
        list.forEach {
            if(it.name == oldAutoGoal) checkIfAutogoalTheSameTEam = true
            if(it.name == newAutoGol) checkOldAutogoal = true
        }
        return checkIfAutogoalTheSameTEam && checkOldAutogoal
    }

}