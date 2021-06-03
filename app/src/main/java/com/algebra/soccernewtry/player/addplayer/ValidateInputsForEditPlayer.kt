package com.algebra.soccernewtry.player.addplayer

import android.view.View
import android.widget.EditText
import com.algebra.fuca.player.adding.PlayerAdapter
import java.lang.NumberFormatException

class ValidateInputsForEditPlayer() {

    fun checkRating(view: View, id: Int): Int {
        val et = view.findViewById<EditText>(id)

        return try{
            val rating = Integer.parseInt(et.text.toString())
            if (rating in 1..100) {
                et.error = null
                rating
            } else {
                et.error = "You have to enter value from 1 to 100"
                0
            }
        } catch(e: NumberFormatException){
            e.printStackTrace()
            et.error = "You have to enter value from 1 to 100"
            0
        }
    }


    fun checkName(view: View, id: Int): String {
        val et = view.findViewById<EditText>(id)
        et.error = null
        return if (et.text.toString().isNotEmpty()) et.text.toString()
        else {
            et.error = "Enter name"
            ""
        }
    }

    fun checkIfNameExist(view: View, id: Int, name: String): Boolean {
        var check = true
        val et = view.findViewById<EditText>(id)
        if (et.text.toString().isNotEmpty()) {
            et.error = null
            PlayerAdapter.listOfPlayers.filter {
                it.name.toUpperCase() == et.text.toString().toUpperCase().trim()
            }.forEach {
                if (name != it.name) {
                    et.error = "Name already exist"
                    check = false
                }
            }
        }
        return check
    }


}