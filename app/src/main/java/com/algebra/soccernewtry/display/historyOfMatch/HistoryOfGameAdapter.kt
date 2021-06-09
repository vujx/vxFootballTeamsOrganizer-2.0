package com.algebra.soccernewtry.display.historyOfMatch

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.ItemHistoryOfGameBinding
import com.algebra.soccernewtry.player.model.Player

class HistoryOfGameAdapter(private val activity: HistoryMatchActivity): RecyclerView.Adapter<HistoryOfGameAdapter.HistoryOfGameViewHolder>() {

    private val listOfGames = mutableListOf<HistoryOfGame>()
    var listener: Listener? = null
    var counter = 0

    fun setList(list: List<HistoryOfGame>){
        listOfGames.clear()
        listOfGames.addAll(list)
        listOfGames.sortByDescending {
            it.id
        }
        notifyDataSetChanged()
    }

    inner class HistoryOfGameViewHolder(private val item: ItemHistoryOfGameBinding): RecyclerView.ViewHolder(item.root){
        init{
            item.moreOptionsAboutPlayer.setOnClickListener {
                val popMenu = PopupMenu(itemView.context, itemView, Gravity.END)
                popMenu.inflate(R.menu.menu_history_player_options)
                popMenu.setOnMenuItemClickListener { 
                    when(it.itemId){
                        R.id.menuDetailHistory -> listener?.getDetailsAboutMatch(listOfGames[layoutPosition].id)
                        R.id.menuDeleteHistoryPlayer -> listener?.getDetailsAboutMatch(listOfGames[layoutPosition].id)
                    }
                    return@setOnMenuItemClickListener true
                }
                popMenu.show()
            }
        }
        fun bind(historyOfGame: HistoryOfGame){
            item.tvDate.text = historyOfGame.date
            item.tvRedGoal.text = historyOfGame.goalRed.toString()
            item.tvBlueGoal.text = historyOfGame.goalBlue.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOfGameViewHolder {
        val binding = ItemHistoryOfGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryOfGameViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfGames.size

    override fun onBindViewHolder(holder: HistoryOfGameViewHolder, position: Int) {
        holder.bind(listOfGames[position])
    }

    interface Listener{
        fun getDetailsAboutMatch(id: Int)
        fun deleteMatch(id: Int)
    }
}