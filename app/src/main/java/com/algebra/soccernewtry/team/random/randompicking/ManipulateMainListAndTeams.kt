package com.algebra.soccernewtry.team.random.randompicking

import com.algebra.soccernewtry.player.model.Player

class ManipulateMainListAndTeams(private val selectedPlayer: MutableList<Player>) {

    fun onBackButtonAction(){
        selectedPlayer.clear()
        GeneratedRandomTeamsActivity.redTeam.clear()
        GeneratedRandomTeamsActivity.blueTeam.clear()
    }

    fun replacePlayersTeam(position: Int){
        val playerRed = GeneratedRandomTeamsActivity.redTeam[position]
        val playerBlue = GeneratedRandomTeamsActivity.blueTeam[position]
        GeneratedRandomTeamsActivity.blueTeam[position] = playerRed
        GeneratedRandomTeamsActivity.redTeam[position] = playerBlue
    }

    fun checkSumOfRatingPlayers(randomTeams: RandomMakeTeam){
        GeneratedRandomTeamsActivity.redTeam.forEach {
            randomTeams.sumaCrvenih += (it.defense*0.6 + it.agility*1.3 + it.technique*1.1) / 30.0
        }
        GeneratedRandomTeamsActivity.blueTeam.forEach {
            randomTeams.sumaPlavih += (it.defense*0.6 + it.agility*1.3 + it.technique*1.1) / 30.0
        }
    }

    fun sortTeams(){
        GeneratedRandomTeamsActivity.blueTeam.sortBy {
            (it.defense*0.6 + it.technique*1.1 + it.agility*1.3)
        }
        GeneratedRandomTeamsActivity.redTeam.sortBy {
            (it.defense*0.6 + it.technique*1.1 + it.agility*1.3)
        }
    }

    fun addFixPlayerToMainList(position: Int){
        selectedPlayer.add(GeneratedRandomTeamsActivity.redTeam[position])
        selectedPlayer.add(GeneratedRandomTeamsActivity.blueTeam[position])
    }

    fun getPositionOfPlayerToRemove(position: Int){
        var positionRed = 0
        var positionBlue = 0
        for (counter: Int in 0 until selectedPlayer.size) {
            if (selectedPlayer[counter].id == GeneratedRandomTeamsActivity.redTeam[position].id) positionRed = counter
            if (selectedPlayer[counter].id == GeneratedRandomTeamsActivity.blueTeam[position].id) positionBlue = counter
        }
        removeUncheckFixPlayerFromList(positionRed, positionBlue)
    }

    private fun removeUncheckFixPlayerFromList(positionRed: Int, positionBlue: Int){
        if (selectedPlayer.size != 0) {
            if (positionBlue > positionRed) {
                selectedPlayer.removeAt(positionBlue)
                selectedPlayer.removeAt(positionRed)
            } else {
                selectedPlayer.removeAt(positionRed)
                selectedPlayer.removeAt(positionBlue)
            }
        }
    }
}