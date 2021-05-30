package com.algebra.soccernewtry.team.random.randompicking

import com.algebra.soccernewtry.player.model.Player
import kotlin.math.abs

class RandomMakeTeam(private val selectedPlayer: MutableList<Player>) {

    var sumaCrvenih: Double = 0.0
    var sumaPlavih: Double = 0.0
    private val listOfBestRandomTeam = mutableListOf<Player>()
    var diffrenceFirst: Double = 0.0
    var diffrenceMain = 0.0
    var checkIfItIsOddNum = false

    //formula for making teams
    fun makeTeams() {
        clearDiffrence()
        checkIfItIsOddNum = lastPlayerWhenTheyAreOddNumOfPlayers()
        for (counter: Int in 0..9) {
            countSumOfRatingBlueAndRedTeam()
            checkDifference(counter)
            getToZeroSumOfRatingTeams()
        }
        setMainList(listOfBestRandomTeam)
    }

    private fun clearDiffrence(){
        diffrenceFirst = 0.0
        diffrenceMain = 0.0
    }

    private fun lastPlayerWhenTheyAreOddNumOfPlayers(): Boolean =
        if (selectedPlayer.size % 2 != 0) {
            selectedPlayer.sortBy {
                (it.agility*1.3 + it.defense*0.6 + it.technique*1.1) / 30.0
            }
            lastPlayer = selectedPlayer[0]
            selectedPlayer.removeAt(0)
            true
        } else false

    private fun countSumOfRatingBlueAndRedTeam() {
        clearRedAndBlueTeam()
        selectedPlayer.shuffle()
        for (j: Int in 0 until selectedPlayer.size) {
            if (j % 2 == 0) {
                sumaPlavih += (selectedPlayer[j].defense*0.6 + selectedPlayer[j].agility*1.3
                        + selectedPlayer[j].technique*1.1) / 30.0
            } else {
                sumaCrvenih += (selectedPlayer[j].defense*0.6 + selectedPlayer[j].agility*1.3
                        + selectedPlayer[j].technique*1.1) / 30.0
            }
        }
    }

    private fun clearRedAndBlueTeam(){
        GeneratedRandomTeamsActivity.redTeam.clear()
        GeneratedRandomTeamsActivity.blueTeam.clear()
    }

    private fun checkDifference(counter: Int){
        diffrenceFirst = abs(sumaCrvenih - sumaPlavih)
        if (counter == 0) {
            diffrenceMain = diffrenceFirst
            setListOfBestRandomTeam()
        } else if (diffrenceMain >= diffrenceFirst) {
            setListOfBestRandomTeam()
            diffrenceMain = diffrenceFirst
        }
    }

    private fun setListOfBestRandomTeam(){
        listOfBestRandomTeam.clear()
        listOfBestRandomTeam.addAll(selectedPlayer)
    }

    fun getToZeroSumOfRatingTeams() {
        sumaCrvenih = 0.0
        sumaPlavih = 0.0
    }

    private fun setMainList(listOfBestRandomTeam: List<Player>){
        selectedPlayer.clear()
        selectedPlayer.addAll(listOfBestRandomTeam)
    }

    fun createTeams() {
        for (counter: Int in 0 until selectedPlayer.size) {
            if (counter % 2 == 0) {
                GeneratedRandomTeamsActivity.redTeam.add(selectedPlayer[counter])
            } else {
                GeneratedRandomTeamsActivity.blueTeam.add(selectedPlayer[counter])
            }
        }
    }

    fun addLastPlayerIfOddNumOfPlayers(checkIfItIsOddNum: Boolean) {
        if (checkIfItIsOddNum) {
            selectedPlayer.add(lastPlayer)
            when{
                sumaCrvenih > sumaPlavih -> GeneratedRandomTeamsActivity.blueTeam.add(lastPlayer)
                sumaPlavih > sumaCrvenih -> GeneratedRandomTeamsActivity.redTeam.add(lastPlayer)
                else -> {
                    when ((0..1).random()) {
                        0 -> GeneratedRandomTeamsActivity.redTeam.add(lastPlayer)
                        1 -> GeneratedRandomTeamsActivity.blueTeam.add(lastPlayer)
                    }
                }
            }
        }
    }

    companion object{
        var lastPlayer: Player = Player(-1, "", 0, 0, 0, 0, 0, 0, 0)
    }

}