package com.algebra.fuca.display.achievement

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemResultBinding
import com.algebra.soccernewtry.display.achievement.PlayerStat
import com.algebra.soccernewtry.player.model.Player

class ResultAdaper: RecyclerView.Adapter<ResultAdaper.ResultViewHolder>() {

    private val listOfResults = mutableListOf<PlayerStat>()

    fun setList(list: List<PlayerStat>){
        listOfResults.clear()
        listOfResults.addAll(list)

        listOfResults.sortBy {
            it.name.toLowerCase()
        }
        listOfResults.sortWith(compareByDescending<PlayerStat> {
            it.wins
        })
        notifyDataSetChanged()
    }

    private fun getlistOfPlayersWithMaxWins(): List<PlayerStat>{
        var maxWins = 0
        listOfResults.forEach {
            if(it.wins > maxWins) maxWins = it.wins
        }
        val getListOfPlayersWithMaxWins = listOfResults.filter {
            it.wins == maxWins
        }
        return if(getListOfPlayersWithMaxWins.size == 1) getListOfPlayersWithMaxWins
        else {
            var minArrivals = getListOfPlayersWithMaxWins[0].attendance
            getListOfPlayersWithMaxWins.forEach {
                if(it.attendance < minArrivals) minArrivals = it.attendance
            }
            val minAverage = maxWins.toDouble()/minArrivals
            getListOfPlayersWithMaxWins.filter {
                minAverage == (it.wins.toDouble()/it.attendance)
            }
        }
    }

    private fun getlistOfPlayersWithMaxLoses(): List<PlayerStat>{
        var maxLoses = 0
        listOfResults.forEach {
            if(it.loses > maxLoses) maxLoses = it.loses
        }
        val getListOfPlayersWithMaxLoses = listOfResults.filter{
            it.loses == maxLoses
        }
        return if(getListOfPlayersWithMaxLoses.size == 1) getListOfPlayersWithMaxLoses
        else {
            var minArrivals = getListOfPlayersWithMaxLoses[0].attendance
            getListOfPlayersWithMaxLoses.forEach {
                if(it.attendance < minArrivals) minArrivals = it.attendance
            }
            val minAverage = maxLoses.toDouble()/minArrivals
            getListOfPlayersWithMaxLoses.filter {
                minAverage == (it.loses.toDouble()/it.attendance)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ResultViewHolder(val itemResult: ItemResultBinding): RecyclerView.ViewHolder(itemResult.root){
        fun bind(player: PlayerStat){
            itemResult.tvPlayer.text = player.name
            itemResult.tvWins.text = player.wins.toString()
            itemResult.tvDraw.text = player.draw.toString()
            itemResult.tvLoses.text = player.loses.toString()

            val average = (player.wins.toDouble() / player.attendance.toDouble()) * 100.0
            val averageRound = Math.round(average * 100.0) / 100.0

            if(averageRound % 1.0 > 0){
                if(player.wins != 0 && player.attendance != 0)
                    itemResult.tvAveriageWins.text = "$averageRound%"
                else itemResult.tvAveriageWins.text = "0%"
            } else itemResult.tvAveriageWins.text = "${averageRound.toInt()}%"

            val averageLoses = (player.loses.toDouble() / player.attendance.toDouble()) * 100.0
            val averageRoundLoses = Math.round(averageLoses * 100.0) / 100.0

            if(averageRoundLoses % 1.0 > 0){
                if(player.loses != 0 && player.attendance != 0)
                    itemResult.tvAverageLoses.text = "$averageRoundLoses%"
                else itemResult.tvAverageLoses.text = "0%"
            } else itemResult.tvAverageLoses.text = "${averageRoundLoses.toInt()}%"

            val averageDraw = (player.draw.toDouble() / player.attendance.toDouble()) * 100.0
            val averageRoundDraw = Math.round(averageDraw * 100.0) / 100.0

            if(averageRoundDraw % 1.0 > 0){
                if(player.draw != 0 && player.attendance != 0)
                    itemResult.tvAverageDraw.text = "$averageRoundDraw%"
                else itemResult.tvAverageDraw.text = "0%"
            } else itemResult.tvAverageDraw.text = "${averageRoundDraw.toInt()}%"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfResults.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(listOfResults[position])
        val listOfPlayersWithMaxWins = getlistOfPlayersWithMaxWins()
        if(listOfPlayersWithMaxWins.isNotEmpty() && listOfPlayersWithMaxWins[0].wins != 0)
        listOfPlayersWithMaxWins.forEach {
            if(it.id == listOfResults[position].id) holder.itemResult.tvWins.setTextColor(Color.RED)
        }
        val listOfPlayersWithMaxLoses = getlistOfPlayersWithMaxLoses()
        if(listOfPlayersWithMaxLoses.isNotEmpty() && listOfPlayersWithMaxLoses[0].loses != 0)
        listOfPlayersWithMaxLoses.forEach {
            if(it.id == listOfResults[position].id) holder.itemResult.tvLoses.setTextColor(Color.RED)
        }
    }
}