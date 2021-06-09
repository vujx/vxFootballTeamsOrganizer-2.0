package com.algebra.fuca.display.history.historyOfPlayers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemHistoryOfPlayersBinding
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.model.PlayerMatchScore

class HistoryPlayerAdapter: RecyclerView.Adapter<HistoryPlayerAdapter.HistoryPlayerViewHolder>() {

    private val listOfPlayers = mutableListOf<PlayerMatchScore>()

    fun setList(list: List<PlayerMatchScore>){
        listOfPlayers.clear()
        listOfPlayers.addAll(list)
        listOfPlayers.sortBy {
            it.name?.toLowerCase()
        }
        notifyDataSetChanged()
    }

    inner class HistoryPlayerViewHolder(private val item: ItemHistoryOfPlayersBinding): RecyclerView.ViewHolder(item.root){

        fun bind(player: PlayerMatchScore){
            item.tvGoal.text = player.goals.toString()
            item.tvAutoGoal.text = "(${player.owngoals})"
            item.tvAss.text = player.assists.toString()
            item.tvName.text = player.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPlayerViewHolder {
        val binding = ItemHistoryOfPlayersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryPlayerViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfPlayers.size

    override fun onBindViewHolder(holder: HistoryPlayerViewHolder, position: Int) {
        holder.bind(listOfPlayers[position])
    }
}