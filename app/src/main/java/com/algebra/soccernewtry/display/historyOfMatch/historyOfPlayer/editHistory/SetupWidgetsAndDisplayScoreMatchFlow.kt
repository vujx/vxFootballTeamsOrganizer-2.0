package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.editHistory

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algebra.fuca.game.teams.SubmitTeamsAdapter
import com.algebra.soccernewtry.R

class SetupWidgetsAndDisplayScoreMatchFlow{

    fun displayErrorMessage(context: Context){
        val toast = Toast.makeText(context, "You have to check assist and golgeter", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun setupAlerDialog(view: View, context: Context): AlertDialog {
        val mBuilder = AlertDialog.Builder(context, R.style.MaterialAlertDialog_OK_color)
        mBuilder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        mBuilder.setPositiveButton("Edit"){ dialog, which ->

        }
        mBuilder.setView(view)
        val alertDialog = mBuilder.create()
        alertDialog.show()
        return alertDialog
    }

    fun setUpRecyclerView(view: View, adapter: SubmitTeamsAdapter, adapterBlue: SubmitTeamsAdapter, context: Context){
        val recyclerViewRed = view.findViewById<RecyclerView>(R.id.rvRedAutogoal)
        recyclerViewRed.layoutManager = LinearLayoutManager(context)
        recyclerViewRed.adapter = adapter

        val recyclerViewBlue = view.findViewById<RecyclerView>(R.id.rvBlueAutogoal)
        recyclerViewBlue.layoutManager = LinearLayoutManager(context)
        recyclerViewBlue.adapter = adapterBlue
    }

}