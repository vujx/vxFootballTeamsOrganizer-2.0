package com.algebra.soccernewtry.bonus

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.databinding.ItemBonusBinding

class BonusAdapter(private val context: Context): RecyclerView.Adapter<BonusAdapter.BonusViewHolder>() {

    val listOfPlayer = mutableListOf<PlayerForBonus>()

    fun setList(list: List<PlayerForBonus>){
        listOfPlayer.clear()
        listOfPlayer.addAll(list)
        listOfPlayer.sortBy {
            it.name
        }
        notifyDataSetChanged()
    }

    inner class BonusViewHolder(private val itemBonus: ItemBonusBinding): RecyclerView.ViewHolder(itemBonus.root){

        init{
            itemBonus.addScore.setOnClickListener {
                if(itemBonus.scoreAmount.text.toString().toInt() == 5){
                    val toast = Toast.makeText(context, "You can only add 5 bonus points!", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                } else {
                    itemBonus.scoreAmount.text = "${itemBonus.scoreAmount.text.toString().toInt() + 1}"
                    listOfPlayer[layoutPosition].bonusPoints++
                }
            }

            itemBonus.removeScore.setOnClickListener {
                if(itemBonus.scoreAmount.text.toString().toInt() == -5){
                    val toast = Toast.makeText(context, "You can only substract 5 bonus points!", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                } else {
                    itemBonus.scoreAmount.text = "${itemBonus.scoreAmount.text.toString().toInt() - 1}"
                    listOfPlayer[layoutPosition].bonusPoints--
                }
            }
        }

        fun bind(player: PlayerForBonus){
            itemBonus.tvPlayer.text = player.name
            itemBonus.scoreAmount.text = player.bonusPoints.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val binding = ItemBonusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BonusViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfPlayer.size

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        holder.bind(listOfPlayer[position])
    }
}