package com.algebra.soccernewtry.display.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.FragmentInformationBinding
import com.algebra.soccernewtry.display.InformationActivity
import com.algebra.soccernewtry.player.model.Player

class FragmentInformation : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        setUpInformation()
        return binding.root
    }

    private fun setUpInformation(){
        val evaluate = ((getInformation().defense.toDouble()*0.6 + getInformation().agility.toDouble()*1.3 + getInformation().technique.toDouble()*1.1) / 30.0).toString()
        if(evaluate.length >= 4){
            binding.tvEnterEvalute.text = evaluate.substring(0,3)
        } else binding.tvEnterEvalute.text = evaluate
        binding.tvEnterDefense.text = getInformation().defense.toString()
        binding.tvEnterAgility.text = getInformation().agility.toString()
        binding.tvEnterTechnique.text = getInformation().technique.toString()
        binding.tvEnterBonusPoints.text = getInformation().bonusPoints.toString()

   /*     binding.tvEnterArriavls.text = getInformation().attendance.toString()
        binding.tvEnterGoals.text = getInformation().numberOfGoal.toString()
        binding.tvEnterAssist.text = getInformation().asistent.toString()
        binding.tvEnterWins.text = getInformation().wins.toString()
        binding.tvEnterLosses.text = getInformation().loses.toString()
        binding.tvEnterAutogoals.text = getInformation().autoGoal.toString()
        binding.tvEnterScore.text = (getInformation().wins*3 + getInformation().draw + getInformation().numberOfGoal*2 + getInformation().asistent*2 + getInformation().bonusPoints).toString()
        binding.tvEnterDraw.text = (getInformation().draw).toString()*/
    }

    private fun getInformation(): Player =
        if(activity is InformationActivity){
            (activity as InformationActivity).getPlayer()
        } else Player(0,"",0,0,0,0,0,0,0)



    companion object {
        @JvmStatic
        fun newInstance() = FragmentInformation()
    }
}