package com.algebra.soccernewtry.display.realisation

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemResultBinding
import com.algebra.soccernewtry.display.achievement.PlayerStat

class RealisationAdapter: RecyclerView.Adapter<RealisationAdapter.RealisationViewHolder>() {

    private val listOfRealisation = mutableListOf<PlayerStat>()

    fun setListRealisation(list: List<PlayerStat>){
        listOfRealisation.clear()
        listOfRealisation.addAll(list)
        listOfRealisation.sortBy { it.name }
        Log.d("ListaImena", listOfRealisation.toString())
        listOfRealisation.sortWith(compareByDescending<PlayerStat> {
            it.numberOfGoal
        }.thenBy { it.attendance })

        notifyDataSetChanged()
    }

    private fun getBestGoalGetter(): List<PlayerStat>{
        var maxGoals = 0
        listOfRealisation.forEach {
            if(it.numberOfGoal > maxGoals) maxGoals = it.numberOfGoal
        }
        val getListOfBestGoalGetter = listOfRealisation.filter {
            it.numberOfGoal == maxGoals
        }

        return if(getListOfBestGoalGetter.size == 1){
            getListOfBestGoalGetter
        } else {
            var minArrivals = getListOfBestGoalGetter[0].attendance
            getListOfBestGoalGetter.forEach {
                if(it.attendance < minArrivals) minArrivals = it.attendance
            }
            val minAverage = maxGoals.toDouble() / minArrivals
            val getBestGoalGetter = getListOfBestGoalGetter.filter {
                (it.numberOfGoal.toDouble()) / it.attendance == minAverage
            }
            getBestGoalGetter
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun getMaxAssist(): List<PlayerStat>{
        var maxAssist = 0
        listOfRealisation.forEach {
            if(it.asistent > maxAssist) maxAssist = it.asistent
        }
        val getListOfBestAssist = listOfRealisation.filter {
            it.asistent == maxAssist
        }
        var minArrivals = getListOfBestAssist[0].attendance
        if(getListOfBestAssist.size == 1) return getListOfBestAssist
        else {
            getListOfBestAssist.forEach {
                if(it.attendance < minArrivals) minArrivals = it.attendance
            }
            val minAverage = maxAssist.toDouble() / minArrivals
            val bestAssist = getListOfBestAssist.filter {
                (it.asistent.toDouble() / it.attendance) == minAverage
            }
            return bestAssist
        }
    }

    inner class RealisationViewHolder(val itemRealisation: ItemResultBinding): RecyclerView.ViewHolder(itemRealisation.root){

        fun bind(player: PlayerStat){
            itemRealisation.tvPlayer.text = player.name
            itemRealisation.tvLoses.text = player.asistent.toString()
            itemRealisation.tvWins.text = player.numberOfGoal.toString()
            itemRealisation.tvDraw.text = player.autoGoal.toString()

            val average = (player.asistent.toDouble() / player.attendance.toDouble())
            val averageRound = Math.round(average * 100.0) / 100.0

            if(averageRound % 1.0 > 0){
                var gettingValueAfterDecimalPoint = averageRound.toString()
                gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
                if(gettingValueAfterDecimalPoint.length == 1){
                    itemRealisation.tvAverageLoses.text = "${averageRound}0"
                }else if(player.asistent != 0 && player.attendance != 0)
                    itemRealisation.tvAverageLoses.text = "$averageRound"
                else itemRealisation.tvAverageLoses.text = "0.00"
            } else itemRealisation.tvAverageLoses.text = "${averageRound.toInt()}.00"


            val averageGoal = (player.numberOfGoal.toDouble() / player.attendance.toDouble())
            val averageRoundGoal = Math.round(averageGoal * 100.0) / 100.0

            if(averageRoundGoal % 1.0 > 0){
                var gettingValueAfterDecimalPoint = averageRoundGoal.toString()
                gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
                if(gettingValueAfterDecimalPoint.length == 1){
                    itemRealisation.tvAveriageWins.text = "${averageRoundGoal}0"
                }else if(player.numberOfGoal != 0 && player.attendance != 0)
                    itemRealisation.tvAveriageWins.text = "$averageRoundGoal"
                else itemRealisation.tvAveriageWins.text = "0.00"
            } else itemRealisation.tvAveriageWins.text = "${averageRoundGoal.toInt()}.00"

            val averageAutogoal = (player.autoGoal.toDouble() / player.attendance.toDouble())
            val averageRoundAutoGoal = Math.round(averageAutogoal * 100.0) / 100.0

            if(averageRoundAutoGoal % 1.0 > 0){
                var gettingValueAfterDecimalPoint = averageRoundAutoGoal.toString()
                gettingValueAfterDecimalPoint = gettingValueAfterDecimalPoint.substring(gettingValueAfterDecimalPoint.indexOf('.') + 1)
                if(gettingValueAfterDecimalPoint.length == 1){
                    itemRealisation.tvAverageDraw.text = "${averageRoundAutoGoal}0"
                } else if(player.autoGoal != 0 && player.attendance != 0)
                    itemRealisation.tvAverageDraw.text = "$averageRoundAutoGoal"
                else itemRealisation.tvAverageDraw.text = "0.00"
            } else itemRealisation.tvAverageDraw.text = "${averageRoundAutoGoal.toInt()}.00"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealisationViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RealisationViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfRealisation.size

    override fun onBindViewHolder(holder: RealisationViewHolder, position: Int) {
        val bestGoalGetter = getBestGoalGetter()
        val maxAssist = getMaxAssist()
        bestGoalGetter.forEach {
            if(it.id == listOfRealisation[position].id) holder.itemRealisation.tvWins.setTextColor(Color.RED)
        }
        maxAssist.forEach {
            if(it.id == listOfRealisation[position].id)  holder.itemRealisation.tvLoses.setTextColor(Color.RED)
        }
        holder.bind(listOfRealisation[position])
    }
}