package com.algebra.soccernewtry.team.random.randompicking

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemBlueTeamGenerateBinding
import com.algebra.soccernewtry.player.model.Player

class RandomBlueTeamAdapter: RecyclerView.Adapter<RandomBlueTeamAdapter.RandomViewHolder>() {

    private val listOfTeams = mutableListOf<Player>()
    var listenerForFixing: ListenerForFixing? = null

    fun setTeams(teams: List<Player>){
        listOfTeams.clear()
        listOfTeams.addAll(teams)
        notifyDataSetChanged()
    }

    fun replacePlayerBlueTeam(position: Int, player: Player){
        listOfTeams[position] = player
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return  position
    }



    inner class RandomViewHolder(private val itemPlayer: ItemBlueTeamGenerateBinding): RecyclerView.ViewHolder(itemPlayer.root){

        init{
            itemPlayer.checkBoxForFixingPlayers.setOnCheckedChangeListener { buttonView, isChecked ->
                listenerForFixing?.fixRow(isChecked, layoutPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(player1: Player){
            Log.d("IspisnameBlue", "Ime: ${player1.name}")
            itemPlayer.player1.text = player1.name
            if(player1.name.toLowerCase() == RandomMakeTeam.lastPlayer.name.toLowerCase() || player1.id == -1){
                itemPlayer.checkBoxForFixingPlayers.visibility = View.GONE
                itemPlayer.layoutBottom.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomViewHolder {
        val binding = ItemBlueTeamGenerateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RandomViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfTeams.size

    override fun onBindViewHolder(holder: RandomViewHolder, position: Int) {
        holder.bind(listOfTeams[position])
    }

    interface ListenerForFixing{
        fun fixRow(isChecked: Boolean ,position: Int)
    }

}