package com.algebra.soccernewtry.team.random.randompicking

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemRedTeamGenerateBinding
import com.algebra.soccernewtry.player.model.Player

class RandomRedTeamAdapter: RecyclerView.Adapter<RandomRedTeamAdapter.RandomRedTeamViewHolder>() {

    private val listOfRedTeamPlayers = mutableListOf<Player>()
    var listenerReplacement: ListenerForReplacement? = null

    fun setList(list: List<Player>){
        listOfRedTeamPlayers.clear()
        listOfRedTeamPlayers.addAll(list)
        notifyDataSetChanged()
    }

    fun replacePlayerRedTeam(position: Int, player: Player){
        listOfRedTeamPlayers[position] = player
        notifyDataSetChanged()
    }

    inner class RandomRedTeamViewHolder(private val itemRed: ItemRedTeamGenerateBinding): RecyclerView.ViewHolder(itemRed.root){

        init{
            itemRed.replaceItems.setOnClickListener {
                listenerReplacement?.getReplacement(layoutPosition)
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(player1: Player){
            itemRed.player1.text = player1.name
            if(player1.name == RandomMakeTeam.lastPlayer.name){
                itemRed.replaceItems.visibility = View.GONE
                itemRed.layoutBottom.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = listOfRedTeamPlayers.size

    override fun onBindViewHolder(holder: RandomRedTeamViewHolder, position: Int) {
        holder.bind(listOfRedTeamPlayers[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomRedTeamViewHolder {
        val binding = ItemRedTeamGenerateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RandomRedTeamViewHolder(binding)
    }

    interface ListenerForReplacement{
        fun getReplacement(position: Int)
    }
}