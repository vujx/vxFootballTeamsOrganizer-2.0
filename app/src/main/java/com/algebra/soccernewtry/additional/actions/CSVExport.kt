package com.algebra.soccernewtry.additional.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.algebra.soccernewtry.display.achievement.PlayerStat
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.player.main.PlayerViewModel
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder

class CSVExport(private val viewModelPlayer: PlayerViewModel, private val viewModelMatches: MatchViewModel, private val activity: MoreActionsActivity) {

    fun createCSVValues(){
        val listForStatRealisation = mutableListOf<PlayerStat>()
        activity.lifecycleScope.launchWhenResumed {
            val allPlayers = viewModelPlayer.getAllPlayersForStat()
            allPlayers.filter {
                it.isDeleted == 0
            }.forEach { player ->
                val playerSpec = viewModelPlayer.getAllPlayersSpecification(player.id)
                var wins = 0
                var loses = 0
                var draw = 0
                viewModelPlayer.getPlayersMatches(player.id).forEach {
                    val getMatchResultRed = viewModelPlayer.getMatchResult(it.matchId)
                    val getMatchResultBlue = viewModelPlayer.getResultBlueTeam(it.matchId)

                    if(getMatchResultRed.teamGoals > getMatchResultBlue.teamGoals && it.teamId == 1) wins++
                    if(getMatchResultRed.teamGoals == getMatchResultBlue.teamGoals) draw++
                    if(getMatchResultRed.teamGoals < getMatchResultBlue.teamGoals && it.teamId == 1) loses++

                    if(getMatchResultRed.teamGoals < getMatchResultBlue.teamGoals && it.teamId == 2) wins++
                    if(getMatchResultRed.teamGoals > getMatchResultBlue.teamGoals && it.teamId == 2) loses++
                }

                val numOfGoals = viewModelPlayer.getNumberOfGoals(player.id)
                val numOfAss = viewModelPlayer.getNumberOfAssist(player.id)
                val numOfAutogoals = viewModelPlayer.getNumberOfAutogoals(player.id)
                listForStatRealisation.add(PlayerStat(player.id, playerSpec.name, wins, loses, draw, numOfGoals, numOfAss, numOfAutogoals, playerSpec.attendance, 0))
            }
            val values = StringBuilder("")
            values.append("name, wins, percentage of wins,loses, percentage of loses,draws, percentage of draws, goals, percantage of goals, assists, percantage of assists,autogoals, " +
                    "percantage of autogoals, score, percantage of score, bonus points, percantage of bonus points, arriavals, percantage of arriavals")
            listForStatRealisation.sortBy {
                it.name
            }
            val getAllGames = viewModelMatches.getAllMatchesCourtine().size
            listForStatRealisation.forEach {
                values.append("\n${it.name}, ${it.wins}, ${getPercentage(it.wins, it.attendance)}, " +
                        "${it.loses}, ${getPercentage(it.loses, it.attendance)}, ${it.draw}, ${getPercentage(it.draw, it.attendance)}, " +
                        "${it.numberOfGoal}, ${getAverage(it.numberOfGoal, it.attendance)}, " +
                        "${it.loses}, ${getAverage(it.loses, it.attendance)}, " +
                        "${it.draw}, ${getAverage(it.draw, it.attendance)}, " +
                        "${it.wins*3 + it.draw + it.numberOfGoal*2 + it.asistent*2}, ${getAverage(it.wins*3 + it.draw + it.numberOfGoal*2 + it.asistent*2, getAllGames)}, " +
                        "${it.bonusPoints}, ${getPercantageBonusPoints(it, (it.wins*3 + it.draw + it.numberOfGoal*2 + it.asistent*2), getAllGames)}, " +
                        "${it.attendance}, ${getPercentage(it.attendance, getAllGames)}")
            }

            try{
                val out: FileOutputStream = activity.applicationContext.openFileOutput("datafootball.csv", Context.MODE_PRIVATE)
                out.write((values.toString()).toByteArray())
                out.close()

                val fileLocation = File(activity.applicationContext.filesDir, "datafootball.csv")
                val path: Uri = FileProvider.getUriForFile(activity.applicationContext, "com.algebra.soccernewtry.fileprovider", fileLocation)
                val fileIntent = Intent(Intent.ACTION_SEND)
                fileIntent.setType("text/csv")
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data")
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                fileIntent.putExtra(Intent.EXTRA_STREAM, path)
                activity.startActivity(Intent.createChooser(fileIntent, "Send mail"))
            } catch(e: Throwable){

            }
        }
    }


    private fun getPercentage(valueToCheck: Int, attendance: Int): String{
        var getAverage = ""
        var average = (valueToCheck.toDouble() / attendance.toDouble()) * 100.0
        if(attendance == 0) average = 0.0
        val averageRound = Math.round(average * 100.0) / 100.0

        if(averageRound % 1.0 > 0){
            if(valueToCheck != 0 && attendance != 0)
                getAverage = "$averageRound%"
            else getAverage = "0%"
        } else getAverage = "${averageRound.toInt()}%"

        return getAverage
    }

    private fun getAverage(valueToCheck: Int, attendance: Int): String{
        var getAverage = ""

        var average = (valueToCheck.toDouble() / attendance.toDouble())
        if(attendance == 0) average = 0.0
        val averageRound = Math.round(average * 100.0) / 100.0

        if(averageRound % 1.0 > 0){
            var gettingValueAfterDecimalPoint = averageRound.toString()
            gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
            if(gettingValueAfterDecimalPoint.length == 1){
                getAverage = "${averageRound}0"
            }else if(valueToCheck != 0 && attendance != 0)
                getAverage = "$averageRound"
            else getAverage = "0.00"
        } else getAverage = "${averageRound.toInt()}.00"

        return getAverage
    }

    private fun getPercantageBonusPoints(player: PlayerStat, scoreValue: Int, getAllGames: Int): String{
        var getAverage = ""

        var averageBonusPoints = (player.bonusPoints.toDouble() / scoreValue) * 100.0
        if(scoreValue == 0) averageBonusPoints = 0.0
        val averageBonusPointsRound = Math.round(averageBonusPoints * 100.0) / 100.0

        if(averageBonusPointsRound % 1.0 > 0){
            var gettingValueAfterDecimalPoint = averageBonusPointsRound.toString()
            gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
            if(gettingValueAfterDecimalPoint.length == 1){
                getAverage = "${averageBonusPointsRound}0%"
            } else if(player.bonusPoints != 0 && getAllGames != 0)
                getAverage = "$averageBonusPointsRound%"
            else getAverage = "0.00%"
        } else getAverage = "${averageBonusPointsRound.toInt()}.00%"

        return getAverage
    }
}