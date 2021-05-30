package com.algebra.soccernewtry.player.editPlayer

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.player.addplayer.RangeEditText
import com.algebra.soccernewtry.player.addplayer.ValidateInputsForEditPlayer
import com.algebra.soccernewtry.player.model.Player

class DialogForEditPlayer(private val player: Player, private val nameOfPositiveButton: String): DialogFragment() {

    var listenerGetChangePlayer: Listener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_player, null)

        setFilters(view, R.id.etDefenseDialog)
        setFilters(view, R.id.etAgilityDialog)
        setFilters(view, R.id.etTehnicqueDialog)

        setEditText(view, R.id.etNewNameDialogEdit, player.name.toUpperCase())
        setEditText(view, R.id.etDefenseDialog, player.defense.toString())
        setEditText(view, R.id.etAgilityDialog, player.agility.toString())
        setEditText(view, R.id.etTehnicqueDialog, player.technique.toString())

        val alertDialog = setUpAlertDialog(view)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newName = ValidateInputsForEditPlayer().checkName(view, R.id.etNewNameDialogEdit)
            val defense = ValidateInputsForEditPlayer().checkRating(view, R.id.etDefenseDialog)
            val agility = ValidateInputsForEditPlayer().checkRating(view, R.id.etAgilityDialog)
            val technique = ValidateInputsForEditPlayer().checkRating(view, R.id.etTehnicqueDialog)
            val doesNameExist = ValidateInputsForEditPlayer().checkIfNameExist(view, R.id.etNewNameDialogEdit, player.name)
            if(newName.isNotEmpty() && defense != 0 && agility != 0 && technique != 0 && (doesNameExist || newName == player.name)){
                val player = Player(player.id, newName, defense, agility, technique,
                    player.isPlaying, player.teamId, player.bonusPoints, player.isDeleted)
                listenerGetChangePlayer?.getChangedPlayer(player)
                alertDialog.dismiss()
            }
        }
        return alertDialog
    }

    private fun setEditText(view: View, id: Int, entryText: String){
        val et = view.findViewById<EditText>(id)
        et.setText(entryText)
        et.setSelection(entryText.length)
    }

    private fun setFilters(view: View, id: Int){
        val etDefense = view.findViewById<EditText>(id)
        etDefense.filters = arrayOf(RangeEditText(1, 100))
    }

    private fun setUpAlertDialog(view: View): AlertDialog{
        val mBuilder = AlertDialog.Builder(requireContext(), R.style.MaterialAlertDialog_OK_color)
        mBuilder.setPositiveButton(nameOfPositiveButton){dialog, which -> }
        mBuilder.setNegativeButton("Cancel"){dialog, which -> dialog.dismiss() }
        mBuilder.setView(view)
        val alertDialog = mBuilder.create()
        alertDialog.show()
        return alertDialog
    }

    interface Listener{
        fun getChangedPlayer(player: Player)
    }
}