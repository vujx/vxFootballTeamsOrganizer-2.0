package com.algebra.soccernewtry.game.teams.red

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.algebra.fuca.game.teams.SubmitTeamsAdapter
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.game.SubmitTeamsActivity

class DialogActionRedTeam {

    fun dialogAssisstAndGoalGetter(requireActivity: FragmentActivity, playerNameAssist: String, playerNameGoalGeter: String, redTeamAction: RedTeamAction,
                                   requireContext: Context, redTeamAdaper: SubmitTeamsAdapter, view: View, activity: SubmitTeamsActivity, frgRed: RedTeamFragment){
        val dialog = DialogCheck("Has ${playerNameAssist.toUpperCase()} assisted and has ${playerNameGoalGeter.toUpperCase()} scored?")
        dialog.show(requireActivity.supportFragmentManager, "CHECK GOAL")
        dialog.listener = object: DialogCheck.Listener{
            override fun getPress(isPress: Boolean) {
                if(isPress){
                    redTeamAction.actionAfterClickOnGoalButton(requireActivity, playerNameGoalGeter, playerNameAssist)
                    setupResultAndClear(activity, requireActivity, requireContext, redTeamAdaper, view)
                    frgRed.setAssistAndGoalToDefaultValue()
                }
            }
        }
    }

    fun dialogGoalgetterOnly(requireActivity: FragmentActivity, playerNameGoalGeter: String, redTeamAction: RedTeamAction,
                             activity: SubmitTeamsActivity, requireContext: Context, redTeamAdaper: SubmitTeamsAdapter, view: View, frgRed: RedTeamFragment
    ){
        val dialog = DialogCheck("Are you sure there aren't any assisters for scorer ${playerNameGoalGeter.toUpperCase()}?")
        dialog.show(requireActivity.supportFragmentManager, "NO ASSISST")
        dialog.listener = object: DialogCheck.Listener{
            override fun getPress(isPress: Boolean) {
                if(isPress){
                    redTeamAction.actionAfterClickOnGoalButtonIfThereIsJustGoalgeter(requireActivity, playerNameGoalGeter)
                    setupResultAndClear(activity, requireActivity, requireContext, redTeamAdaper, view)
                    frgRed.setAssistAndGoalToDefaultValue()
                }
            }
        }
    }

    fun dialogAutogoal(requireActivity: FragmentActivity, playerNameAutoGoal: String, redTeamAction: RedTeamAction,
                       activity: SubmitTeamsActivity, requireContext: Context, redTeamAdaper: SubmitTeamsAdapter, view: View, frgRed: RedTeamFragment
    ){
        val dialog = DialogCheck("Has ${playerNameAutoGoal.toUpperCase()} scored autogoal?")
        dialog.show(requireActivity.supportFragmentManager, "AUTOGOAL")
        dialog.listener = object: DialogCheck.Listener{
            override fun getPress(isPress: Boolean) {
                if(isPress){
                    redTeamAction.actionAfterAutogoal(requireActivity, playerNameAutoGoal)
                    setupResultAndClear(activity, requireActivity, requireContext, redTeamAdaper, view)
                    frgRed.setAssistAndGoalToDefaultValue()
                }
            }
        }
    }

    private fun setupResultAndClear(activity: SubmitTeamsActivity, requireActivity: FragmentActivity, requireContext: Context, redTeamAdaper: SubmitTeamsAdapter, view: View){
        setScore(view)
        setScoreInOtherFragment(activity)
        clearAssistAndGoalGeters(requireActivity, requireContext, redTeamAdaper)
    }

    @SuppressLint("SetTextI18n")
    fun setScore(view: View){
        val tvText = view.findViewById<TextView>(R.id.tvScore)
        tvText.text = "${RedTeamFragment.goalRed}:${RedTeamFragment.goalBlue}"
    }

    private fun clearAssistAndGoalGeters(requireActivity: FragmentActivity, requireContext: Context, redTeamAdaper: SubmitTeamsAdapter){
    //    SameMethodsForBlueAndRedTeam(requireActivity, requireContext).clearRedTeam()
   //     redTeamAdaper.setList(emptyList())
    }

    private fun setScoreInOtherFragment(activity: SubmitTeamsActivity){
        activity.setScore()
    }
}