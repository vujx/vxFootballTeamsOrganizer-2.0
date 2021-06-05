package com.algebra.fuca.team.random

import android.util.Log
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.team.random.randompicking.GeneratedRandomTeamsActivity
import com.algebra.soccernewtry.team.random.randompicking.RandomMakeTeam

class FixPlayers {

    private val listOfFixPlayers = mutableListOf<Any>()

    fun addingFixPlayersToTeam(randomTeams: RandomMakeTeam, isItFixList: Boolean){
        var isPlayer = true
        for(counter: Int in 0 until listOfFixPlayers.size){
            if(isPlayer && listOfFixPlayers[counter] is Player && !isItFixList){
                randomTeams.sumaCrvenih += ((listOfFixPlayers[counter] as Player).defense*0.6 + (listOfFixPlayers[counter] as Player).agility*1.3 +
                        (listOfFixPlayers[counter] as Player).technique*1.1) / 30.0
                isPlayer = false
            } else if(listOfFixPlayers[counter] is Player && !isItFixList){
                randomTeams.sumaPlavih += ((listOfFixPlayers[counter] as Player).defense*0.6 + (listOfFixPlayers[counter] as Player).agility*1.3 +
                        (listOfFixPlayers[counter] as Player).technique*1.1) / 30.0
                isPlayer = true
            }
        }
        Log.d("FIXLIS", listOfFixPlayers.toString())
    }

    fun checkIfFixRowWantToReplacePlayers(position: Int){
        var indexOfReplacePlayers = -1
        for(counter: Int in 0 until listOfFixPlayers.size){
            if(listOfFixPlayers[counter] is Int){
                if(position == listOfFixPlayers[counter]){
                    indexOfReplacePlayers = counter
                }
            }
        }
        if(indexOfReplacePlayers != -1){
            val redTeamPlayer = listOfFixPlayers[indexOfReplacePlayers - 1]
            val blueTeamPlayer = listOfFixPlayers[indexOfReplacePlayers + 1]
            listOfFixPlayers[indexOfReplacePlayers - 1] = blueTeamPlayer
            listOfFixPlayers[indexOfReplacePlayers + 1] = redTeamPlayer
        }
    }

    fun addFixPlayerAfterCheck(position: Int){
        listOfFixPlayers.add(GeneratedRandomTeamsActivity.redTeam[position])
        listOfFixPlayers.add(position)
        listOfFixPlayers.add(GeneratedRandomTeamsActivity.blueTeam[position])
        Log.d("AFTERCLICK", listOfFixPlayers.toString())
    }

    fun removeFixPlayerAfterUncheck(removePosition: Int){
        for(counter: Int in removePosition until removePosition + 2){
            listOfFixPlayers.removeAt(removePosition)
        }
    }

    fun positionToRemoveFixPlayers(position: Int): Int{
        var removePosition = 0
        for (counter: Int in 0 until listOfFixPlayers.size) {
            if (listOfFixPlayers[counter] is Player) {
                if (GeneratedRandomTeamsActivity.redTeam[position].id == (listOfFixPlayers[counter] as Player).id) {
                    removePosition = counter
                }
            }
        }
        return removePosition
    }

    fun getPositionOfPlayersInTeamAndAddToTeam(){
        if(listOfFixPlayers.isNotEmpty()){
            val listOfIndex = mutableListOf<Int>()
            listOfFixPlayers.filterIsInstance<Int>().forEach {
                listOfIndex.add(it)
            }
            listOfIndex.sortBy {
                it
            }
            addFixPlayersAfterSortingList(createAndAddToNewSortFixList(listOfIndex))
        }
    }

    private fun createAndAddToNewSortFixList(listOfIndex: List<Int>): List<Any>{
        val newFixList =  mutableListOf<Any>()
        for(element in listOfIndex){
            val index = listOfFixPlayers.indexOf(element)
            newFixList.add(listOfFixPlayers[index - 1])
            newFixList.add(listOfFixPlayers[index])
            newFixList.add(listOfFixPlayers[index +1])
        }
        return newFixList
    }

    private fun addFixPlayersAfterSortingList(newFixList: List<Any>){
        Log.d("IspisMainFix", newFixList.toString())
        Log.d("ispisSizeRed", GeneratedRandomTeamsActivity.redTeam.size.toString())
        for(counter: Int in newFixList.indices){
            if(counter % 3 == 0){
                GeneratedRandomTeamsActivity.redTeam.add(newFixList[counter + 1] as Int, newFixList[counter] as Player)
                GeneratedRandomTeamsActivity.blueTeam.add(newFixList[counter + 1] as Int, newFixList[counter + 2] as Player)
            }
        }
    }
}