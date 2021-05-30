package com.algebra.fuca.player.adding

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.ItemPlayerBinding
import com.algebra.soccernewtry.player.model.Player

class PlayerAdapter(): RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    var listener: Listener? = null

    fun setList(list: List<Player>){
        listOfPlayers.clear()
        listOfPlayers.addAll(list)
        listOfPlayers.sortBy {
            it.name.toLowerCase()
        }
        notifyDataSetChanged()
    }

    fun add(player: Player){
        listOfPlayers.add(player)
        listOfPlayers.sortBy {
            it.name.toLowerCase()
        }
        notifyDataSetChanged()
    }

    fun update(player: Player){
        var position = 0
        listOfPlayers.forEach {
            if(it.id == player.id){
                listOfPlayers[position] = player
            }
            position++
        }
        listOfPlayers.sortBy {
            it.name.toLowerCase()
        }
        notifyDataSetChanged()
    }

    fun remove(player: Player){
        var position = 0
        for(counter: Int in 0 until listOfPlayers.size){
            if(listOfPlayers[counter].id == player.id) position = counter
        }
        listOfPlayers.removeAt(position)
        notifyDataSetChanged()
    }

    inner class PlayerViewHolder(private val itemPlayer: ItemPlayerBinding): RecyclerView.ViewHolder(itemPlayer.root){

        init{
            itemPlayer.moreOptionsAboutPlayer.setOnClickListener {
                val popMenu = PopupMenu(itemView.context, itemView, Gravity.END)
                popMenu.inflate(R.menu.menu_more_options_players)
                popMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuDeletePlayer -> listener?.deletePlayer(listOfPlayers[layoutPosition])
                        R.id.menuinformationAboutPlayer -> listener?.getInformationAboutPlayer(listOfPlayers[layoutPosition])
                        R.id.menuEditPlayer -> listener?.getUpdatePlayer(listOfPlayers[layoutPosition])
                    }
                    return@setOnMenuItemClickListener true
                }
                popMenu.show()
            }
        }

        fun bind(player: Player){
            itemPlayer.tvPlayer.text = player.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun deletePlayer(player: Player)
        fun getInformationAboutPlayer(player: Player)
        fun getUpdatePlayer(player: Player)
    }

    companion object{
        val listOfPlayers = mutableListOf<Player>()
    }

}