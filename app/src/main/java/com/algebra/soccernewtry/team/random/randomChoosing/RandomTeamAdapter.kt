package com.algebra.soccernewtry.team.random.randomChoosing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemRandomTeamBinding
import com.algebra.soccernewtry.player.model.Player

class RandomTeamAdapter: RecyclerView.Adapter<RandomTeamAdapter.PlayerViewHolder>() {

    var listener: Listener? = null
    private val listOfPlayers = mutableListOf<Player>()

    fun setList(list: List<Player>){
        listOfPlayers.clear()
        listOfPlayers.addAll(list)
        listOfPlayers.sortBy {
            it.name.toLowerCase()
        }
        notifyDataSetChanged()
    }

    fun getListOfPlayers(): List<Player> = listOfPlayers

    inner class PlayerViewHolder(private val itemPlayer: ItemRandomTeamBinding): RecyclerView.ViewHolder(itemPlayer.root){

        init {
            itemPlayer.cbSelectPlayer.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) {
                    listOfPlayers[layoutPosition].isPlaying = 1
                    listener?.getPlayerCheckToPlay(listOfPlayers[layoutPosition])
                }
                else {
                    listOfPlayers[layoutPosition].isPlaying = 0
                    listener?.getPlayerUncheckToPlay(listOfPlayers[layoutPosition])
                }
            }
        }

        fun bind(player: Player){
            itemPlayer.cbSelectPlayer.isChecked = player.isPlaying == 1
            itemPlayer.tvPlayer.text = player.name

            itemPlayer.enterDefense.text = player.defense.toString()
            itemPlayer.enterAgility.text = player.agility.toString()
            itemPlayer.enterTechicque.text = player.technique.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemRandomTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfPlayers.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(listOfPlayers[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface Listener{
        fun getPlayerCheckToPlay(player: Player)
        fun getPlayerUncheckToPlay(player: Player)
    }

}