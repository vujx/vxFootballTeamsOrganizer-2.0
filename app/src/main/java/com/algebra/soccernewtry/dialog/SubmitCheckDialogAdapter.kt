package com.algebra.soccernewtry.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemSubmitTeamsBinding
import com.algebra.soccernewtry.player.model.Player

class SubmitCheckDialogAdapter: RecyclerView.Adapter<SubmitCheckDialogAdapter.SubmitViewHolder>() {

    private val listOfBlueTeams = mutableListOf<Player>()
    private val listOfRedTeams = mutableListOf<Player>()

    fun setList(list: List<Player>, listBlue: List<Player>){
        listOfBlueTeams.clear()
        listOfRedTeams.clear()

        listOfRedTeams.addAll(list)
        listOfBlueTeams.addAll(listBlue)
        notifyDataSetChanged()
    }
    inner class SubmitViewHolder(private val item: ItemSubmitTeamsBinding): RecyclerView.ViewHolder(item.root){
        fun bind(player1: Player, player2: Player){
            item.redTeamPLayer.text = player1.name
            item.blueTeamPlayer.text = player2.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmitViewHolder {
        val binding = ItemSubmitTeamsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubmitViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(listOfBlueTeams.size > listOfRedTeams.size){
            return listOfBlueTeams.size
        } else return listOfRedTeams.size
    }

    override fun onBindViewHolder(holder: SubmitViewHolder, position: Int) {
        if(listOfRedTeams.size >= position && listOfBlueTeams.size <= position)
            holder.bind(listOfRedTeams[position], Player(0, "", 0, 0, 0, 0,0, 0,0))

        else if(listOfRedTeams.size <= position && listOfBlueTeams.size >= position)
            holder.bind(Player(0, "", 0, 0, 0,0 ,0 ,0 ,0), listOfBlueTeams[position])
        else holder.bind(listOfRedTeams[position], listOfBlueTeams[position])
    }
}