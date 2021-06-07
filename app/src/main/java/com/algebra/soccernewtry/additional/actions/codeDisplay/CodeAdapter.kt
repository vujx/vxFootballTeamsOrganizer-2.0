package com.algebra.soccernewtry.additional.actions.codeDisplay

import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.ItemCodeBinding
import com.algebra.soccernewtry.shareCode.model.ShareCode

class CodeAdapter: RecyclerView.Adapter<CodeAdapter.CodeViewHolder>() {

    val listOfCode = mutableListOf<ShareCode>()
    var listener: Listener? = null

    fun setList(list: List<ShareCode>){
        listOfCode.clear()
        listOfCode.addAll(list)
        notifyDataSetChanged()
    }

    fun addCode(code: ShareCode){
        listOfCode.add(code)
        notifyDataSetChanged()
    }

    fun removeCode(code: ShareCode){
        var position = 0
        for(counter: Int in 0 until listOfCode.size){
            if(code.id == listOfCode[counter].id) position = counter
        }
        listOfCode.removeAt(position)
        notifyDataSetChanged()
    }

    inner class CodeViewHolder(private val itemCode: ItemCodeBinding): RecyclerView.ViewHolder(itemCode.root){

        init {
            itemCode.moreOptions.setOnClickListener {
                val popMenu = PopupMenu(itemView.context, itemView,  Gravity.END)
                popMenu.inflate(R.menu.menu_more_options_code)
                popMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuCopyCode ->{
                            listener?.copyCode(listOfCode[layoutPosition].code)
                        }
                        R.id.menuDeleteCode -> {
                            listener?.deleteCode(listOfCode[layoutPosition])
                        }
                    }
                    return@setOnMenuItemClickListener true
                }
                popMenu.show()
            }
        }

        fun bind(shareCode: ShareCode){
            itemCode.tvCode.text = shareCode.code
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {
        val binding = ItemCodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CodeViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfCode.size

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        holder.bind(listOfCode[position])
    }

    interface Listener{
        fun copyCode(code: String)
        fun deleteCode(shareCode: ShareCode)
    }
}