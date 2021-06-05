package com.algebra.soccernewtry.game.history

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.ItemHistoryBinding

class HistoryAdapter(): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    var listener: Listener? = null
    val listOfResults = mutableListOf<History>()

    fun setList(list: List<History>){
        listOfResults.clear()
        listOfResults.addAll(list)
        notifyDataSetChanged()
    }

    fun addResult(resultForHistory: History){
        listOfResults.add(resultForHistory)
        notifyDataSetChanged()
    }

    fun editResult(resultForHistory: History){
        listOfResults[positonOfEdit] = resultForHistory
        notifyDataSetChanged()
    }

    fun removeResult(resultForHistory: History, position: Int){
        listOfResults.removeAt(position)
        notifyDataSetChanged()
    }

    fun clearList(){
        listOfResults.clear()
    }

    inner class HistoryViewHolder(private val itemResult: ItemHistoryBinding): RecyclerView.ViewHolder(itemResult.root){
        init {
            itemResult.moreOptionsAboutResult.setOnClickListener {
                val popMenu = PopupMenu(itemView.context, itemView, Gravity.END)
                popMenu.inflate(R.menu.menu_history_options)
                popMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuEditHistory -> {
                            positonOfEdit = layoutPosition
                            listener?.editHistory(listOfResults[layoutPosition])
                        }
                        R.id.menuDeleteHistory -> listener?.deleteHistory(listOfResults[layoutPosition], layoutPosition)
                    }
                    return@setOnMenuItemClickListener true
                }
                popMenu.show()
            }
        }

        fun bind(resultForHistory: History){
            itemResult.tvScore.text = "${resultForHistory.goalRed}:${resultForHistory.goalBlue}"
            if(resultForHistory.goalGeter?.isNotBlank()!! && resultForHistory.assisst?.isNotBlank()!!) {
                itemResult.tvGoalGeter.text = "${resultForHistory.goalGeter}"
                itemResult.tvAssistant.text = "(${resultForHistory.assisst})"
            } else if(resultForHistory.goalGeter.isNotBlank()) {
                itemResult.tvGoalGeter.text = resultForHistory.goalGeter
                itemResult.tvAssistant.text = ""
            }
            else if(resultForHistory.assisst?.isBlank()!! && resultForHistory.goalGeter.isBlank()) {
                itemResult.tvGoalGeter.text = resultForHistory.autoGoal
                itemResult.tvAssistant.text = ""
            }

            if(resultForHistory.goalGeter.isEmpty() && resultForHistory.assisst?.isEmpty()!!) itemResult.contraintLayout.setBackgroundColor(Color.WHITE)
            else if(resultForHistory.isRed) itemResult.contraintLayout.setBackgroundColor(Color.RED)
            else itemResult.contraintLayout.setBackgroundColor(Color.argb(225, 30, 136, 229))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfResults.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listOfResults[position])
    }

    interface Listener{
        fun editHistory(item: History)
        fun deleteHistory(item: History, position: Int)
    }

    companion object{
        var positonOfEdit = 0
    }
}