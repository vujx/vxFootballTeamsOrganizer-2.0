package com.algebra.fuca.game.teams

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemAssistGoalsBinding
import com.algebra.soccernewtry.game.PlayerCheck
import com.algebra.soccernewtry.player.model.Player

class SubmitTeamsAdapter: RecyclerView.Adapter<SubmitTeamsAdapter.SubmitTeamsViewHolder>() {

    val listOfTeam = mutableListOf<PlayerCheck>()
    var listenerForGoalsAndAssist: ListenerForGoalAndAssist? = null


    fun setList(list: List<PlayerCheck>){
        listOfTeam.clear()
        listOfTeam.addAll(list)
        listOfTeam.sortBy {
            it.name.toLowerCase()
        }
        try{
            notifyDataSetChanged()
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class SubmitTeamsViewHolder(private val itemSubmitTeam: ItemAssistGoalsBinding): RecyclerView.ViewHolder(itemSubmitTeam.root){
        init {
            itemSubmitTeam.cbAssist.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    listOfTeam.forEach {
                        it.isAssist = false
                        it.isAutoGol = false
                    }
                    listOfTeam[layoutPosition].isAssist = isChecked
                    if(isChecked) {
                        listOfTeam[layoutPosition].isGoalgeter = false
                        listOfTeam[layoutPosition].isAutoGol = false
                    }
                    listenerForGoalsAndAssist?.getAssist(listOfTeam[layoutPosition])
                } else listOfTeam[layoutPosition].isAssist = isChecked

            }
            itemSubmitTeam.cbGoal.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    listOfTeam.forEach {
                        it.isGoalgeter = false
                        it.isAutoGol = false
                    }
                    if(isChecked) {
                        listOfTeam[layoutPosition].isAssist = false
                        listOfTeam[layoutPosition].isAutoGol = false
                    }
                    listOfTeam[layoutPosition].isGoalgeter = isChecked
                    listenerForGoalsAndAssist?.getGoal(listOfTeam[layoutPosition])
                } else {
                    listOfTeam[layoutPosition].isGoalgeter = isChecked
                }
            }
            itemSubmitTeam.cbAutoGoal.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    listOfTeam.forEach {
                        it.isGoalgeter = false
                        it.isAssist = false
                        it.isAutoGol = false
                    }
                    if(isChecked) {
                        listOfTeam[layoutPosition].isAssist = false
                        listOfTeam[layoutPosition].isGoalgeter = false
                    }
                    listOfTeam[layoutPosition].isAutoGol = isChecked
                    listenerForGoalsAndAssist?.getAutogoal(listOfTeam[layoutPosition])
                } else {
                    listOfTeam[layoutPosition].isAutoGol = isChecked
                }
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(player: PlayerCheck){
            itemSubmitTeam.players.text = player.name
            itemSubmitTeam.cbAssist.isChecked = player.isAssist
            itemSubmitTeam.cbGoal.isChecked = player.isGoalgeter
            itemSubmitTeam.cbAutoGoal.isChecked = player.isAutoGol
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmitTeamsViewHolder {
        val binding = ItemAssistGoalsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubmitTeamsViewHolder(binding)
    }


    override fun getItemCount(): Int = listOfTeam.size

    override fun onBindViewHolder(holder: SubmitTeamsViewHolder, position: Int) {
        holder.bind(listOfTeam[position])

    }

    interface ListenerForGoalAndAssist{
        fun getGoal(item: PlayerCheck)
        fun getAssist(item: PlayerCheck)
        fun getAutogoal(item: PlayerCheck)
    }
}