package com.algebra.soccernewtry.game.teams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.algebra.soccernewtry.CreateTeamsActivity
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.game.SubmitTeamsActivity

class SameMethodInFragments {

    fun endMatch(requireActivity: SubmitTeamsActivity){
        val dialog = DialogCheck("Do you want to finish the match?")
        dialog.show(requireActivity.supportFragmentManager, "Exit")
        dialog.listener = object : DialogCheck.Listener {
            override fun getPress(isPress: Boolean) {
                if (isPress) {
                    SubmitTeamsActivity.checkIfUserLeftApp = false
                    requireActivity.endMatchOperation()
                    val intent = Intent(requireActivity, CreateTeamsActivity::class.java)
                    requireActivity.startActivity(intent)
                }
            }
        }
    }

    fun endMatchOperation(){

    }
}