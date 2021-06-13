package com.algebra.fuca.display.efficiency

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemResultBinding
import com.algebra.soccernewtry.display.achievement.PlayerStat

class EfficiencyAdapter(val numOfAllMatches: Int) : RecyclerView.Adapter<EfficiencyAdapter.EfficiencyViewHolder>() {

    private var listOfEfficieny = mutableListOf<PlayerStat>()

    fun setEfficiencyList(list: List<PlayerStat>){
        listOfEfficieny.clear()
        listOfEfficieny.addAll(list)
        listOfEfficieny.sortBy {
            it.name.toLowerCase()
        }
        listOfEfficieny.sortWith(compareByDescending<PlayerStat> {
            it.numberOfGoal*2 + it.asistent*2 + it.wins*3 + it.draw + it.bonusPoints
        })

        notifyDataSetChanged()
    }

    private fun getPlayerWithBestScore(): List<PlayerStat>{
        var maxScore = 0
        listOfEfficieny.forEach {
            if(it.numberOfGoal*2 + it.asistent*2 + it.wins*3 + it.draw + it.bonusPoints > maxScore)
                maxScore = it.numberOfGoal*2 + it.asistent*2 + it.wins*3 + it.draw + it.bonusPoints
        }
        val listOfPlayerWithBestScore = listOfEfficieny.filter {
            maxScore == it.numberOfGoal*2 + it.asistent*2 + it.wins*3 + it.draw + it.bonusPoints
        }
        return if(listOfPlayerWithBestScore.size == 1)
            listOfPlayerWithBestScore
        else {
            var minArrivals = listOfPlayerWithBestScore[0].attendance
            listOfPlayerWithBestScore.forEach {
                if(it.attendance < minArrivals) minArrivals = it.attendance
            }
            val averageScore = maxScore.toDouble() / minArrivals
            listOfPlayerWithBestScore.filter {
                (it.numberOfGoal*2 + it.asistent*2 + it.wins*3 + it.draw + it.bonusPoints).toDouble()/it.attendance == averageScore
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun getPlayerWithBestAttendance(): List<PlayerStat>{
        var maxAttendance = 0
        listOfEfficieny.forEach {
            if(it.attendance > maxAttendance) maxAttendance = it.attendance
        }
        if(maxAttendance == 0) return emptyList()
        return listOfEfficieny.filter{
            it.attendance == maxAttendance
        }
    }

    inner class EfficiencyViewHolder(val itemEfficiency: ItemResultBinding): RecyclerView.ViewHolder(itemEfficiency.root){
        fun bind(player: PlayerStat){

            val scoreValue = player.numberOfGoal*2 + player.asistent*2 + player.wins*3 + player.draw + player.bonusPoints
            itemEfficiency.tvPlayer.text = player.name
            itemEfficiency.tvWins.text = scoreValue.toString()
            itemEfficiency.tvLoses.text = player.bonusPoints.toString()
            itemEfficiency.tvDraw.text = player.attendance.toString()

           // val getAllGames = DatabaseMethodsHistoryOfGame().getAllHistoryOfGame(requireActivity).size
            //dodati ukupan broj odigranih gameova

            var average = (scoreValue.toDouble() / player.attendance)
            if(numOfAllMatches == 0) average = 0.0
            val averageRound = Math.round(average * 100.0) / 100.0

            if(averageRound % 1.0 > 0){
                var gettingValueAfterDecimalPoint = averageRound.toString()
                gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
                if(gettingValueAfterDecimalPoint.length == 1){
                    itemEfficiency.tvAveriageWins.text = "${averageRound}0"
                }else if(scoreValue != 0 && numOfAllMatches != 0){
                    itemEfficiency.tvAveriageWins.text = "$averageRound"
                }
                else itemEfficiency.tvAveriageWins.text = "0.00"
            } else itemEfficiency.tvAveriageWins.text = "${averageRound.toInt()}.00"

            if(player.bonusPoints > 0 && player.attendance == 0) itemEfficiency.tvAveriageWins.text = "${player.bonusPoints}"

            var averageArrivals = (player.attendance.toDouble() / numOfAllMatches) * 100.0
            if(numOfAllMatches == 0) averageArrivals = 0.0
            val averageRoundArriavals = Math.round(averageArrivals * 100.0) / 100.0

            if(averageRoundArriavals % 1.0 > 0){
                var gettingValueAfterDecimalPoint = averageRoundArriavals.toString()
                gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
                if(gettingValueAfterDecimalPoint.length == 1){
                    itemEfficiency.tvAverageDraw.text = "${averageRoundArriavals}0%"
                } else if(player.attendance != 0 && numOfAllMatches != 0)
                    itemEfficiency.tvAverageDraw.text = "$averageRoundArriavals%"
                else itemEfficiency.tvAverageDraw.text = "0%"
            } else itemEfficiency.tvAverageDraw.text = "${averageRoundArriavals.toInt()}%"

            var averageBonusPoints = (player.bonusPoints.toDouble() / scoreValue) * 100.0
            if(scoreValue == 0) averageBonusPoints = 0.0
            val averageBonusPointsRound = Math.round(averageBonusPoints * 100.0) / 100.0

            if(averageBonusPointsRound % 1.0 > 0){
                var gettingValueAfterDecimalPoint = averageBonusPointsRound.toString()
                gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
                if(gettingValueAfterDecimalPoint.length == 1){
                    itemEfficiency.tvAverageLoses.text = "${averageBonusPointsRound}0%"
                } else if(player.bonusPoints != 0 && numOfAllMatches != 0)
                    itemEfficiency.tvAverageLoses.text = "$averageBonusPointsRound%"
                else itemEfficiency.tvAverageLoses.text = "0%"
            } else itemEfficiency.tvAverageLoses.text = "${averageBonusPointsRound.toInt()}%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EfficiencyViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EfficiencyViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfEfficieny.size

    override fun onBindViewHolder(holder: EfficiencyViewHolder, position: Int) {
        val getAllPlayersWithBestScore = getPlayerWithBestScore()
        getAllPlayersWithBestScore.forEach {
            if(it.id == listOfEfficieny[position].id) holder.itemEfficiency.tvWins.setTextColor(Color.RED)
        }
        val getAllPlayersWithMaxAttendance = getPlayerWithBestAttendance()
        getAllPlayersWithMaxAttendance.forEach {
            if(it.id == listOfEfficieny[position].id) holder.itemEfficiency.tvDraw.setTextColor(Color.RED)
        }
        holder.bind(listOfEfficieny[position])
    }
}