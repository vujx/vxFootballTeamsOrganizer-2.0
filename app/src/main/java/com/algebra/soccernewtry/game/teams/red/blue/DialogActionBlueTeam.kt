package com.algebra.soccernewtry.game.teams.blue

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.teams.red.RedTeamFragment

class DialogActionBlueTeam {

    fun dialogAssisst(requireActivity: FragmentActivity, playerNameAssist: String, playerNameGoalGeter: String, blueTeamAction: BlueTeamAction, view: View, activity: SubmitTeamsActivity,
    frgBlue: BlueTeamFragment){
        val dialog = DialogCheck("Has ${playerNameAssist.toUpperCase()} assisted and has ${playerNameGoalGeter.toUpperCase()} scored?")
        dialog.show(requireActivity.supportFragmentManager, "CHECK GOAL BLUE TEAM")
        dialog.listener = object: DialogCheck.Listener{
            override fun getPress(isPress: Boolean) {
                if(isPress) {
                    blueTeamAction.actionAfterClickOnGoalButton(requireActivity, playerNameAssist, playerNameGoalGeter)
                    // BlueTeamFragment.newInstance().setupResultAndClear()
                    setScoreInOtherFragment(activity)
                    setScore(view)
                    frgBlue.setAssistAndGoalToDefaultValue()
                }
            }
        }
    }

    fun dialogGoalgetter(requireActivity: FragmentActivity, playerNameGoalGeter: String, blueTeamAction: BlueTeamAction, view: View, activity: SubmitTeamsActivity, frgBlue: BlueTeamFragment){
        val dialog = DialogCheck("Are you sure there aren't any assisters for scorer ${playerNameGoalGeter.toUpperCase()}?")
        dialog.show(requireActivity.supportFragmentManager, "NOT ASSISST BLUE")
        dialog.listener = object: DialogCheck.Listener{
            override fun getPress(isPress: Boolean) {
                if(isPress){
                    blueTeamAction.actionAfterClickOnGoalButtonIfThereIsJustGoalgeter(requireActivity, playerNameGoalGeter)
               //     BlueTeamFragment.newInstance().setupResultAndClear()
                    setScore(view)
                    setScoreInOtherFragment(activity)
                    frgBlue.setAssistAndGoalToDefaultValue()
                }            }
        }
    }

    fun dialogAutogoal(requireActivity: FragmentActivity, playerNameAutoGoal: String, blueTeamAction: BlueTeamAction, view: View, activity: SubmitTeamsActivity, frgBlue: BlueTeamFragment){
        val dialog = DialogCheck("Has ${playerNameAutoGoal.toUpperCase()} scored autogoal?")
        dialog.show(requireActivity.supportFragmentManager, "AUTOGOAL")
        dialog.listener = object: DialogCheck.Listener{
            override fun getPress(isPress: Boolean) {
                if (isPress) {
                    blueTeamAction.actionAfterAutogoal(requireActivity, playerNameAutoGoal)
                 //   BlueTeamFragment.newInstance().setupResultAndClear()
                    setScoreInOtherFragment(activity)
                    setScore(view)
                    frgBlue.setAssistAndGoalToDefaultValue()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setScore(view: View){
        val tvText = view.findViewById<TextView>(R.id.tvScore)
        tvText.text = "${RedTeamFragment.goalRed}:${RedTeamFragment.goalBlue}"
    }

    fun setScoreInOtherFragment(activity: SubmitTeamsActivity){
        activity.setScore()
    }
}