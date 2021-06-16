package com.algebra.soccernewtry.display.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.algebra.soccernewtry.databinding.FragmentInformationBinding
import com.algebra.soccernewtry.display.InformationActivity
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentInformation : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!
    private val viewModelPlayer: PlayerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        setUpInformation()
        return binding.root
    }

    private fun setUpInformation(){
        lifecycleScope.launchWhenResumed{
            val evaluate = ((getInformation().defense.toDouble()*0.6 + getInformation().agility.toDouble()*1.3 + getInformation().technique.toDouble()*1.1) / 30.0).toString()
            if(evaluate.length >= 4){
                binding.tvEnterEvalute.text = evaluate.substring(0,3)
            } else binding.tvEnterEvalute.text = evaluate
            binding.tvEnterDefense.text = getInformation().defense.toString()
            binding.tvEnterAgility.text = getInformation().agility.toString()
            binding.tvEnterTechnique.text = getInformation().technique.toString()
            binding.tvEnterBonusPoints.text = getInformation().bonusPoints.toString()

            val playerSpec = viewModelPlayer.getAllPlayersSpecification(getInformation().id)
            val numOfGoals = viewModelPlayer.getNumberOfGoals(getInformation().id)
            val numOfAss = viewModelPlayer.getNumberOfAssist(getInformation().id)
            val numOfAutogoals = viewModelPlayer.getNumberOfAutogoals(getInformation().id)

            var wins = 0
            var loses = 0
            var draw = 0
            viewModelPlayer.getPlayersMatches(getInformation().id).forEach {
                val getMatchResultRed = viewModelPlayer.getMatchResult(it.matchId)
                val getMatchResultBlue = viewModelPlayer.getResultBlueTeam(it.matchId)

                if(getMatchResultRed.teamGoals > getMatchResultBlue.teamGoals && it.teamId == 1) wins++
                if(getMatchResultRed.teamGoals == getMatchResultBlue.teamGoals) draw++
                if(getMatchResultRed.teamGoals < getMatchResultBlue.teamGoals && it.teamId == 1) loses++

                if(getMatchResultRed.teamGoals < getMatchResultBlue.teamGoals && it.teamId == 2) wins++
                if(getMatchResultRed.teamGoals > getMatchResultBlue.teamGoals && it.teamId == 2) loses++
            }

            binding.tvEnterArriavls.text = playerSpec.attendance.toString()
            binding.tvEnterGoals.text = numOfGoals.toString()
            binding.tvEnterAssist.text = numOfAss.toString()
            binding.tvEnterWins.text = wins.toString()
            binding.tvEnterLosses.text = loses.toString()
            binding.tvEnterAutogoals.text = numOfAutogoals.toString()
            binding.tvEnterScore.text = (wins*3 + draw + numOfGoals*2 + numOfAss*2 + getInformation().bonusPoints - numOfAutogoals).toString()
            binding.tvEnterDraw.text = draw.toString()

        }
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