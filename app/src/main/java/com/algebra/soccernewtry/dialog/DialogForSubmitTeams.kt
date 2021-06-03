package com.algebra.soccernewtry.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.player.model.Player

class DialogForSubmitTeams(private val list: List<Player>, private val listBlue: List<Player>): DialogFragment() {

    var listener: Listener? = null
    private val adapter = SubmitCheckDialogAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_before_submit, null)
        val recyclerViewSubmit = view.findViewById<RecyclerView>(R.id.rvSubmitTeamsRecyclerView)
        recyclerViewSubmit.layoutManager = LinearLayoutManager(requireActivity())
        recyclerViewSubmit.adapter = adapter
        adapter.setList(list, listBlue)
        return setUpAlertDialog(view)
    }

    private fun setUpAlertDialog(view: View): AlertDialog {
        val mBuilder = AlertDialog.Builder(requireContext(), R.style.MaterialAlertDialog_OK_color)
        mBuilder.setPositiveButton("Submit"){dialog, which ->
            listener?.checkPlayers(true)
        }
        mBuilder.setNegativeButton("Cancel"){dialog, which ->
            listener?.checkPlayers(false)
            dialog.dismiss() }
        mBuilder.setView(view)
        val alertDialog = mBuilder.create()
        alertDialog.show()
        return alertDialog
    }

    interface Listener{
        fun checkPlayers(check: Boolean)
    }
}