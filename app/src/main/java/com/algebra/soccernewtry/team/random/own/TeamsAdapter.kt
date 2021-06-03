package com.algebra.soccernewtry.team.random.own

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemYourOwnTeamBinding
import com.algebra.soccernewtry.player.model.Player

class TeamsAdapter: RecyclerView.Adapter<TeamsAdapter.YourOwnTeamViewHolder>(){

    private val listOfChoosenPlayers = mutableListOf<Player>()
    var listenerForChoosenTeam: ListenerForCheckBox?= null

    fun setList(list: List<Player>){
        listOfChoosenPlayers.clear()
        listOfChoosenPlayers.addAll(list)
        listOfChoosenPlayers.sortBy {
            it.name.toLowerCase()
        }
        notifyDataSetChanged()
    }

    fun getListOfPlayers(): List<Player> = listOfChoosenPlayers

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class YourOwnTeamViewHolder(private val item_Players: ItemYourOwnTeamBinding): RecyclerView.ViewHolder(item_Players.root){
        init{
            item_Players.cbSelectRedPlayer.setOnCheckedChangeListener { buttonView, isChecked ->
                listenerForChoosenTeam?.chooseRedTeam(isChecked, listOfChoosenPlayers[layoutPosition])
                if(isChecked && item_Players.cbSelectBluePlayer.isChecked) item_Players.cbSelectBluePlayer.isChecked = false
            }
            item_Players.cbSelectBluePlayer.setOnCheckedChangeListener { buttonView, isChecked ->
                listenerForChoosenTeam?.chooseBlueTeam(isChecked, listOfChoosenPlayers[layoutPosition])
                if(isChecked && item_Players.cbSelectRedPlayer.isChecked) item_Players.cbSelectRedPlayer.isChecked = false
            }
        }

        fun bind(player: Player){
            Log.d("ISPISTeamId2", "${player.name} ${player.teamId}")
            item_Players.tvPlayer.text = player.name
            if(player.teamId == 1){
                item_Players.cbSelectRedPlayer.isChecked = true
            } else if(player.teamId == 2)  {
                item_Players.cbSelectBluePlayer.isChecked = true
            } else {
                item_Players.cbSelectRedPlayer.isChecked = false
                item_Players.cbSelectBluePlayer.isChecked = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourOwnTeamViewHolder {
        val binding = ItemYourOwnTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YourOwnTeamViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfChoosenPlayers.size

    override fun onBindViewHolder(holder: YourOwnTeamViewHolder, position: Int) {
        holder.bind(listOfChoosenPlayers[position])
    }

    interface ListenerForCheckBox{
        fun chooseRedTeam(IsCheck: Boolean, player: Player)
        fun chooseBlueTeam(IsCheck: Boolean, player: Player)
    }
}