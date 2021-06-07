package com.algebra.soccernewtry.additional.actions

import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.player.main.PlayerViewModel
import java.lang.StringBuilder

class InsertValues(private val viewModelPlayer: PlayerViewModel, private val viewModelMatchFlow: MatchFlowViewModel,
                    private val viewModelMatch: MatchViewModel, private val viewModelMatchPlayer: MatchPlayerViewModel) {


    private suspend fun generatePlayersDatabase(): String{
        val listOfPlayers = viewModelPlayer.getAllPlayersForStat()
        val insertValue = StringBuilder()
        insertValue.append("INSERT INTO AllPlayers (id, name, defense, agility, technique, isPlaying, teamId, bonusPoints, isDeleted) VALUES\n")

        for(i: Int in listOfPlayers.indices){
            if(i != listOfPlayers.size - 1)
                insertValue.append("(${listOfPlayers[i].id}, '${listOfPlayers[i].name}', ${listOfPlayers[i].defense}, ${listOfPlayers[i].agility}, ${listOfPlayers[i].technique}, " +
                        "${listOfPlayers[i].isPlaying}, ${listOfPlayers[i].teamId}, ${listOfPlayers[i].bonusPoints}, ${listOfPlayers[i].isDeleted}),\n")
            else
                insertValue.append("(${listOfPlayers[i].id}, '${listOfPlayers[i].name}', ${listOfPlayers[i].defense}, ${listOfPlayers[i].agility}, ${listOfPlayers[i].technique}, " +
                        "${listOfPlayers[i].isPlaying}, ${listOfPlayers[i].teamId}, ${listOfPlayers[i].bonusPoints}, ${listOfPlayers[i].isDeleted})")
        }
        insertValue.append(";\n")

        return insertValue.toString()
    }

    private suspend fun getMatchFlow(): String{
        val insertValues = StringBuilder()
        insertValues.append("INSERT INTO MatchFlow (id, matchId, goalgetterId, assisterId, teamId, isAutoGoal) VALUES\n")
        val getAllMatchFlow = viewModelMatchFlow.getAll()

        for(i: Int in getAllMatchFlow.indices){
            if(i != getAllMatchFlow.size - 1)
                insertValues.append("(${getAllMatchFlow[i].id}, ${getAllMatchFlow[i].matchId}, ${getAllMatchFlow[i].goalgetterId}, ${getAllMatchFlow[i].assisterId}, " +
                        "${getAllMatchFlow[i].teamId}, ${getAllMatchFlow[i].isAutoGoal}),\n")
            else
                insertValues.append("(${getAllMatchFlow[i].id}, ${getAllMatchFlow[i].matchId}, ${getAllMatchFlow[i].goalgetterId}, ${getAllMatchFlow[i].assisterId}, " +
                        "${getAllMatchFlow[i].teamId}, ${getAllMatchFlow[i].isAutoGoal})")
        }
        insertValues.append(";\n")

        return insertValues.toString()
    }

    private suspend fun getMatchHistory(): String{
        val insertValues = StringBuilder()
        insertValues.append("INSERT INTO matches (id, name) VALUES\n")

        val getAllMatches = viewModelMatch.getAllMatchesCourtine()
        for(i: Int in getAllMatches.indices){
            if(i != getAllMatches.size - 1)
                insertValues.append("(${getAllMatches[i].id}, '${getAllMatches[i].date}'),\n")
            else
                insertValues.append("(${getAllMatches[i].id}, '${getAllMatches[i].date}')")
        }
        insertValues.append(";\n")

        return insertValues.toString()
    }

    private suspend fun getMatchPlayer(): String{
        val insertValues = StringBuilder()
        insertValues.append("INSERT INTO MatchPlayer (id, matchId, playerId, teamId) VALUES\n")

        val list = viewModelMatchPlayer.getAll()
            for(i: Int in list.indices){
                if(i != list.size - 1)
                    insertValues.append("(${list[i].id}, ${list[i].matchId}, ${list[i].playerId}, ${list[i].teamId}),\n")
                else
                    insertValues.append("(${list[i].id}, ${list[i].matchId}, ${list[i].playerId}, ${list[i].teamId})")
            }
        insertValues.append(";")

        return insertValues.toString()
    }

    suspend fun getAllValues(): String{
        return generatePlayersDatabase() + getMatchFlow() + getMatchHistory() + getMatchPlayer()
    }
}