package com.algebra.soccernewtry.player.addplayer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.player.model.Player

class DialogForAddingPlayer(): DialogFragment() {

    var listenerGetChangePlayer: Listener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_adding_player, null)
        setFilters(view, R.id.etDefenseDialog)
        setFilters(view, R.id.etAgilityDialog)
        setFilters(view, R.id.etTehnicqueDialog)

        setEditText(view, R.id.etNewNameDialog, "")
        setEditText(view, R.id.etDefenseDialog, "")

        val alertDialog = setUpAlertDialog(view)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newName = ValidateInputsForEditPlayer().checkName(view, R.id.etNewNameDialog)
            val defense = ValidateInputsForEditPlayer().checkRating(view, R.id.etDefenseDialog)
            val agility = ValidateInputsForEditPlayer().checkRating(view, R.id.etAgilityDialog)
            val technique = ValidateInputsForEditPlayer().checkRating(view, R.id.etTehnicqueDialog)
            val doesNameExist = ValidateInputsForEditPlayer().checkIfNameExist(view, R.id.etNewNameDialog, "")
            if(newName.isNotEmpty() && defense != 0 && agility != 0 && technique != 0 && (doesNameExist)){
                val player = Player(0, newName, defense, agility, technique, 0, 0, 0, 0)
                listenerGetChangePlayer?.addNewPlayer(player)
                alertDialog.dismiss()
            }
        }
        return alertDialog
    }

    private fun setFilters(view: View, id: Int){
        val etDefense = view.findViewById<EditText>(id)
        etDefense.filters = arrayOf(RangeEditText(1, 100))
    }

    private fun setEditText(view: View, id: Int, entryText: String){
        val et = view.findViewById<EditText>(id)
        et.setText(entryText)
        et.setSelection(entryText.length)
        Log.d("Daj da vidim", entryText)
    }

    private fun setUpAlertDialog(view: View): AlertDialog {
        val mBuilder = AlertDialog.Builder(requireContext(), R.style.MaterialAlertDialog_OK_color)
        mBuilder.setPositiveButton("Add"){dialog, which -> }
        mBuilder.setNegativeButton("Cancel"){dialog, which -> dialog.dismiss() }
        mBuilder.setView(view)
        val alertDialog = mBuilder.create()
        alertDialog.show()
        return alertDialog
    }

    interface Listener{
        fun addNewPlayer(player: Player)
    }
}