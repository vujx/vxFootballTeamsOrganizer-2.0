package com.algebra.soccernewtry.dialog

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.algebra.soccernewtry.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogCheck(private val title: String): DialogFragment() {

    var listener: Listener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_OK_color).setView(view)
            .setMessage(title)
            .setPositiveButton("Ok") { dialog, which ->
                listener?.getPress(true)
            }.setNegativeButton("Cancel") { dialog, which ->
                getDialog()?.cancel()
            }.create()
    }

    interface Listener{
        fun getPress(isPress: Boolean)
    }
}